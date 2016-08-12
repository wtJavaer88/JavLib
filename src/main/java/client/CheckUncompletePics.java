package client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.wnc.basic.BasicStringUtil;
import com.wnc.tools.FileOp;

import db.DbExecMgr;
import service.DownPicTask;

public class CheckUncompletePics {

	public static void main(String[] args) {
		String p = "D:\\Users\\wnc\\Programs\\sts-bundle\\projects\\javlib\\uncomplete.txt";
		List<String> readFrom = FileOp.readFrom(p, "UTF-8");
		for (String image : readFrom) {
			if (BasicStringUtil.isNullString(image)) {
				continue;
			}
			Map selectSqlMap = DbExecMgr.getSelectSqlMap("SELECT * FROM JVIDEO WHERE image='" + image + "'");
			// check(vId);
			// System.out.println(selectSqlMap);
			if (!image.equals("http://j8vlib.com/img/noimagepl.gif")) {
				check(selectSqlMap.get("ID").toString(), selectSqlMap.get("IMAGE").toString());
			}
		}
		executor.shutdown();

	}

	static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	public static void check(String id, String image) {
		// System.out.println(jVideo.getName() + " " + jVideo.getImage());
		try {
			executor.execute(new DownPicTask(id, image));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
