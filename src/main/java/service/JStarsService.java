package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import bean.JStar;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;
import util.JavHtmlPagesUtil;

public class JStarsService implements IJavServie<JStar> {

	// 解析http://j8vlib.com/cn/star_list.php
	public List<JStar> getAll() {
		// .staralphabets .alphabet
		List<JStar> stars = new ArrayList<JStar>();

		String url = "http://j8vlib.com/cn/star_list.php?prefix=A";
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (IOException e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}
		getAll(url);
		for (Element element : doc.select(".staralphabets .alphabet")) {
			if (element.select("a").first() != null) {
				String absUrl = element.select("a").first().absUrl("href");
				System.out.println(absUrl);
				getAll(absUrl);
			}
		}
		return stars;
	}

	// http://j8vlib.com/cn/star_list.php?prefix=A
	public List<JStar> getAll(String url) {

		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (IOException e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}

		int pages = JavHtmlPagesUtil.getPagesFromDom(doc);
		System.out.println(url + " 页数:" + pages);
		if (pages == 0) {
			throw new JavException(ErrorCode.PAGE_NOT_FOUND);
		}
		for (int i = 1; i <= pages; i++) {
			System.out.println("Page:" + i);
			if (i == 1) {
				getByDocument(doc);
			} else {
				getByPage(url, 2);
			}
		}
		return null;
	}

	public List<JStar> getByDocument(Document doc) {
		if (doc != null) {
			try {
				List<JStar> stars = new StarService().getStars(doc);
				System.out.println(stars);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
			}
		}
		return null;
	}

	public List<JStar> getByPage(String url, int page) {
		String pageUrl = url + "&page=" + page;
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(pageUrl);
		} catch (IOException e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + pageUrl);
		}
		if (doc != null) {
			return getByDocument(doc);
		}
		throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + pageUrl);
	}
}
