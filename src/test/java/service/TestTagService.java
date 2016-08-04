package service;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import bean.JTag;

public class TestTagService {

	@Test
	public void t() throws IOException {
		try {
			List<JTag> tags = new JTagsService().getAll();
			for (JTag tag : tags) {
				System.out.println(tag.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
