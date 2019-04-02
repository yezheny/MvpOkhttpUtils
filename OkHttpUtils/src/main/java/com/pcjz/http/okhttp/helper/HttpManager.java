package com.pcjz.http.okhttp.helper;

import com.pcjz.http.okhttp.callback.OnTokenOverduedListener;
import com.pcjz.http.okhttp.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangtianming
 * @date 2016/8/17 19:13
 */
public class HttpManager {

    private HttpManager() {
    }

    private static HttpManager instance;

    public static HttpManager getInstance() {
        synchronized (HttpManager.class) {
            if (instance == null) {
                instance = new HttpManager();
            }
        }
        return instance;
    }

    private String token;

    private Map<String, String> params = new HashMap<>();

    private OnTokenOverduedListener tokenOverduedListener;

    public void init(String token, OnTokenOverduedListener tokenOverduedListener) {
        this.token = token;
        this.tokenOverduedListener = tokenOverduedListener;
    }

    public void initHead(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 执行token回调
     */
    private synchronized void doOnTokenOverduedListener() {
        if (tokenOverduedListener != null) {
            tokenOverduedListener.onTokenOverdued();
            this.token = null;
            this.tokenOverduedListener = null;
        }
    }

    /**
     * 执行http过滤
     *
     * @param serverResponse
     */
    public void doHttpFilter(ServerResponse serverResponse) {
        switch (serverResponse.getStatus()) {
            case HttpConstant.Status.SERVER_STATUS_TOKEN_OVERDUED:
                doOnTokenOverduedListener();
                break;
        }
    }

    public String getToken() {
        return token;
    }

    public Map<String, String> getHeads() {
        return params;
    }

}
