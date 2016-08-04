package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import bean.JStar;
import bean.JTag;
import bean.JVideo;
import service.VideoService;

public class DbTest {
	// @Test
	public void conn() throws SQLException {
		Connection connection = DBconnectionMgr.getConnection();
		System.out.println(connection);

		DbFieldSqlUtil util = new DbFieldSqlUtil("JSTAR", "");
		util.addInsertField(new DbField("URL", "" + "WWW.A.COM", "STRING"));
		util.addInsertField(new DbField("NAME", "" + "WNC", "STRING"));
		DbExecMgr.execOnlyOneUpdate(util.getInsertSql());
	}

	@Test
	public void insertWithVideo() throws Exception {
		Connection connection = DBconnectionMgr.getConnection();
		System.out.println(connection);

		JVideo video = new VideoService().getVideo("http://j8vlib.com/cn/?v=javlidyi44");
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

	private String getTagsContent(List<JTag> tags) {
		if (tags == null) {
			return null;
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(tags, ","));
	}

	private String getStarsContent(List<JStar> stars) {
		if (stars == null) {
			return null;
		}
		return StringEscapeUtils.escapeSql(StringUtils.join(stars, ","));
	}

}
