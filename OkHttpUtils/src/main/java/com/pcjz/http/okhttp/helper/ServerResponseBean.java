package com.pcjz.http.okhttp.helper;

/**
 * 单个Bean专用类
 * @param <T>
 * @author jiangtianming
 */
public class ServerResponseBean<T> extends ServerResponse {

	private T data;

	@Override
	public String toString() {
		return "ServerResponseBean{" +
				"status=" + getStatus() +
				", message='" + getMessage() + '\'' +
				", serverTimeStamp=" + getServerTimeStamp() +
				", result=" +  (data != null ? data.toString():"") +
				'}';
	}

	@Override
	public Object getResult() {
		return data;
	}
}
