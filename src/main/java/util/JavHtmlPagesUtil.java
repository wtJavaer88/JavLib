package util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.wnc.basic.BasicNumberUtil;
import com.wnc.string.PatternUtil;

public class JavHtmlPagesUtil {
	public static int getPagesFromDom(Document doc) {
		if (doc != null) {
			Elements select = doc.select(".page_selector a");
			if (select != null && select.last() != null) {
				String lastHref = select.last().attr("href");
				return BasicNumberUtil.getNumber(PatternUtil.getLastPattern(lastHref, "\\d+"));
			}
			// 如果只有一页,没有那么多选项
			return 1;
		}
		return 0;
	}
}
