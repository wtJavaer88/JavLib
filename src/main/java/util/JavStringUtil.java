package util;

import common.ErrorCode;
import common.JavException;

public class JavStringUtil {
	public static String getStarId(String starLink) {
		int i = starLink.lastIndexOf("=");
		if (i == 0) {
			throw new JavException(ErrorCode.PARAMETER_EMPTY);
		}
		return starLink.substring(i + 1);
	}
}
