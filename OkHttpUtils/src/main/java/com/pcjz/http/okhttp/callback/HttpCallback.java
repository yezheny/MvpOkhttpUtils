package com.pcjz.http.okhttp.callback;


import com.pcjz.http.okhttp.helper.ServerResponse;

import java.util.Map;

/**
 * Created by jiangtianming on 2016/6/24.
 */
public interface HttpCallback {


    /**
     * 界面http回调
     * @param serverResponse 响应数据
     * @param requestParams 请求参数
     * @param requestUrl 请求URL
     * @author jiangtianming
     */
    void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl);

}
