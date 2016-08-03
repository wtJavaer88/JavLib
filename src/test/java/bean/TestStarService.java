package bean;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import service.JStarsService;
import service.StarService;
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

	@Test
	public void t2() throws IOException {

		try {
			List<JStar> stars = new JStarsService().getAll();
			System.out.println(stars.size() + " " + stars);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
