package client.srcfill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import dao.DbSaveDao;
import db.DbExecMgr;

public class GetSrcInfoFromVideos {
	public static void main(String[] args) {
		// DbSaveDao.saveMakeDesc("a", "b", "c", "x,y,z");
		Map map = DbExecMgr.getSelectAllSqlMap("SELECT DIRECTOR,MAKER,LABEL,TAGS from JVIDEO ORDER BY ID ASC ");
		int size = map.size();
		Map rowMap;
		System.out.println(size);
		List<String> allVideos = new ArrayList<String>();
		for (int i = 1; i <= size; i++) {
			rowMap = (Map) map.get(i);
			final String vId = String.valueOf(rowMap.get("ID"));
			check(String.valueOf(rowMap.get("DIRECTOR")), String.valueOf(rowMap.get("MAKER")),
					String.valueOf(rowMap.get("LABEL")), String.valueOf(rowMap.get("TAGS")));

		}
		executor.shutdown();
	}

	static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

	public static void check(final String director, final String maker, final String label, final String tags) {
		try {
			executor.execute(new Runnable() {
				public void run() {
					try {
						DbSaveDao.saveMakeDesc(director, maker, label, tags);
					} catch (SQLException e) {
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
