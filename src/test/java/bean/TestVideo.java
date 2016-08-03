package bean;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.wnc.basic.BasicNumberUtil;

import util.DocumentUtil;
import util.JavStringUtil;

public class TestVideo {
	@Test
	public void t() throws IOException {
		// Document doc =
		// DocumentUtil.getDoc("http://j8vlib.com/cn/?v=javlilrhza");
		// System.out.println(doc);
		String location = "D:\\Users\\wnc\\Programs\\sts-bundle\\projects\\javlib\\src\\resources\\video.html";
		Document doc = DocumentUtil.getDocFromLocal(location);
		getStars(doc.select("#video_cast"));
		getTags(doc.select("#video_genres"));
		// getScore(doc.select("#video_review .score"));
		getMaker(doc.select("#video_maker .maker"));
		getLength(doc.select("#video_length span"));
		getId(doc.select("#video_id .text"));
		getDate(doc.select("#video_date .text"));
		getDirector(doc.select("#video_director .text"));
		// video_label
		getLabel(doc.select("#video_label .text"));
		getTitleAndUrl(doc.select("#video_title"));
	}

	private void getTitleAndUrl(Elements title) {
		System.out.println("title:" + title.first().text());
		System.out.println("url:" + title.first().select("a").first().attr("href"));
	}

	private void getLabel(Elements label) {
		// TODO Auto-generated method stub
		System.out.println("labelId:" + JavStringUtil.getStarId(label.first().select("a").first().attr("href")));
		System.out.println("labelName:" + label.first().text());
		System.out.println("labelUrl:" + label.first().select("a").first().attr("href"));
	}

	private void getDirector(Elements director) {
		System.out.println("directorId:" + JavStringUtil.getStarId(director.first().select("a").first().attr("href")));
		System.out.println("directorName:" + director.first().text());
		System.out.println("directorUrl:" + director.first().select("a").first().attr("href"));
	}

	private void getDate(Elements date) {
		System.out.println("date:" + date.first().text().trim());
	}

	private void getId(Elements id) {
		System.out.println("length:" + id.first().text().trim());
	}

	private void getLength(Elements length) {
		System.out.println("length:" + length.first().text().trim());
	}

	private void getMaker(Elements maker) {
		System.out.println("makerId:" + JavStringUtil.getStarId(maker.first().select("a").first().attr("href")));
		System.out.println("makerName:" + maker.first().text());
		System.out.println("makerUrl:" + maker.first().select("a").first().attr("href"));
	}

	/**
	 * 新片可能没数据
	 * 
	 * @param score
	 */
	private double getScore(Elements score) {
		if (score == null) {
			return 0;
		}
		System.out.println(score.first().text());
		return BasicNumberUtil.getDouble(score.first().text());
	}

	private void getTags(Elements genres) {
		System.out.println("getTags");
		Elements tags = genres.first().select("span");
		System.out.println(tags);
		for (Element element : tags) {
			System.out.println("tagId:" + JavStringUtil.getStarId(element.select("a").first().attr("href")));
			System.out.println("tagName:" + element.text());
			System.out.println("tagName:" + element.select("a").first().attr("href"));
		}
	}

	private void getStars(Elements video_cast) {
		System.out.println("getStars");
		Elements spans = video_cast.first().select("span");
		for (int i = 0; i < spans.size(); i++) {
			if (spans.get(i).attr("class").equals("star")) {
				System.out.println("starId:" + JavStringUtil.getStarId(spans.get(i).select("a").first().attr("href")));
				System.out.println("starName:" + spans.get(i).text());
				System.out.println("starUrl:" + spans.get(i).select("a").first().attr("href"));
			}
			int j = i + 1;
			while (j < spans.size() && spans.get(j).attr("class").equals("alias")) {
				System.out.println("别名:" + spans.get(j).text());
				j++;
			}
			i = j - 1;
		}
	}
}
