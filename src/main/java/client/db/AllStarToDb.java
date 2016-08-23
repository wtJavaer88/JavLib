package client.db;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bean.JStar;
import dao.DbSaveDao;
import db.DbExecMgr;
import service.JStarsService;
import service.Logger;
import util.CollectionUtil;

public class AllStarToDb {
	public static void main(String[] args) {
		try {
			Map map = DbExecMgr.getSelectAllSqlMap("SELECT ID from JSTAR");
			int size = map.size();
			Map rowMap;
			System.out.println("数据库已有数据:" + size);
			// 首先找出数据库已有的数据
			Set<String> set = new HashSet<String>();
			for (int i = 1; i <= size; i++) {
				rowMap = (Map) map.get(i);
				final String starId = String.valueOf(rowMap.get("ID"));
				set.add(starId);
			}
			System.out.println(set.size());
			// 再从网站中找到所有数据
			List<JStar> stars = new JStarsService().getAll();
			System.out.println("网站数据:" + stars.size());
			stars = CollectionUtil.removeDuplicateWithOrder(stars);
			System.out.println("网站去重后的数据:" + stars.size());
			for (JStar star : stars) {
				try {
					if (!set.contains(star.getId())) {
						System.out.println(star.getId() + " not save");
						DbSaveDao.insertWithStar(star);
						set.add(star.getId());
						System.out.println(star.getId() + " save success!");
					}
				} catch (Exception e) {
					Logger.logGlobal(star.getName() + "插入数据库失败:" + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
