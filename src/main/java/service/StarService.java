package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.JStar;
import util.JavStringUtil;

public class StarService {
	/**
	 * 从一个star页面中获取所有star
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public List<JStar> getStars(Document doc) throws Exception {
		// .starbox.searchitem
		List<JStar> stars = new ArrayList<JStar>();
		Elements select = doc.select(".starbox .searchitem");
		for (Element element : select) {
			// System.out.println(element.text());
			if (element.select("a").first() != null) {
				JStar star = new JStar();
				star.setId(JavStringUtil.getStarId(element.select("a").first().attr("href")));
				star.setName(element.text());
				star.setUrl(element.select("a").first().attr("href"));
				stars.add(star);
			}
		}
		return stars;
	}
}
