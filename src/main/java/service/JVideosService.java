package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wnc.basic.BasicFileUtil;
import com.wnc.basic.BasicStringUtil;

import bean.JVideo;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;
import util.JavHtmlPagesUtil;
import util.VideoQueue;

//http://j8vlib.com/cn/vl_star.php?s=ci
//http://j8vlib.com/cn/vl_genre.php?g=bm
//http://j8vlib.com/cn/vl_director.php?d=ayja
public class JVideosService implements IJavServie<JVideo> {

	List<JVideo> videos = new ArrayList<JVideo>();

	public List<JVideo> getAllByStarId(String starID) {
		BasicFileUtil.deleteFile("D:\\txt\\javlib\\star\\" + starID + ".txt");
		return getAll("http://j8vlib.com/cn/vl_star.php?s=" + starID);
	}

	public List<JVideo> getAllByTagId(String tagID) {
		return getAll("http://j8vlib.com/cn/vl_genre.php?g=" + tagID);
	}

	public List<JVideo> getAll(String url) {
		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (Exception e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}

		int pages = JavHtmlPagesUtil.getPagesFromVideosDom(doc);
		System.out.println(url + " 总页数:" + pages);
		Logger.logByStarOrTagId(url, url + "总页数:" + pages);
		if (pages == 0) {
			Logger.logByStarOrTagId(url, " 没有分页数据");
			// throw new JavException(ErrorCode.PAGE_NOT_FOUND);
		}
		for (int i = 1; i <= pages; i++) {
			System.out.println("Page:" + i);
			List<JVideo> tVideos = null;
			Logger.logByStarOrTagId(url, "开始解析Page:" + i);
			if (i == 1) {
				tVideos = getByDocument(doc);
			} else {
				tVideos = getByPage(url, i);
			}
			if (tVideos != null) {
				String vResult = "";
				for (JVideo video : tVideos) {
					vResult += video.getId() + ",";
				}
				vResult = BasicStringUtil.removeRightString(vResult, ",");
				Logger.logByStarOrTagId(url, "结果集:" + vResult);
				videos.addAll(tVideos);
			}
		}
		Logger.logByStarOrTagId(url, "总结果数:" + videos.size());
		return videos;
	}

	public List<JVideo> getByDocument(Document doc) {
		List<JVideo> videosOfPage = new ArrayList<JVideo>();
		if (doc != null) {
			Elements videosElements = doc.select(".videos .video");
			for (Element element : videosElements) {
				String videoUrl = element.select("a").first().absUrl("href");
				if (VideoQueue.addVideoUrl(videoUrl)) {
					try {
						JVideo video = new VideoService().getVideo(videoUrl);
						// System.out.println(video);
						videosOfPage.add(video);
					} catch (Exception e) {
						Logger.logGlobal(videoUrl + " 解析video异常:" + e.getMessage());
						System.out.println(videoUrl + e.getMessage());
					}
				} else {
					System.out.println("已经存在, 不用做了" + videoUrl);
				}
			}
		}
		return videosOfPage;
	}

	public List<JVideo> getByPage(String url, int page) {
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
}
