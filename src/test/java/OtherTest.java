import java.io.IOException;

import org.junit.Test;

import com.wnc.basic.BasicStringUtil;
import com.wnc.string.PatternUtil;

public class OtherTest {
	@Test
	public void t2() throws IOException {

		System.out.println(PatternUtil.getLastPattern("http://j8vlib.com/cn/vl_star.php?s=ci&page=2", "s=.*&")
				.replace("s=", "").replace("&", ""));
		System.out.println(PatternUtil.getLastPattern("http://j8vlib.com/cn/vl_star.php?s=ci", "s=.*&?")
				.replace("s=", "").replace("&", ""));
		System.out.println(BasicStringUtil.removeRightString("abbb,dd,d,d,", ","));
	}
}
