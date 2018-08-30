package com.wnc.javlib.service;

import com.wnc.basic.BasicDateUtil;
import com.wnc.basic.BasicNumberUtil;
import com.wnc.javlib.entity.*;
import com.wnc.string.PatternUtil;
import common.spider.node.MyElement;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieDetailImpl implements IMovieService {

	public JMovie getMovieDetail(Document doc) {
		JMovie movie = new JMovie();
		movie.setMovieCode(doc.select("#video_id .text").text().trim());
		movie.setStars(getStars(doc));
		if (movie.getStars().size() > 1) {
			movie.setSingleStar('N');
		} else {
			movie.setSingleStar('Y');
		}
		movie.setImg(getImg(doc));
		movie.setLength(getLength(doc));

		movie.setMakeDescs(getMovieMakeDescList(doc));
		for (JMakeDesc md : movie.getMakeDescs()) {
			if (md.getMdEnum().getType() == 1) {
				movie.setDirector(md.getMdCode());
			}
			if (md.getMdEnum().getType() == 2) {
				movie.setMaker(md.getMdCode());
			}
			if (md.getMdEnum().getType() == 3) {
				movie.setLabel(md.getMdCode());
			}
		}

		movie.setPublishDate(getPublishDate(doc));
		movie.setScore(getScore(doc));
		movie.setTags(getTags(doc));
		movie.setCnComments(doc.select("#video_comments .comment").size());
		movie.setCnReviews(doc.select("#video_reviews .review").size());

		movie.setLastUpdateDate(BasicDateUtil.getCurrentDateString());

		getFavInfo(doc, movie);

		return movie;
	}

	public List<JStar> getStars(Document doc) {
		List<JStar> stars = new ArrayList<JStar>();
		Elements select = doc.select("#video_cast .cast");
		for (Element element : select) {
			JStar star = new JStar();
			Element starUrl = element.select(".star a").first();
			if (starUrl == null) {
				continue;
			}
			star.setStarCode(new MyElement(starUrl).pattern4Attr("href", "s=(.*+)"));
			Elements select2 = element.select(".alias");
			List<String> alias = new ArrayList<String>();
			for (Element element2 : select2) {
				alias.add(element2.text().trim());
			}
			star.setAliasName(alias);
			stars.add(star);
		}
		return stars;
	}

	public void getFavInfo(Document doc, JMovie mv) {
		mv.setWantCount(BasicNumberUtil.getNumber(
				PatternUtil.getFirstPatternGroup(doc.select("#video_favorite_edit #subscribed").text(), "(\\d+)")));
		mv.setViewedCount(BasicNumberUtil.getNumber(
				PatternUtil.getFirstPatternGroup(doc.select("#video_favorite_edit #watched").text(), "(\\d+)")));
		mv.setOwnCount(BasicNumberUtil.getNumber(
				PatternUtil.getFirstPatternGroup(doc.select("#video_favorite_edit #owned").text(), "(\\d+)")));
	}

	public int getLength(Document doc) {
		String str = doc.select("#video_length .text").text();
		return BasicNumberUtil.getNumber(PatternUtil.getFirstPatternGroup(str, "(\\d+)"));
	}

	public String getImg(Document doc) {
		String str = doc.select("img#video_jacket_img").attr("onerror");
		return PatternUtil.getFirstPatternGroup(str, "'//(.*?)'");
	}

	public String getPublishDate(Document doc) {
		String str = doc.select("#video_date .text").text();
		return PatternUtil.getFirstPatternGroup(str, "(\\d{4}-\\d{2}-\\d{2})");
	}

	public double getScore(Document doc) {
		String str = doc.select(".score").text();
		return BasicNumberUtil.getDouble(PatternUtil.getFirstPatternGroup(str, "([\\d\\.]+)"));
	}

	public List<JTag> getTags(Document doc) {
		Elements select = doc.select("#video_genres a");
		List<JTag> tags = new ArrayList<JTag>();
		for (Element element : select) {
			tags.add(new JTag(new MyElement(element).pattern4Attr("href", "g=(.*+)"), element.text()));
		}
		return tags;
	}

	public List<JMakeDesc> getMovieMakeDescList(Document doc) {
		List<JMakeDesc> makedescs = new ArrayList<JMakeDesc>();
		makedescs.addAll(getMd(doc.select("#video_director .director a"), MakeDescEnum.DIRECTOR));
		makedescs.addAll(getMd(doc.select("#video_maker .maker a"), MakeDescEnum.MAKER));
		makedescs.addAll(getMd(doc.select("#video_label .label a"), MakeDescEnum.LABEL));
		return makedescs;
	}

	private Collection<? extends JMakeDesc> getMd(Elements select, MakeDescEnum mdEnum) {
		List<JMakeDesc> result = new ArrayList<JMakeDesc>();
		for (Element element : select) {
			result.add(new JMakeDesc(mdEnum, new MyElement(element).pattern4Attr("href", "=(.*+)"), element.text()));
		}
		return result;
	}

}
