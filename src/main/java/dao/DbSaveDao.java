package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.wnc.basic.BasicStringUtil;

import bean.JStar;
import bean.JTag;
import bean.JVideo;
import db.DBconnectionMgr;
import db.DbExecMgr;
import db.DbField;
import db.DbFieldSqlUtil;

public class DbSaveDao {
	static Connection con = DBconnectionMgr.getConnection();

	public static void insertWithStar(JStar star) throws Exception {
		DbFieldSqlUtil util = new DbFieldSqlUtil("JSTAR", "");
		util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(star.getId()), "STRING"));
		util.addInsertField(new DbField("NAME", "" + StringEscapeUtils.escapeSql(star.getName()), "STRING"));
		util.addInsertField(new DbField("URL", "" + StringEscapeUtils.escapeSql(star.getUrl()), "STRING"));
		util.addInsertField(new DbField("ALIAS", "" + getStarAliasContent(star.getAlias()), "STRING"));
		System.out.println("插入Star:" + util.getInsertSql());
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
		System.out.println("插入Video:" + util.getInsertSql());
		DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
		// saveVideoMakeDesc(video);
	}

	public static void saveMakeDesc(String director, String maker, String label, String tags) throws SQLException {
		DbFieldSqlUtil util;
		if (BasicStringUtil.isNotNullString(director)) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(director), "STRING"));
			util.addInsertField(new DbField("TYPE", "1", "STRING"));
			try {
				DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
			} catch (Exception e) {
			}
		}

		if (BasicStringUtil.isNotNullString(maker)) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(maker), "STRING"));
			util.addInsertField(new DbField("TYPE", "2", "STRING"));
			try {
				DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
			} catch (Exception e) {
			}
		}

		if (BasicStringUtil.isNotNullString(label)) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(label), "STRING"));
			util.addInsertField(new DbField("TYPE", "3", "STRING"));
			try {
				DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
			} catch (Exception e) {
			}
		}
		if (BasicStringUtil.isNotNullString(tags)) {
			for (String tag : tags.split(",")) {
				util = new DbFieldSqlUtil("JMAKEDESC", "");
				util.addInsertField(new DbField("ID", "" + StringEscapeUtils.escapeSql(tag), "STRING"));
				util.addInsertField(new DbField("TYPE", "4", "STRING"));
				System.out.println("Tag: " + util.getInsertSql());
				try {
					DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 保存Maker,Label,Director到本地,他们可以放一个表里
	 * <p>
	 * 一次完成后 暂时不维护
	 * <p>
	 * 别名也要在这儿处理, 更新到jstar表
	 * 
	 * @param video
	 * @throws Exception
	 */
	private static void saveVideoMakeDesc(JVideo video) throws Exception {
		DbFieldSqlUtil util = null;
		if (video.getMaker() != null) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(
					new DbField("ID", "" + StringEscapeUtils.escapeSql(video.getMaker().getId()), "STRING"));
			util.addInsertField(
					new DbField("NAME", "" + StringEscapeUtils.escapeSql(video.getMaker().getName()), "STRING"));
			util.addInsertField(
					new DbField("URL", "" + StringEscapeUtils.escapeSql(video.getMaker().getUrl()), "STRING"));
			util.addInsertField(new DbField("TYPE", "2", "STRING"));
			DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
		}
		if (video.getLabel() != null) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(
					new DbField("ID", "" + StringEscapeUtils.escapeSql(video.getLabel().getId()), "STRING"));
			util.addInsertField(
					new DbField("NAME", "" + StringEscapeUtils.escapeSql(video.getLabel().getName()), "STRING"));
			util.addInsertField(
					new DbField("URL", "" + StringEscapeUtils.escapeSql(video.getLabel().getUrl()), "STRING"));
			util.addInsertField(new DbField("TYPE", "3", "STRING"));
			DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
		}
		if (video.getDirector() != null) {
			util = new DbFieldSqlUtil("JMAKEDESC", "");
			util.addInsertField(
					new DbField("ID", "" + StringEscapeUtils.escapeSql(video.getDirector().getId()), "STRING"));
			util.addInsertField(
					new DbField("NAME", "" + StringEscapeUtils.escapeSql(video.getDirector().getName()), "STRING"));
			util.addInsertField(
					new DbField("URL", "" + StringEscapeUtils.escapeSql(video.getDirector().getUrl()), "STRING"));
			util.addInsertField(new DbField("TYPE", "1", "STRING"));
			DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
		}
		if (video.getTags() != null) {

		}

		// if (video.getStars() != null) {
		// for (JStar star : video.getStars()) {
		// if (star.getAlias() != null && star.getAlias().size() > 0) {
		// util = new DbFieldSqlUtil("JSTAR", "");
		// util.addUpdateField(new DbField("ALIAS",
		// "" + StringEscapeUtils.escapeSql(star.getAlias().toString()),
		// "STRING"));
		// util.addUpdateField(
		// new DbField("NAME", "" + StringEscapeUtils.escapeSql(star.getName()),
		// "STRING"));
		// util.addWhereField(new DbField("ID", "" +
		// StringEscapeUtils.escapeSql(star.getId()), "STRING"));
		// DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
		// }
		// }
		// }

	}

	public static void insertStarAlias(String starId, String alias) throws SQLException {
		DbFieldSqlUtil util = new DbFieldSqlUtil("JSTAR", "");
		if (alias != null) {
			util.addUpdateField(new DbField("ALIAS", "" + StringEscapeUtils.escapeSql(alias), "STRING"));
			util.addWhereField(new DbField("ID", "" + StringEscapeUtils.escapeSql(starId), "STRING"));
			System.out.println(util.getUpdateSql());
			DbExecMgr.execOnlyOneUpdate(util.getUpdateSql());
		}
	}

	public static void updateDescName(String descId, String descName, int type) throws SQLException {
		DbFieldSqlUtil util = new DbFieldSqlUtil("JMAKEDESC", "");
		if (BasicStringUtil.isNotNullString(descName)) {
			util.addUpdateField(new DbField("NAME", "" + StringEscapeUtils.escapeSql(descName), "STRING"));
			util.addWhereField(new DbField("ID", "" + StringEscapeUtils.escapeSql(descId), "STRING"));
			util.addWhereField(new DbField("TYPE", "" + type, "NUMBER"));
			System.out.println(util.getUpdateSql());
			DbExecMgr.execOnlyOneUpdate(util.getUpdateSql());
		}
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
			// AliasHelper.writeAlias(star);
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(starIds, ","));
	}

}
