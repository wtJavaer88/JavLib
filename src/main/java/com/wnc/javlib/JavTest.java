package com.wnc.javlib;

import com.crawl.spider.SpiderHttpClient;
import com.wnc.basic.BasicFileUtil;
import com.wnc.basic.BasicNumberUtil;
import com.wnc.javlib.task.MovieDetailTask;
import com.wnc.javlib.task.StarIndexTask;
import com.wnc.javlib.task.StarMoviesTask;
import com.wnc.javlib.utils.JavConfig;
import db.DbExecMgr;

import java.util.Map;

public class JavTest {
	public static void startMvDetailTest() {
		// 将SINGLE_STAR字段作为是否更新标记
		Map starMap = DbExecMgr.getSelectAllSqlMap("SELECT URL,MOVIE_CODE FROM JAV_MOVIE WHERE SINGLE_STAR IS NULL");
		System.out.println("starMap.size():" + starMap.size());
		Map fieldMap;
		String file;
		String url;
		for (int i = 1; i <= starMap.size(); i++) {
			fieldMap = (Map) starMap.get(i);
			url = JavConfig.DOMAIN + fieldMap.get("URL").toString();
			file = "F:/资源/爬虫/javlib/" + fieldMap.get("MOVIE_CODE").toString() + ".json";
			if (!BasicFileUtil.isExistFile(file)) {
				SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new MovieDetailTask(url));
			}
		}
	}

	public static void starMoviesTest() {
		Map starMap = DbExecMgr.getSelectAllSqlMap("SELECT STAR_CODE,MV_PAGES FROM JAV_STAR");
		Map fieldMap;
		for (int i = 1; i <= starMap.size(); i++) {
			fieldMap = (Map) starMap.get(i);
			String starCode = fieldMap.get("STAR_CODE").toString();
			int pages = BasicNumberUtil.getNumber(fieldMap.get("MV_PAGES") + "");
			SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new StarMoviesTask(starCode, 1, pages));
		}
	}

	public static void starIndexTest() {
		// jStarQueue.startQueue();
		for (char i = 97; i < 123; i++) {
			SpiderHttpClient.getInstance().getNetPageThreadPool().execute(new StarIndexTask(i, 1));
		}
	}
}
