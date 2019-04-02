package com.pcjz.http.okhttp.exception;

/**
 * 从服务端获得响应之后，解析时发生异常
 * 
 */
public class ParseResponseFailException extends Exception {

	private static final long serialVersionUID = -9200018732248755316L;

	public ParseResponseFailException(String msg) {
		super(msg);
	}

	public ParseResponseFailException(String msg, Exception e) {
		super(msg, e);
	}

}
