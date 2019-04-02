package com.pcjz.http.okhttp.callback;

/**
 * token过期监听
 * @author jiangtianming
 * @date 2016/8/17 19:21
 */
public interface OnTokenOverduedListener {
    /**
     * token过期
     */
    void onTokenOverdued();
}
