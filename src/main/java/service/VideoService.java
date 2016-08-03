package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wnc.basic.BasicNumberUtil;
import com.wnc.string.PatternUtil;

import bean.JDirector;
import bean.JLabel;
import bean.JMaker;
import bean.JStar;
import bean.JTag;
import bean.JVideo;
import common.ErrorCode;
import common.JavException;
import util.DocumentUtil;
import util.JavStringUtil;

//http://j8vlib.com/cn/vl_star.php?s=ci
//http://j8vlib.com/cn/vl_genre.php?g=bm
//http://j8vlib.com/cn/vl_director.php?d=ayja
public class VideoService {
	public JVideo getVideo(String url) throws Exception {
		JVideo video = new JVideo();
		Document doc = DocumentUtil.getDoc(url);
		video.setStars(getStars(doc.select("#video_cast")));
		video.setTags(getTags(doc.select("#video_genres")));
		video.setScore(getScore(doc.select("#video_review .score")));
		video.setMaker(getMaker(doc.select("#video_maker .maker")));
		video.setLength(getLength(doc.select("#video_length span")));
		video.setId(getId(doc.select("#video_id .text")));
		video.setDate(getDate(doc.select("#video_date .text")));
		video.setDirector(getDirector(doc.select("#video_director .text")));
		// video_label
		video.setLabel(getLabel(doc.select("#video_label .text")));

		Elements select = doc.select("#video_title");
		if (select != null && select.first() != null) {
			video.setName(select.first().text());
			video.setUrl(select.first().select("a").first().attr("href"));
		}
		// getTitleAndUrl(doc.select("#video_title"));
		return video;
	}

	private JLabel getLabel(Elements label) {
		if (label == null || label.first() == null || label.first().select("a").first() == null) {
			return null;
		}
		JLabel jlabel = new JLabel();
		try {
			jlabel.setId(JavStringUtil.getStarId(label.first().select("a").first().attr("href")));
			jlabel.setName(label.first().text());
			jlabel.setUrl(label.first().select("a").first().attr("href"));
		} catch (Exception e) {
			throw new JavException(ErrorCode.FAIL, "获取label错误");
		}
		// System.out.println("labelId:" +
		// JavStringUtil.getStarId(label.first().select("a").first().attr("href")));
		// System.out.println("labelName:" + label.first().text());
		// System.out.println("labelUrl:" +
		// label.first().select("a").first().attr("href"));
		return jlabel;
	}

	private JDirector getDirector(Elements director) {
		if (director == null || director.first() == null || director.first().select("a").first() == null) {
			return null;
		}
		JDirector jdirector = new JDirector();
		try {
			jdirector.setId(JavStringUtil.getStarId(director.first().select("a").first().attr("href")));
			jdirector.setName(director.first().text());
			jdirector.setUrl(director.first().select("a").first().attr("href"));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JavException(ErrorCode.FAIL, "获取director错误");
		}
		// System.out.println("directorId:" +
		// JavStringUtil.getStarId(director.first().select("a").first().attr("href")));
		// System.out.println("directorName:" + director.first().text());
		// System.out.println("directorUrl:" +
		// director.first().select("a").first().attr("href"));
		return jdirector;
	}

	private String getDate(Elements date) {
		if (date == null || date.first() == null) {
			return null;
		}
		// System.out.println("date:" + date.first().text().trim());
		try {
			return date.first().text().trim();
		} catch (Exception ex) {
			throw new JavException(ErrorCode.FAIL, "获取date错误");
		}
	}

	private String getId(Elements id) {
		// System.out.println("id:" + id.first().text().trim());
		try {
			return id.first().text().trim();
		} catch (Exception ex) {
			throw new JavException(ErrorCode.FAIL, "获取id错误");
		}
	}

