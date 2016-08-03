package service;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.JVideo;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;
import util.JavHtmlPagesUtil;

//http://j8vlib.com/cn/vl_star.php?s=ci
//http://j8vlib.com/cn/vl_genre.php?g=bm
//http://j8vlib.com/cn/vl_director.php?d=ayja
public class JVideosService implements IJavServie<JVideo> {

	public List<JVideo> getAll(String url) {
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

	public List<JVideo> getByDocument(Document doc) {
		if (doc != null) {
			Elements videos = doc.select(".videos .video");
			for (Element element : videos) {
				String videoUrl = element.select("a").first().absUrl("href");
				System.out.println(videoUrl);
				try {
					JVideo video = new VideoService().getVideo(videoUrl);
					System.out.println(video);
				} catch (Exception e) {
					System.out.println(videoUrl + e.getMessage());
					// e.printStackTrace();
				}
			}
		}
		return null;
	}

	public List<JVideo> getByPage(String url, int page) {
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
