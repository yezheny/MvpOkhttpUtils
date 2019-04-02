package com.pcjz.http.okhttp.exception;

/**
 * 发送HTTP POST或者HTTP PUT请求时发生异常
 * 
 */
public class DoHttpRequestFailException extends Exception {

	private static final long serialVersionUID = 1929017435825857966L;

	public DoHttpRequestFailException(String msg) {
		super(msg);
	}

	public DoHttpRequestFailException(String msg, Exception e) {
		super(msg, e);
	}

}
