package com.pcjz.http.okhttp.exception;

/**
 * 从JSON转换成BEAN的异常
 * 
 */
public class FromJsonToBeanFailException extends Exception {

	private static final long serialVersionUID = 5895223930808591547L;

	public FromJsonToBeanFailException(String msg) {
		super(msg);
	}

	public FromJsonToBeanFailException() {
		super();
	}

}
