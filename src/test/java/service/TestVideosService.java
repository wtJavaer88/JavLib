package service;

import java.io.IOException;

import org.junit.Test;

public class TestVideosService {
	@Test
	public void t() throws IOException {

		try {
			new JVideosService().getAll("http://j8vlib.com/cn/vl_star.php?s=ci");
			System.out.println(new VideoService().getVideo("http://j8vlib.com/cn/?v=javlild62i"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
