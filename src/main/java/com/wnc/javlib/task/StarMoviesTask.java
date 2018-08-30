package com.wnc.javlib.task;

import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.entity.JMovie;
import com.wnc.javlib.service.IStarService;
import com.wnc.javlib.utils.PageCountUtil;
import com.wnc.javlib.utils.RetryMgr;
import com.wnc.javlib.utils.SpiderLogMgr;
import com.wnc.javlib.utils.UrlFormatter;
import common.spider.node.MyElement;
import db.DbExecMgr;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StarMoviesTask extends AbstractPageTask {
	private String starCode;
	private int pageIndex;
	private int pages;

	public StarMoviesTask(String starCode, int pageIndex, int pages) {
		super(UrlFormatter.getStarMoviesUrl(starCode, pageIndex), true);
		this.starCode = starCode;
		this.pageIndex = pageIndex;
		this.pages = pages;
	}

	@Override
	public void handle(Page page) {
		Document doc = getDoc(page);
		IStarService iStarService = getStarSrv();
		if (pageIndex == 1) {
			// TODO 更新该明星总页数
			int pageCount = iStarService.getPageCount(doc);
			if (pageCount != pages) {
				System.out.println(starCode + " Pagecount:" + pageCount);
				try {
					DbExecMgr.execOnlyOneUpdate(
							"UPDATE JAV_STAR SET MV_PAGES=" + pageCount + " WHERE STAR_CODE='" + starCode + "'");
				} catch (SQLException e) {
					BasicFileUtil.writeFileString("err-jav-starmv-db.txt", url + " " + e.toString() + "\r\n", null,
							true);
					e.printStackTrace();
				}
			}
			// 与上次记录对比避免重复
			if (pages > 0 && pageCount > pages) {
				pageCount = pageCount - pages + 1;
			}
			for (int i = 2; i <= pageCount; i++) {
				if (!SpiderLogMgr.isExist("StarMoviesTask", UrlFormatter.getStarMoviesUrl(starCode, i))) {
					SpiderHttpClient.getInstance().getNetPageThreadPool()
							.execute(new StarMoviesTask(starCode, i, pages));
				}
			}
		}
		// if (!SpiderLogMgr.isExist(url))
		parseMovies(iStarService, doc);
	}

	private IStarService getStarSrv() {
		return new IStarService() {

			public int getPageCount(Document doc) {
				return PageCountUtil.getPageCount(doc);
			}

			public List<JMovie> getPageBasicMovies(Document doc) {
				Elements select = doc.select(".videos .video>a");
				List<JMovie> movies = new ArrayList<JMovie>(20);
				MyElement myElement;
				for (Element element : select) {
					try {
						myElement = new MyElement(element);
						String url = myElement.attr("href");
						if (url.startsWith("./")) {
							url = url.substring(1);
						}
						String title = myElement.attr("title");
						String code = element.select(".id").first().text().trim();
						JMovie e = new JMovie(code, title, url);
						String monoImg = element.select("img").first().attr("src").trim();
						if (monoImg.startsWith("//") && monoImg.length() > 2) {
							monoImg = monoImg.substring(2);
						}
						e.setMonoImg(monoImg);
						movies.add(e);
					} catch (Exception e) {
						e.printStackTrace();
						logErr(e.toString());
					}
				}
				return movies;
			}

		};
	}

	private void logErr(String msg) {
		BasicFileUtil.writeFileString("err-jav.txt", url + " " + msg + "\r\n", null, true);
	}

	private void parseMovies(IStarService IStarService, Document doc) {
		List<JMovie> pageMovies = IStarService.getPageBasicMovies(doc);
		for (JMovie jMovie : pageMovies) {
			System.out.println("插入数据库..." + starCode + " " + jMovie.getMovieCode());
			// JavTest.jMovieQueue.addEntity(jMovie);
			try {
				String mvCode = StringEscapeUtils.escapeSql(jMovie.getMovieCode());
				String sql = "INSERT INTO JAV_MOVIE(MOVIE_CODE,URL,MONO_IMG,TITLE) VALUES('" + mvCode + "','"
						+ StringEscapeUtils.escapeSql(jMovie.getUrl()) + "','"
						+ StringEscapeUtils.escapeSql(jMovie.getMonoImg()) + "','"
						+ StringEscapeUtils.escapeSql(jMovie.getTitle()) + "')";
				// System.out.println(sql);
				if (!DbExecMgr.isExistData("JAV_MOVIE", "MOVIE_CODE", mvCode)) {
					DbExecMgr.execOnlyOneUpdate(sql);
					SpiderHttpClient.parseCount.getAndIncrement();
				} else {
					System.out.println(jMovie.getMovieCode() + "已经存在");
					// 后面的停止插入
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logErr("SQL异常: " + e.toString());
			}
		}
		SpiderHttpClient.parseCount.getAndIncrement();
		BasicFileUtil.writeFileString(spiderqueue.util.QueueConfig.logPath + "starmovieurl.txt", url + "\r\n", null,
				true);
	}

	@Override
	public void retry() {
		boolean addUrlAndStop = RetryMgr.addUrlAndStop(url, 10);
		if (!addUrlAndStop) {
			SpiderHttpClient.getInstance().getNetPageThreadPool()
					.execute(new StarMoviesTask(starCode, pageIndex, pages));
		} else {
			RetryMgr.logTimeout(url);
		}
	}

}
