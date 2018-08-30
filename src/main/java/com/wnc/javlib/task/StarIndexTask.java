package com.wnc.javlib.task;

import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.wnc.basic.BasicDateUtil;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.entity.JStar;
import com.wnc.javlib.service.IStarIndexService;
import com.wnc.javlib.utils.PageCountUtil;
import com.wnc.javlib.utils.RetryMgr;
import com.wnc.javlib.utils.UrlFormatter;
import common.spider.node.MyElement;
import db.DbExecMgr;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StarIndexTask extends AbstractPageTask {
	private char index;
	private int pageIndex;

	public StarIndexTask(char index, int page) {
		super(UrlFormatter.getIndexStarsUrl(index, page), true);
		this.index = index;
		this.pageIndex = page;
		// this.proxyFlag = false;
	}

	public void handle(Page page) {
		Document doc = getDoc(page);
		IStarIndexService iStarIndexService = getStarIndexSrv();
		if (pageIndex == 1) {
			int pageCount = iStarIndexService.getPageCount(doc);
			System.out.println(url + " pageCount: " + pageCount);
			for (int i = 2; i <= pageCount; i++) {
				SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new StarIndexTask(index, i));
			}
		}
		parseStars(iStarIndexService, doc);
	}

	private IStarIndexService getStarIndexSrv() {
		return new IStarIndexService() {

			public List<JStar> getPageStars(Document doc) {
				Elements select = doc.select(".starbox .searchitem a");
				List<JStar> stars = new ArrayList<JStar>(50);
				MyElement myElement;
				for (Element element : select) {
					myElement = new MyElement(element);
					String code = myElement.pattern4Attr("href", "s=(\\S+)");
					String name = myElement.text();
					stars.add(new JStar(index, code, name));
				}
				return stars;
			}

			public int getPageCount(Document doc) {
				return PageCountUtil.getPageCount(doc);
			}
		};
	}

	private void parseStars(IStarIndexService iStarIndexService, Document doc) {
		List<JStar> pageStars = iStarIndexService.getPageStars(doc);
		for (JStar jStar : pageStars) {
			// System.out.println("插入数据库..." + jStar);
			// JavTest.jStarQueue.addEntity(jStar);
			try {
				if (!DbExecMgr.isExistData("JAV_STAR", "STAR_CODE", jStar.getStarCode())) {
					String sql = "INSERT INTO JAV_STAR(STAR_CODE,NAME,HEAD,CREATE_DATE) VALUES('" + jStar.getStarCode()
							+ "','" + jStar.getName() + "','" + jStar.getHead() + "','"
							+ BasicDateUtil.getCurrentDateString() + "')";
					System.out.println(sql);
					DbExecMgr.execOnlyOneUpdate(sql);
					SpiderHttpClient.parseCount.getAndIncrement();
				}
			} catch (SQLException e) {
				BasicFileUtil.writeFileString("err-jav.txt", url + " " + e.toString() + "\r\n", null, true);
				e.printStackTrace();
			}
		}
		BasicFileUtil.writeFileString(spiderqueue.util.QueueConfig.logPath + "success-url-jav.txt", url + "\r\n", null,
				true);
	}

	@Override
	public void retry() {
		boolean addUrlAndStop = RetryMgr.addUrlAndStop(url, 10);
		if (!addUrlAndStop) {
			SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new StarIndexTask(index, pageIndex));
		} else {
			RetryMgr.logTimeout(url);
		}
	}

}
