package util;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentUtil {
	public static Document getDoc(String url) throws Exception {
		Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
				.timeout(30000).post();
		return doc;
	}

	public static Document getDocFromLocal(String location) throws Exception {
		Document doc = Jsoup.parse(new File(location), "UTF-8");
		return doc;
	}
}
