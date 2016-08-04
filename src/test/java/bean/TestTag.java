package bean;

import java.util.List;

import org.junit.Test;

import service.JTagsService;

public class TestTag {
	@Test
	public void t() throws Exception {
		List<JTag> tags = new JTagsService().getAll();
		for (JTag jTag : tags) {
			System.out.println(jTag.getId() + " " + jTag.getName());
		}
		System.out.println(tags.size());
	}
}
