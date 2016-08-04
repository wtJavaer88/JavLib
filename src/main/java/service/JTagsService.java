package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import bean.JTag;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;

public class JTagsService implements IJavServie<JTag> {

	List<JTag> tags = new ArrayList<JTag>();

	public List<JTag> getAll() {
		return getAll("http://j8vlib.com/cn/genres.php");
	}

	public List<JTag> getAll(String url) {

		Document doc = null;
		try {
			doc = DocumentUtil.getDoc(url);
		} catch (Exception e) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR);
		}
		if (doc == null) {
			throw new JavException(ErrorCode.URL_PARSE_ERROR, "page解析失败:" + url);
		}
		tags.addAll(getByDocument(doc));
		return tags;
	}

	public List<JTag> getByDocument(Document doc) {
		List<JTag> tTags = new ArrayList<JTag>();
		if (doc != null) {
			try {
				tTags = new TagService().getTags(doc);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return tTags;
	}

	public List<JTag> getByPage(String url, int page) {
		return null;
	}

}
