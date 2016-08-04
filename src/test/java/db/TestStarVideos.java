package db;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Test;

import bean.JVideo;
import dao.DbSaveDao;
import service.JVideosService;
import service.Logger;

public class TestStarVideos {

	/**
	 * 查找所有的明星的video并导入数据库
	 * 
	 * @throws IOException
	 */
	@Test
	public void t3() throws IOException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		try {
			Map map = DbExecMgr.getSelectAllSqlMap("SELECT ID from JSTAR ORDER BY ID ASC  limit 10 offset 0");
			int size = map.size();
			Map rowMap;
			System.out.println(size);
			for (int i = 1; i <= size; i++) {
				rowMap = (Map) map.get(i);
				final String starId = String.valueOf(rowMap.get("ID"));
				executor.execute(new VideosTask(starId));
				// List<JVideo> videos = new
				// JVideosService().getAllByStarId(starId);
				// System.out.println(starId + " videos数:" + videos.size());
			}
			executor.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class VideosTask implements Runnable {
		String starId;

		public VideosTask(String starId) {
			this.starId = starId;
		}

		public void run() {
			System.out.println("runAA:" + starId);
			try {
				List<JVideo> videos = new JVideosService().getAllByStarId(starId);
				System.out.println(starId + " videos数:" + videos.size());
				for (JVideo jVideo : videos) {
					// System.out.println(jVideo.getId() + " image:" +
					// jVideo.getImage());
					try {
						DbSaveDao.insertWithVideo(jVideo);
					} catch (Exception e) {
						Logger.logGlobal(jVideo.getId() + "插入数据库失败:" + e.getMessage());
						e.printStackTrace();
					}
				}
				System.out.println("线程:" + Thread.currentThread().getName() + " Star:" + starId + "保存完毕!");
				Logger.logGlobal("线程:" + Thread.currentThread().getName() + " Star:" + starId + "保存完毕!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
