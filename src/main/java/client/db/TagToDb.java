package client.db;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.wnc.string.PatternUtil;

import dao.DbSaveDao;
import db.DbExecMgr;
import util.DocumentUtil;

public class TagToDb {
	public static void main(String[] args) throws Exception {
		Map map = DbExecMgr.getSelectAllSqlMap("SELECT * FROM JMAKEDESC WHERE NAME IS NULL");
		int size = map.size();
		Map rowMap;
		System.out.println(size);
		for (int i = 1; i <= size; i++) {
			rowMap = (Map) map.get(i);
			final int type = Integer.parseInt(String.valueOf(rowMap.get("TYPE")));
			final String descId = String.valueOf(rowMap.get("ID"));
			String name = "";
			switch (type) {
			case 1:
				name = getDirectorName(descId);
				break;
			case 2:
				name = getMakerName(descId);
				break;
			case 3:
				name = getLabelName(descId);
				break;
			case 4:
				name = getTagName(descId);
				break;
			}
			System.out.println(name);
			DbSaveDao.updateDescName(descId, name, type);
		}
	}

	private static String getTagName(String tagId) throws Exception {
		String url = "http://j8vlib.com/cn/vl_genre.php?g=" + tagId;
		Document doc = DocumentUtil.getDoc(url);
		return PatternUtil.getFirstPattern(doc.title(), ".*?\\s").trim();
	}

	private static String getLabelName(String labelId) throws Exception {
		String url = "http://j8vlib.com/cn/vl_label.php?l=" + labelId;
		Document doc = DocumentUtil.getDoc(url);
		return PatternUtil.getFirstPattern(doc.title(), ".*?\\s").trim();
	}

	// =aq7a
	private static String getMakerName(String makerId) throws Exception {
		String url = "http://j8vlib.com/cn/vl_maker.php?m=" + makerId;
		Document doc = DocumentUtil.getDoc(url);
		return PatternUtil.getFirstPattern(doc.title(), ".*?\\s").trim();
	}

	private static String getDirectorName(String drId) throws Exception {
		String url = "http://j8vlib.com/cn/vl_director.php?d=" + drId;
		Document doc = DocumentUtil.getDoc(url);
		return PatternUtil.getFirstPattern(doc.title(), ".*?\\s").trim();
	}
}
