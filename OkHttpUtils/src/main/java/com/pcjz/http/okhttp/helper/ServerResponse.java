package com.pcjz.http.okhttp.helper;

import java.util.Map;

import static android.R.id.message;

/**
 * 服务器响应界面类
 * @author jiangtianming
 * @date 2016/6/27 16:58
 */
public abstract class ServerResponse {
    /**
     * 响应状态
     */
    private int code;
    /**
     * 响应描述
     */
    private String msg;
    /**
     * 服务器响应时间戳
     */
    private long serverTimeStamp;
    /**
     * 请求标记TAG MAP
     */
    private Map<String, Object> requestTagMap;

    /**
     * 创建错误实体
     * @param status
     * @param message
     * @return
     */
    public static ServerResponse createErrorResponse(int status, String message){
        ServerResponse response = new ServerResponse() {
            @Override
            public Object getResult() {
                return null;
            }
        };
        response.code = status;
        response.msg = message;
        response.serverTimeStamp = System.currentTimeMillis();
        return response;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + code +
                ", message='" + msg + '\'' +
                ", serverTimeStamp=" + serverTimeStamp +
                '}';
    }

    public void setRequestTagMap(Map<String, Object> requestTagMap) {
        this.requestTagMap = requestTagMap;
    }

    public Map<String, Object> getRequestTagMap() {
        return requestTagMap;
    }

    public int getStatus() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public long getServerTimeStamp() {
        return serverTimeStamp;
    }

    public abstract Object getResult();
}
