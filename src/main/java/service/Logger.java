package service;

import com.wnc.basic.BasicFileUtil;
import com.wnc.basic.BasicStringUtil;
import com.wnc.string.PatternUtil;

public class Logger {
	public static void logByStarOrTagId(String url, String msg) {
		String star = PatternUtil.getLastPattern(url, "s=.*&?").replace("s=", "").replace("&", "");
		if (BasicStringUtil.isNotNullString(star)) {
			BasicFileUtil.writeFileString("D:\\txt\\javlib\\star\\" + star + ".txt", msg + "\r\n", "UTF-8", true);
		} else {
			String tag = PatternUtil.getLastPattern(url, "g=.*&?").replace("s=", "").replace("&", "");
			if (BasicStringUtil.isNotNullString(tag)) {
				BasicFileUtil.writeFileString("D:\\txt\\javlib\\tag\\" + tag + ".txt", msg + "\r\n", "UTF-8", true);
			} else {
				BasicFileUtil.writeFileString("D:\\txt\\javlib\\unkown.txt", msg + "\r\n", "UTF-8", true);
			}
		}

	}

	public static void logByPrefix(String url, String msg) {
		String prefix = PatternUtil.getLastPattern(url, "[A-Z]+");
		BasicFileUtil.writeFileString(prefix + ".txt", msg + "\r\n", "UTF-8", true);
	}

	public static void logGlobal(String msg) {
		BasicFileUtil.writeFileString("log.txt", msg + "\r\n", "UTF-8", true);
	}

}
