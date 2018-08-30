package com.wnc.javlib.dao;

import com.wnc.basic.BasicFileUtil;
import com.wnc.javlib.entity.JMakeDesc;
import com.wnc.javlib.entity.JMovie;
import com.wnc.javlib.entity.JStar;
import com.wnc.javlib.entity.JTag;
import db.DbExecMgr;
import db.DbField;
import db.DbFieldSqlUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.SQLException;

public class MvDetailDao {
	public static boolean saveMovieDetailDb(JMovie jMovie) {

		String movieCode = jMovie.getMovieCode();
		System.out.println("更新数据库..." + movieCode);
		try {
			updateMovie(jMovie, movieCode);

			for (JMakeDesc md : jMovie.getMakeDescs()) {
				if (!DbExecMgr.isExistData("SELECT 1 FROM JAV_MAKEDESC WHERE CODE = '" + md.getMdCode() + "' AND TYPE="
						+ md.getMdEnum().getType())) {
					System.out.println("新增md:" + md.getMdCode());
					execOnlyOneUpdate("INSERT INTO JAV_MAKEDESC(CODE,TYPE,NAME) VALUES('" + md.getMdCode() + "',"
							+ md.getMdEnum().getType() + ",'" + StringEscapeUtils.escapeSql(md.getMdName()) + "')");
				}

				execOnlyOneUpdate("INSERT INTO JAV_MV_MAKEDESCS(MV_CODE,MD_TYPE,MD_CODE) VALUES('" + movieCode + "',"
						+ md.getMdEnum().getType() + ",'" + md.getMdCode() + "')");
			}
			for (JStar star : jMovie.getStars()) {
				String starCode = star.getStarCode();
				execOnlyOneUpdate(
						"INSERT INTO JAV_MV_STAR(MV_CODE,STAR_CODE) VALUES('" + movieCode + "','" + starCode + "')");

				if (star.getAliasName() != null && !star.getAliasName().isEmpty()
						&& !DbExecMgr.isExistData("JAV_STAR_ALIAS", "STAR_CODE", starCode))
					for (String name : star.getAliasName()) {
						execOnlyOneUpdate("INSERT INTO JAV_STAR_ALIAS(STAR_CODE,ALIAS_NAME) VALUES('" + starCode + "','"
								+ StringEscapeUtils.escapeSql(name) + "')");
					}
			}
			for (JTag tag : jMovie.getTags()) {
				execOnlyOneUpdate("INSERT INTO JAV_MV_TAGS(MV_CODE,TAG_CODE) VALUES('" + movieCode + "','"
						+ tag.getTagCode() + "')");
				if (!DbExecMgr.isExistData("JAV_TAG", "TAG_CODE", tag.getTagCode())) {
					execOnlyOneUpdate("INSERT INTO JAV_TAG(TAG_CODE,TAG_NAME) VALUES('" + tag.getTagCode() + "','"
							+ tag.getTagName() + "')");
					System.out.println("新增标签:" + tag.getTagCode());
				}
			}

		} catch (Exception e) {
			BasicFileUtil.writeFileString("err-jav-db.txt", movieCode + " " + e.toString() + "\r\n", null, true);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static void execOnlyOneUpdate(String sql) throws SQLException {
		try {
			DbExecMgr.execOnlyOneUpdate(sql);
		} catch (SQLException e) {
			BasicFileUtil.writeFileString("err-jav.txt", sql + " " + e.toString() + "\r\n", null, true);
			// e.printStackTrace();
			throw e;
		}
	}

	private static void updateMovie(JMovie jMovie, String movieCode) throws SQLException {
		DbFieldSqlUtil util = new DbFieldSqlUtil("JAV_MOVIE");
		util.addUpdateField(new DbField("IMG", StringEscapeUtils.escapeSql(jMovie.getImg()), "STRING"));
		util.addUpdateField(new DbField("PUBLISH_DATE", jMovie.getPublishDate(), "STRING"));
		util.addUpdateField(new DbField("SCORE", "" + jMovie.getScore(), "NUMBER"));
		util.addUpdateField(new DbField("LENGTH", "" + jMovie.getLength(), "NUMBER"));
		util.addUpdateField(new DbField("OWN_COUNT", "" + jMovie.getOwnCount(), "NUMBER"));
		util.addUpdateField(new DbField("WANT_COUNT", "" + jMovie.getWantCount(), "NUMBER"));
		util.addUpdateField(new DbField("VIEWED_COUNT", "" + jMovie.getViewedCount(), "NUMBER"));
		util.addUpdateField(new DbField("CN_COMMENTS", "" + jMovie.getCnComments(), "NUMBER"));
		util.addUpdateField(new DbField("CN_REVIEWS", "" + jMovie.getCnReviews(), "NUMBER"));
		util.addUpdateField(new DbField("SINGLE_STAR", jMovie.getSingleStar() + "", "STRING"));
		util.addUpdateField(new DbField("DIRECTOR", jMovie.getDirector(), "STRING"));
		util.addUpdateField(new DbField("MAKER", jMovie.getMaker(), "STRING"));
		util.addUpdateField(new DbField("LABEL", jMovie.getLabel(), "STRING"));
		util.addUpdateField(new DbField("LAST_UPDATE_DATE", jMovie.getLastUpdateDate(), "STRING"));

		util.addWhereField(new DbField("MOVIE_CODE", movieCode, "STRING"));

		String updateSql = util.getUpdateSql();
		// System.out.println(updateSql);
		DbExecMgr.execOnlyOneUpdate(updateSql);
	}
}
