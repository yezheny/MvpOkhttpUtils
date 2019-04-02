package com.pcjz.http.okhttp.builder;

import com.pcjz.http.okhttp.OkHttpUtils;
import com.pcjz.http.okhttp.request.OtherRequest;
import com.pcjz.http.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
