package db;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import bean.JStar;
import dao.DbSaveDao;
import service.JStarsService;
import service.Logger;
import util.CollectionUtil;

public class TestStarDb {

	@Test
	// 找出所有的Star信息并插入数据库
	public void t2() throws IOException {

		try {
			Map map = DbExecMgr.getSelectAllSqlMap("SELECT ID from JSTAR");
			int size = map.size();
			Map rowMap;
			System.out.println(size);
			Set<String> set = new HashSet<String>();
			for (int i = 1; i <= size; i++) {
				rowMap = (Map) map.get(i);
				final String starId = String.valueOf(rowMap.get("ID"));
				set.add(starId);
			}
			System.out.println(set.size());

			List<JStar> stars = new JStarsService().getAll();
			System.out.println(stars.size());
			stars = CollectionUtil.removeDuplicateWithOrder(stars);
			System.out.println(stars.size());
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
