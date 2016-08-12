import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.wnc.basic.BasicFileUtil;
import com.wnc.basic.BasicStringUtil;
import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;

import bean.JVideo;
import db.DbExecMgr;
import service.VideoService;

public class OtherTest {
	@Test
	public void t2() throws Exception {

		System.out.println(PatternUtil.getLastPattern("http://j8vlib.com/cn/vl_star.php?s=ci&page=2", "s=.*&")
				.replace("s=", "").replace("&", ""));
		System.out.println(PatternUtil.getLastPattern("http://j8vlib.com/cn/vl_star.php?s=ci", "s=.*&?")
				.replace("s=", "").replace("&", ""));
		System.out.println(BasicStringUtil.removeRightString("abbb,dd,d,d,", ","));
		// AliasHelper.init();
		System.out.println(PatternUtil.getFirstPattern("adsf组组长郭", "[\u4e00-\u9fa5]"));
		System.out.println(PatternUtil.getFirstPattern("AAA-004 完全", ".*?\\s").trim());

		checkErrorImages();
		// checkVideoAvop("http://j8vlib.com/cn/?v=javliizwz4");
		// checkEmptyImages();

	}

	private void checkEmptyImages() {
		String p = "D:\\txt\\javlib\\pic\\";
		for (File f : new File(p).listFiles()) {
			if (f.length() == 0) {
				String id = PatternUtil.getFirstPattern(f.getName(), ".*?\\d+");
				System.out.println("Empty:" + id);
				Map selectSqlMap = DbExecMgr.getSelectSqlMap("SELECT * FROM JVIDEO WHERE ID='" + id + "'");
				System.out.println(selectSqlMap.get("IMAGE"));
				BasicFileUtil.writeFileString("uncomplete.txt", selectSqlMap.get("IMAGE") + "\r\n", null, true);
			}
		}
	}

	private void checkVideoAvop(String string) throws Exception {
		JVideo video = new VideoService().getVideo(string);
		System.out.println(video.getImage());
	}

	private void checkErrorImages() {
		String p = "D:\\Users\\wnc\\Programs\\sts-bundle\\projects\\javlib\\log.txt";
		List<String> readFrom = FileOp.readFrom(p, "UTF-8");
		int count = 0;
		for (String string : readFrom) {
			if (string.contains("未完成")) {
				System.out.println("GOOD" + string);
				count++;
				BasicFileUtil.writeFileString("uncomplete.txt",
						PatternUtil.getFirstPattern(string, "http:.*?\\s").trim() + "\r\n", null, true);
			}
		}
		System.out.println(count);
	}
}
