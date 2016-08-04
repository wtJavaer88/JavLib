package service;

import java.util.List;

import com.wnc.basic.BasicStringUtil;

import bean.JStar;

public class StarTask implements Runnable {
	JStarsService starService;
	String url;

	/**
	 * 获取某一页的所有明星
	 * 
	 * @param starService
	 * @param url
	 */
	public StarTask(JStarsService starService, String url) {
		this.starService = starService;
		this.url = url;
		Logger.logByPrefix(url, "线程开始");

	}

	public void run() {
		try {
			List<JStar> all = starService.getAll(url);
			starService.appendStars(all);
			Logger.logByPrefix(url, "getAll结果集大小:" + all.size());
			Logger.logGlobal(url + "结果集大小:" + all.size());

			String allResultName = "";
			for (JStar star : all) {
				allResultName += star.getName() + ",";
			}
			allResultName = BasicStringUtil.removeRightString(allResultName, ",");
			Logger.logByPrefix(url, "getAll结果:" + allResultName);
		} catch (Exception e) {
			Logger.logByPrefix(url, "getAll失败:" + e.getMessage());
		}
	}
}
