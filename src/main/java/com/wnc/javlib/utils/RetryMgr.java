package com.wnc.javlib.utils;

import com.wnc.basic.BasicDateUtil;
import com.wnc.basic.BasicFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理每个http请求url的重试次数, 默认不超过3次
 */
public class RetryMgr {
	public static final int DEFAULT_RETRY_TIMES = 3;
	public static Map<String, Integer> retryMap = new HashMap<String, Integer>(200);

	public static boolean addUrlAndStop(String url) {
		return addUrlAndStop(url, DEFAULT_RETRY_TIMES);
	}

	public static boolean addUrlAndStop(String url, int retryTimes) {
		int k = 1;
		if (retryMap.containsKey(url)) {
			k = retryMap.get(url) + 1;
		}
		retryMap.put(url, k);

		return k > retryTimes;
	}

	public static void logTimeout(String url) {
		BasicFileUtil.writeFileString(
				spiderqueue.util.QueueConfig.logPath + "retrylog-" + BasicDateUtil.getCurrentDateString() + ".txt",
				url + "\r\n", null, true);
	}
}
