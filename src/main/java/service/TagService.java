package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.JTag;
import util.JavStringUtil;

public class TagService {
	/**
	 * 从一个tag页面中获取所有star(一般100条/页)
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<JTag> getTags(Document doc) throws Exception {
		// .starbox.searchitem
		List<JTag> tags = new ArrayList<JTag>();
		Elements select = doc.select(".textbox .genreitem");
		for (Element element : select) {
			// System.out.println(element.text());
			if (element.select("a").first() != null) {
				JTag tag = new JTag();
				tag.setId(JavStringUtil.getStarId(element.select("a").first().absUrl("href")));
				tag.setName(element.text());
				tag.setUrl(element.select("a").first().absUrl("href"));
				tags.add(tag);
			}
		}
		return tags;
	}
}
