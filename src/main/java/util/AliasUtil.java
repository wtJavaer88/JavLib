package util;

import java.util.HashSet;
import java.util.Set;

import com.wnc.basic.BasicFileUtil;

import bean.JStar;

public class AliasUtil {
	static Set<String> set = new HashSet<String>();

	public static void writeAlias(JStar star) {
		if (star != null && star.getAlias() != null && star.getAlias().size() > 0) {
			String aliasID = star.getId();
			if (set.add(aliasID)) {
				BasicFileUtil.writeFileString("alias.txt",
						star.getId() + ">" + star.getName() + ">" + star.getAlias().toString() + "\r\n", "UTF-8", true);
			}
		}
	}
}
