package service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bean.JVideo;
import dao.DbSaveDao;
import db.DbExecMgr;

public class VideosTask implements Runnable {
	String starId;
	String starName;

	public VideosTask(String starId, String starName) {
		this.starId = starId;
		this.starName = starName;
	}

	public void run() {
		Logger.logGlobal("线程:" + Thread.currentThread().getName() + " Star:" + starId + "开始!");
		Map map = DbExecMgr.getSelectAllSqlMap("SELECT ID from JVIDEO WHERE STARS LIKE '%" + starId + "%'");
		int size = map.size();
		Map rowMap;
		System.out.println("数据库已有video数据:" + size);
		// 首先找出数据库已有的数据
		Set<String> set = new HashSet<String>();
		for (int i = 1; i <= size; i++) {
			rowMap = (Map) map.get(i);
			final String starId = String.valueOf(rowMap.get("ID"));
			set.add(starId);
		}

		try {
			List<JVideo> videos = new JVideosService().getAllByStarId(starId);
			System.out.println(starName + " videos数:" + videos.size());
			Logger.logGlobal(starName + " videos数:" + videos.size());
			for (JVideo jVideo : videos) {
				try {
					if (!set.contains(jVideo.getId())) {
						DbSaveDao.insertWithVideo(jVideo);
						System.out.println("不存在..." + jVideo.getId());
					} else {
						System.out.println("已存在...");
					}
				} catch (Exception e) {
					Logger.logGlobal(jVideo.getId() + "插入数据库失败:" + e.getMessage());
					e.printStackTrace();
				}
			}
			System.out.println("线程:" + Thread.currentThread().getName() + " Star:" + starId + "保存完毕!");
			Logger.logGlobal("线程:" + Thread.currentThread().getName() + " Star:" + starId + "保存完毕!");
		} catch (Exception e) {
			Logger.logGlobal("线程:" + Thread.currentThread().getName() + "异常:" + e.getMessage());
			e.printStackTrace();
		}
	}

}