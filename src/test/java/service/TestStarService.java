package service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bean.JStar;
import util.DocumentUtil;

public class TestStarService {
	// @Test
	public void t() throws IOException {

		try {
			List<JStar> stars = new StarService()
					.getStars(DocumentUtil.getDoc("http://j8vlib.com/cn/star_list.php?prefix=A"));
			System.out.println(stars.size() + " " + stars);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	// 找出所有的Star信息
	public void t2() throws IOException {

		try {
			List<JStar> stars = new JStarsService().getAll();
			System.out.println(stars.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	// 对K开头的Star进行Video查找
	public void t4() throws IOException {
		try {
			List<JStar> stars = new JStarsService().getAll("http://j8vlib.com/cn/star_list.php?prefix=K");
			System.out.println(stars.size());
			Collections.sort(stars, new Comparator<JStar>() {
				public int compare(JStar o1, JStar o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
			for (JStar star : stars) {
				System.out.println(star.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
