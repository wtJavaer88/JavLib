package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import bean.JStar;
import bean.JTag;
import bean.JVideo;
import db.DBconnectionMgr;
import db.DbExecMgr;
import db.DbField;
import db.DbFieldSqlUtil;
import util.AliasUtil;

public class DbSaveDao {
	static Connection con = DBconnectionMgr.getConnection();

	public static void insertWithStar(JStar star) throws Exception {
		DbFieldSqlUtil util = new DbFieldSqlUtil("JSTAR", "");
		util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(star.getId()), "STRING"));
		util.addInsertField(new DbField("NAME", "" + StringEscapeUtils.escapeSql(star.getName()), "STRING"));
		util.addInsertField(new DbField("URL", "" + StringEscapeUtils.escapeSql(star.getUrl()), "STRING"));
		util.addInsertField(new DbField("ALIAS", "" + getStarAliasContent(star.getAlias()), "STRING"));
		DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
	}

	public static void insertWithVideo(JVideo video) throws Exception {
		// JVideo video = new
		// VideoService().getVideo("http://j8vlib.com/cn/?v=javlild62i");
		DbFieldSqlUtil util = new DbFieldSqlUtil("JVIDEO", "");
		util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(video.getId()), "STRING"));
		util.addInsertField(new DbField("NAME", "" + StringEscapeUtils.escapeSql(video.getName()), "STRING"));
		util.addInsertField(new DbField("DATE", "" + StringEscapeUtils.escapeSql(video.getDate()), "STRING"));
		util.addInsertField(new DbField("URL", "" + StringEscapeUtils.escapeSql(video.getUrl()), "STRING"));
		util.addInsertField(new DbField("SCORE", "" + video.getScore(), "STRING"));
		util.addInsertField(new DbField("LENGTH", "" + video.getLength(), "STRING"));
		util.addInsertField(new DbField("IMAGE", "" + StringEscapeUtils.escapeSql(video.getImage()), "STRING"));
		util.addInsertField(new DbField("MAKER",
				"" + StringEscapeUtils.escapeSql(video.getMaker() == null ? null : video.getMaker().getId()),
				"STRING"));
		util.addInsertField(new DbField("LABEL",
				"" + StringEscapeUtils.escapeSql(video.getLabel() == null ? null : video.getLabel().getId()),
				"STRING"));
		util.addInsertField(new DbField("DIRECTOR",
				"" + StringEscapeUtils.escapeSql(video.getDirector() == null ? null : video.getDirector().getId()),
				"STRING"));
		util.addInsertField(new DbField("STARS", "" + getStarsContent(video.getStars()), "STRING"));
		util.addInsertField(new DbField("TAGS", "" + getTagsContent(video.getTags()), "STRING"));
		DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
	}

	private static String getStarAliasContent(List<String> alias) {
		if (alias == null) {
			return null;
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(alias, ","));
	}

	private static String getTagsContent(List<JTag> tags) {
		if (tags == null) {
			return null;
		}
		List<String> tagIds = new ArrayList<String>();
		for (JTag tag : tags) {
			tagIds.add(tag.getId());
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(tagIds, ","));
	}

	/**
	 * 拼出Star的id,然后输出别名到文件,以后再处理
	 * 
	 * @param stars
	 * @return
	 */
	private static String getStarsContent(List<JStar> stars) {
		if (stars == null) {
			return null;
		}
		List<String> starIds = new ArrayList<String>();
		for (JStar star : stars) {
			starIds.add(star.getId());
			AliasUtil.writeAlias(star);
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(starIds, ","));
	}

}
