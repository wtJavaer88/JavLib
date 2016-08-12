package client;

import java.io.File;
import java.util.Map;

import bean.JVideo;
import db.DbExecMgr;
import service.VideoService;

public class CheckSmallPics {
	public static void main(String[] args) throws Exception {
		File[] picFiles = new File("D:\\txt\\javlib\\pic\\").listFiles();
		int count = 0;
		for (File file : picFiles) {
			if (file.length() < 10 * 1024) {
				System.out.println(count++ + file.getName());
				Map selectSqlMap = DbExecMgr.getSelectSqlMap(
						"SELECT URL,IMAGE FROM jvideo where id = '" + file.getName().replace(".jpg", "") + "'");
				JVideo video = new VideoService().getVideo(selectSqlMap.get("URL").toString());
				System.out.println(video.getImage());
			}
		}
	}
}
