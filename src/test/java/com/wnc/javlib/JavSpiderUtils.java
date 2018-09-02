package com.wnc.javlib;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wnc.basic.BasicNumberUtil;
import com.wnc.string.PatternUtil;

public class JavSpiderUtils {
	public static int getMaxPage(Document parse) throws IOException {
		int pages = 0;
		Elements select = parse.select(".page_selector > .page");
		if (select.size() > 0) {
			for (Element element : select) {
				int num = BasicNumberUtil.getNumber(PatternUtil.getLastPattern(element.attr("href"), "\\d+"));
				// 直接从所有page中找最大值
				if (num > pages) {
					pages = num;
				}
			}
		} else if (parse.select(".videos > .video").size() > 0) {
			pages = 1;
		}
		return pages;
	}
}
