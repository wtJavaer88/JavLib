package client.db;

import java.sql.SQLException;
import java.util.List;

import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;

import dao.DbSaveDao;

public class AliasToDb {
	public static void main(String[] args) throws SQLException {
		List<String> readFrom = FileOp.readFrom("D:\\Users\\wnc\\Programs\\sts-bundle\\projects\\javlib\\alias.txt",
				"UTF-8");
		for (String string : readFrom) {
			String starId = PatternUtil.getFirstPattern(string, ".*?>").replace(">", "");
			String alias = PatternUtil.getFirstPattern(string, ">.*+").replace(">", "");
			System.out.println(starId);
			System.out.println(alias);
			DbSaveDao.insertStarAlias(starId, alias);
		}
	}
}
