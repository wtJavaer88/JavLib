package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VideoQueue {
	static Set<String> videoUrls = Collections.synchronizedSet(new HashSet<String>());

	public static boolean addVideoUrl(String videoUrl) {
		return videoUrls.add(videoUrl);
	}
}