	private int getLength(Elements length) {
		if (length == null || length.first() == null) {
			return 0;
		}
		// System.out.println("length:" + length.first().text().trim());
		try {
			return BasicNumberUtil.getNumber(length.first().text().trim());
		} catch (Exception ex) {
			throw new JavException(ErrorCode.FAIL, "获取length错误");
		}
	}

	private JMaker getMaker(Elements maker) {
		if (maker == null || maker.first() == null || maker.first().select("a").first() == null) {
			return null;
		}
		JMaker jmaker = new JMaker();
		try {
			jmaker.setId(JavStringUtil.getStarId(maker.first().select("a").first().attr("href")));
			jmaker.setName(maker.first().text());
			jmaker.setUrl(maker.first().select("a").first().attr("href"));
		} catch (Exception e) {
			throw new JavException(ErrorCode.FAIL, "获取Maker错误");
		}
		// System.out.println("makerId:" +
		// JavStringUtil.getStarId(maker.first().select("a").first().attr("href")));
		// System.out.println("makerName:" + maker.first().text());
		// System.out.println("makerUrl:" +
		// maker.first().select("a").first().attr("href"));
		return jmaker;
	}

	/**
	 * 新片可能没数据
	 * 
	 * @param score
	 */
	private double getScore(Elements score) {
		if (score == null || score.first() == null) {
			return 0;
		}
		// System.out.println(score.first().text());
		try {
			return BasicNumberUtil.getDouble(PatternUtil.getFirstPattern(score.first().text(), "\\d+.\\d+"));
		} catch (Exception e) {
			throw new JavException(ErrorCode.FAIL, "获取Tag错误");
		}
	}

	private List<JTag> getTags(Elements genres) {
		if (genres == null || genres.first() == null || genres.first().select("span").first() == null) {
			return null;
		}
		List<JTag> tagList = new ArrayList<JTag>();
		// System.out.println("getTags");
		try {
			Elements tags = genres.first().select("span");
			// System.out.println(tags);
			for (Element element : tags) {
				JTag tag = new JTag();
				tag.setId(JavStringUtil.getStarId(element.select("a").first().attr("href")));
				tag.setName(element.text());
				tag.setUrl(element.select("a").first().attr("href"));
				tagList.add(tag);
				// System.out.println("tagId:" +
				// JavStringUtil.getStarId(element.select("a").first().attr("href")));
				// System.out.println("tagName:" + element.text());
				// System.out.println("tagName:" +
				// element.select("a").first().attr("href"));
			}
		} catch (Exception e) {
			throw new JavException(ErrorCode.FAIL, "获取Tag错误");
		}
		return tagList;
	}

	private List<JStar> getStars(Elements video_cast) {
		if (video_cast == null || video_cast.first() == null || video_cast.first().select("span").first() == null) {
			return null;
		}
		List<JStar> stars = new ArrayList<JStar>();
		try {
			// System.out.println("getStars");
			Elements spans = video_cast.first().select("span");
			for (int i = 0; i < spans.size(); i++) {
				if (spans.get(i).attr("class").equals("star")) {
					JStar star = new JStar();
					star.setId(JavStringUtil.getStarId(spans.get(i).select("a").first().attr("href")));
					star.setName(spans.get(i).text());
					star.setUrl(spans.get(i).select("a").first().attr("href"));
					// System.out.println("starId:" +
					// JavStringUtil.getStarId(spans.get(i).select("a").first().attr("href")));
					// System.out.println("starName:" + spans.get(i).text());
					// System.out.println("starUrl:" +
					// spans.get(i).select("a").first().attr("href"));
					int j = i + 1;
					List<String> alias = new ArrayList<String>();
					while (j < spans.size() && spans.get(j).attr("class").equals("alias")) {
						// System.out.println("别名:" + spans.get(j).text());
						alias.add(spans.get(j).text());
						j++;
					}
					star.setAlias(alias);
					stars.add(star);
					i = j - 1;
				}
			}
		} catch (Exception e) {
			throw new JavException(ErrorCode.FAIL, "获取Star错误");
		}
		return stars;
	}
}
