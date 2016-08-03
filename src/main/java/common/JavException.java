package common;

public class JavException extends RuntimeException {

	private static final long serialVersionUID = 3681127260069300492L;

	protected int errorCode;
	protected String errorDescription;
	protected String inputParamWhereCatch;

	public JavException(int errorCode, String errorDescription, String inputParamWhereCatch) {
		super(errorDescription);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.inputParamWhereCatch = inputParamWhereCatch;
	}

	public JavException(int errorCode) {
		this(errorCode, ErrorCode.getMessage(errorCode));
	}

	public JavException(int errorCode, String errorDescription) {
		super(errorDescription);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public JavException(String errorMsg, Throwable ex) {
		super(errorMsg, ex);
	}

	public JavException(int errorCode, String errorDescription, Throwable cause, String inputParamWhereCatch) {
		super(errorDescription, cause);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.inputParamWhereCatch = inputParamWhereCatch;
	}

	public JavException(int errorCode, String errorDescription, Throwable cause) {
		super(errorDescription, cause);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getInputParamWhereCatch() {
		return inputParamWhereCatch;
	}

	public void setInputParamWhereCatch(String inputParamWhereCatch) {
		this.inputParamWhereCatch = inputParamWhereCatch;
	}

}
