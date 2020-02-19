package com.assign.mancala.object;

/**
 * 
 * Global Error Response Object
 *
 */

public class GlobalErrorResponse {
	private String errorCode;
	private String message;
	private Object data;

	public GlobalErrorResponse() {
	}

	public GlobalErrorResponse(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public GlobalErrorResponse(String errorCode, String message, Object data) {
		this.errorCode = errorCode;
		this.message = message;
		this.data = data;
	}

	public static GlobalErrorResponse createGlobalErrorWithBasicsInformation(String errorCode, String message) {
		GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse();
		globalErrorResponse.setErrorCode(errorCode);
		globalErrorResponse.setMessage(message);
		return globalErrorResponse;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
