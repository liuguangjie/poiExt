
package com.chuangwl.poiext.message;

public class RuntimeExceptionMessage extends RuntimeException {

	private static final long serialVersionUID = -4616234852974191446L;
	private String message;

	public RuntimeExceptionMessage() {
		 super();
	}

	public RuntimeExceptionMessage(String message) {
		super(message);
		this.message=message;
	}

	public RuntimeExceptionMessage(String message, Throwable e) {
		super(message, e);
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
