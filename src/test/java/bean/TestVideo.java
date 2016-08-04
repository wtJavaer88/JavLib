package bean;

import org.junit.Test;

import service.VideoService;

public class TestVideo {
	@Test
	public void t() throws Exception {
		JVideo video = new VideoService().getVideo("http://j8vlib.com/cn/?v=javlidr56q");
		System.out.println(video.getId() + " " + video.getImage());
	}
}
