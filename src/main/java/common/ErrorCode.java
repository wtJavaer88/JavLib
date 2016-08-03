package common;

import java.util.HashMap;
import java.util.Map;

public abstract class ErrorCode {

	/**
	 * 成功
	 */
	public final static int SUCCESS = 0;

	/**
	 * 系统错误
	 */
	public final static int FAIL = 9999;

	public final static int PARAMETER_EMPTY = 1000;
	public final static int PARAMETER_LENGTH = 1001;
	public final static int PARAMETER_NOT_RULES = 1004;

	public final static int URL_PARSE_ERROR = 1005;

	public final static int PAGE_NOT_FOUND = 2003;
	public final static int STAR_NOT_FOUND = 2004;

	protected final static Map<Integer, String> msgMap = new HashMap<Integer, String>();

	static {
		msgMap.put(SUCCESS, "成功");
		msgMap.put(FAIL, "系统异常");
		msgMap.put(PARAMETER_EMPTY, "参数为空");
		msgMap.put(PARAMETER_LENGTH, "参数超长");
		msgMap.put(PARAMETER_NOT_RULES, "参数不符合规则");

		msgMap.put(URL_PARSE_ERROR, "网页解析错误");

		msgMap.put(PAGE_NOT_FOUND, "没有分页数据");
		msgMap.put(STAR_NOT_FOUND, "没找到演员");
	}

	public static String getMsg(int errorCode) {
		String errMsg = msgMap.get(errorCode);
		if (errMsg == null) {
			return "未知错误";
		} else {
			return errMsg;
		}
	}

	/**
	 * 获取消息
	 * 
	 * @param errorCode
	 * @return
	 */
	public static final String getMessage(int errorCode) {
		return msgMap.get(errorCode);
	}

	/**
	 * @param errorCode
	 * @return
	 */
	public static final boolean isSuccess(int errorCode) {
		return (ErrorCode.SUCCESS == errorCode) ? true : false;
	}
}
