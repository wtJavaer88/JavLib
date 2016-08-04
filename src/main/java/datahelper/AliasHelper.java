package datahelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wnc.basic.BasicFileUtil;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;

import bean.JStar;

public class AliasHelper {
	static Set<String> set = new HashSet<String>();

	public static void init() {
		List<String> readFrom = FileOp.readFrom("D:\\Users\\wnc\\Programs\\sts-bundle\\projects\\javlib\\alias.txt",
				"UTF-8");
		for (String line : readFrom) {
			String starID = PatternUtil.getFirstPattern(line, ".*?\\>").replace(">", "");
			System.out.println(starID);
			set.add(starID);
		}
		System.out.println(set);
	}

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
