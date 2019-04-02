package com.pcjz.http.okhttp.utils;

/**
 * HTTP相关常量
 * @author jiangtianming
 * @date 2016/7/4 9:48
 */
public interface HttpConstant {
    /**
     * 请求状态
     * PS：四位数9开头是本地错误码
     */
    interface Status{
        /**
         * 其他错误
         */
        int LOCAL_STATUS_OTHERS = 9000;
        /**
         * 请求失败
         */
        int LOCAL_STATUS_REQUEST_FAIL = 9001;
        /**
         * 解析json失败
         */
        int LOCAL_STATUS_FROM_JSON_TO_BEAN_FAIL = 9002;

        /**
         * 请求成功
         */
        int SERVER_STATUS_SUCCESSFUL = 10000;
        /**
         * token过期
         */
        int SERVER_STATUS_TOKEN_OVERDUED = 30005;
    }

}
