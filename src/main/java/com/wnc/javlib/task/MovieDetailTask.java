package com.wnc.javlib.task;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.crawl.spider.SpiderHttpClient;
import com.crawl.spider.entity.Page;
import com.crawl.spider.task.AbstractPageTask;
import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.entity.JMovie;
import com.wnc.javlib.service.IMovieService;
import com.wnc.javlib.service.MovieDetailImpl;
import com.wnc.javlib.utils.RetryMgr;
import org.jsoup.nodes.Document;

public class MovieDetailTask extends AbstractPageTask {

	public MovieDetailTask(String url) {
		super(url, true);
	}

	@Override
	public void handle(Page page) {
		Document doc = getDoc(page);

		IMovieService iMovieService = getMovieSrv();
		JMovie jMovie = iMovieService.getMovieDetail(doc);
		// saveMovieDetailDb(iMovieService, doc);
		saveMovieDetailFile(jMovie);
	}

	private IMovieService getMovieSrv() {
		return new MovieDetailImpl();
	}

	private void saveMovieDetailFile(JMovie jMovie) {
		BasicFileUtil.writeFileString(
				"F:/资源/爬虫/javlib/" + jMovie.getMovieCode() + ".json", JSONObject.toJSONString(jMovie,
						SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect),
				null, true);

		SpiderHttpClient.parseCount.getAndIncrement();
		BasicFileUtil.writeFileString(spiderqueue.util.QueueConfig.logPath + "success-url-jav.txt", url + "\r\n", null,
				false);
	}

	@Override
	public void retry() {
		boolean addUrlAndStop = RetryMgr.addUrlAndStop(url);
		if (!addUrlAndStop) {
			SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(url));
		} else {
			RetryMgr.logTimeout(url);
		}
	}

}
