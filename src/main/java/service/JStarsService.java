package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import bean.JStar;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;
import util.JavHtmlPagesUtil;

public class JStarsService implements IJavServie<JStar> {

	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(26);
	List<JStar> stars = new ArrayList<JStar>();

	// 解析http://j8vlib.com/cn/star_list.php
	public List<JStar> getAll() {
		// .staralphabets .alphabet

		String url = "http://j8vlib.com/cn/star_list.php?prefix=A";
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (Exception e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}

		executor.execute(new StarTask(this, url));
		// getAll(url);
		for (Element element : doc.select(".staralphabets .alphabet")) {
			if (element.select("a").first() != null) {
				String absUrl = element.select("a").first().absUrl("href");
				System.out.println(absUrl);
				// getAll(absUrl);
				executor.execute(new StarTask(this, absUrl));
			}
		}
		executor.shutdown();
		while (executor.getActiveCount() > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stars;
	}

	// http://j8vlib.com/cn/star_list.php?prefix=A
	public List<JStar> getAll(String url) {
		List<JStar> starsOfPrefix = new ArrayList<JStar>();
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (Exception e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}

		int pages = JavHtmlPagesUtil.getPagesFromDom(doc);

		Logger.logByPrefix(url, "总页数:" + pages);
		System.out.println(url + " 页数:" + pages);
		if (pages == 0) {
			throw new JavException(ErrorCode.PAGE_NOT_FOUND);
		}
		for (int i = 1; i <= pages; i++) {
			System.out.println("Page:" + i);
			Logger.logByPrefix(url, "开始解析页数:" + i);
			if (i == 1) {
				starsOfPrefix.addAll(getByDocument(doc));
			} else {
				starsOfPrefix.addAll(getByPage(url, i));
			}
		}
		return starsOfPrefix;
	}

	public List<JStar> getByDocument(Document doc) {
		List<JStar> stars = new ArrayList<JStar>();
		if (doc != null) {
			try {
				stars = new StarService().getStars(doc);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return stars;
	}

	public List<JStar> getByPage(String url, int page) {
		String pageUrl = url + "&page=" + page;
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(pageUrl);
		} catch (Exception e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + pageUrl);
		}
		if (doc != null) {
			return getByDocument(doc);
		}
		throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + pageUrl);
	}

	public synchronized void appendStars(List<JStar> all) {
		// TODO Auto-generated method stub
		stars.addAll(all);
		System.out.println("当前大小" + stars.size());
		Logger.logGlobal("当前结果集大小" + stars.size());
	}

	public List<JStar> getStars() {
		return stars;
	}
}
