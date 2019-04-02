package com.pcjz.http.okhttp.helper;

import android.text.TextUtils;
import android.util.Log;

import com.pcjz.http.okhttp.OkHttpUtils;
import com.pcjz.http.okhttp.builder.OkHttpRequestBuilder;
import com.pcjz.http.okhttp.callback.Callback;
import com.pcjz.http.okhttp.callback.HttpCallback;
import com.pcjz.http.okhttp.exception.FromJsonToBeanFailException;
import com.pcjz.http.okhttp.json.JsonUtils;
import com.pcjz.http.okhttp.utils.HttpConstant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.key;

/**
 * HTTP请求调用工具类
 * <p/>
 * 异步调用例子：
 * HttpInvoker invoker = HttpInvoker.createBuilder(url)
 * .setParams(null)
 * .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
 * .setClz(null)
 * .setHeadParams(null)
 * .build();
 * invoker.sendAsyncHttpRequest(null);
 * <p/>
 * 同步调用例子：
 * HttpInvoker invoker = HttpInvoker.createBuilder(url)
 * .setParams(null)
 * .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
 * .setClz(null)
 * .setHeadParams(null)
 * .build();
 * ServerResponse response = invoker.sendSyncHttpRequest()
 *
 * @author jiangtianming
 */
public class HttpInvoker {

    private static final String TAG = HttpInvoker.class.getSimpleName();

    private static final String PARAMETER_KEY = "json"; //"parameter";// 参数的KEY
    /**
     * POST请求方法
     */
    public static final String HTTP_REQUEST_METHOD_POST = "POST";
    /**
     * GET请求方法
     */
    public static final String HTTP_REQUEST_METHOD_GET = "GET";

    /**
     * 文件上传请求方法
     */
    public static final String HTTP_REQUEST_METHOD_FILEUP = "FILEUP";
    /**
     * 请求参数（可选）
     */
    private Map<String, String> params;
    /**
     * 请求方法（可配，默认GET，方法参数传入错误使用默认值）
     */
    private String methodType;
    /**
     * 请求URL（必传）
     */
    private String url;
    /**
     * 请求头参数（可选）
     */
    private Map<String, String> headParams;
    /**
     * 解析数据类型（默认解析成json字符串）
     */
    private Class<?> clz;
    /**
     * 请求标记，记录自己想要的内容
     */
    private Map<String, Object> tagMap;

    private Map<String, File> fileParams;

    /**
     * HTTP调用对象构造函数
     *
     * @param builder HttpInvoker builder工具类
     * @author jiangtianming
     */
    private HttpInvoker(Builder builder) {
        this.url = builder.url;
        this.params = builder.params;
        this.methodType = builder.methodType;
        this.headParams = builder.headParams;
        this.clz = builder.clz;
        this.tagMap = builder.tagMap;
        this.fileParams = builder.fileParams;
    }

    /**
     * 创建Builder工具
     *
     * @param url 请求URL，不能为空，空会抛异常
     * @return
     * @author jiangtianming
     */
    public static Builder createBuilder(String url) {
        return new Builder(url);
    }

    /**
     * HttpInvoker构造工具类
     *
     * @author jiangtianming
     */
    public static class Builder {
        private String url;
        private Map<String, String> params;
        private String methodType;
        private Map<String, String> headParams;
        private Class<?> clz;
        private Map<String, Object> tagMap;
        private Map<String, File> fileParams;

        /**
         * 请求URL，不能为空，空会抛异常，默认使用GET请求
         *
         * @param url
         * @author jiangtianming
         */
        private Builder(String url) {
            if (TextUtils.isEmpty(url)) {
                throw new NullPointerException("url is null or empty~");
            }
            this.url = url;
            this.methodType = HttpInvoker.HTTP_REQUEST_METHOD_GET;
            this.params = new HashMap<>();
            if (HttpManager.getInstance().getToken() != null) {
                params.put("token", HttpManager.getInstance().getToken());
            }
            this.headParams = new HashMap<>();
            this.clz = String.class;
            this.tagMap = new HashMap<>();
            this.fileParams = new HashMap<>();
        }

        /**
         * 设置请求参数，可传null
         *
         * @param params
         * @return
         */
        public Builder setParams(Map<String, String> params) {
            this.params = params == null ? new HashMap<String, String>() : params;
            return this;
        }

        /**
         * HTTP请求方法，传入非法方法字符会默认使用GET请求
         *
         * @param methodType
         * @return
         */
        public Builder setMethodType(String methodType) {

            if (!HttpInvoker.HTTP_REQUEST_METHOD_GET.equals(methodType) && !HttpInvoker.HTTP_REQUEST_METHOD_POST.equals(methodType)
                    && !HttpInvoker.HTTP_REQUEST_METHOD_FILEUP.equals(methodType)) {
                this.methodType = HttpInvoker.HTTP_REQUEST_METHOD_GET;
            } else {
                this.methodType = methodType;
            }
            return this;
        }

        /**
         * 设置请求头参数
         *
         * @param headParams
         * @return
         */
        public Builder setHeadParams(Map<String, String> headParams) {
            this.headParams = headParams == null ? new HashMap<String, String>() : headParams;
            return this;
        }

        /**
         * 设置响应数据解析类型，默认解析成String
         *
         * @param clz
         * @return
         */
        public Builder setClz(Class<?> clz) {
            this.clz = clz == null ? String.class : clz;
            return this;
        }

        /**
         * 设置请求标记数据，回调的时候返回
         *
         * @param tagMap
         * @return
         */
        public Builder setTagMap(Map<String, Object> tagMap) {
            this.tagMap = tagMap == null ? new HashMap<String, Object>() : tagMap;
            return this;
        }

        public Builder setFileParams(Map<String, File> fileParams) {
            this.fileParams = fileParams == null ? new HashMap<String, File>() : fileParams;
            return this;
        }

        /**
         * 构建HttpInvoker对象
         *
         * @return
         */
        public HttpInvoker build() {
            return new HttpInvoker(this);
        }
    }

    /**
     * 创建OkHttpRequestBuilder对象
     *
     * @return
     */
    private OkHttpRequestBuilder createOkHttpRequestBuilder() {
        // 构建请求方法Builder对象
        OkHttpRequestBuilder requestBuilder = null;
        if (HttpManager.getInstance().getHeads() != null) {
            this.headParams = HttpManager.getInstance().getHeads();
        }
        if (methodType.equals(HTTP_REQUEST_METHOD_POST)) {
            requestBuilder = OkHttpUtils.post().params(params).headers(headParams);
        } else if (methodType.equals(HTTP_REQUEST_METHOD_FILEUP)) {
            if (fileParams != null) {
                String name = "";
                String filename = "";
                File file = null;
                for (String fname : fileParams.keySet()) {
                    name = fname;
                    filename = fileParams.get(fname).getName();
                    file = fileParams.get(fname);
                }
                Log.i(TAG, url + " 上传文件 ==> " + filename);
                requestBuilder = OkHttpUtils.post().addFile(name, filename, file).headers(headParams);
            }

        } else {
            requestBuilder = OkHttpUtils.get().params(params).headers(headParams);
        }

        return requestBuilder;
    }


    /**
     * 创建OKHttp回调，里面回调自己的监听，外部界面不依赖于OKHttp回调
     *
     * @param params       请求参数
     * @param url          请求url
     * @param httpCallback 界面回调
     * @param clz          数据解析类型
     * @return 如果传进来的clz是array就解析为ServerResponseArray，反之解析为ServerResponseBean
     * @author jiangtianming
     */
    private Callback createOkHttpCallback(final Map<String, String> params, final String url, final HttpCallback httpCallback, final Class<?> clz, final boolean callbackInUiThread) {
        return new Callback<ServerResponse>() {
            @Override
            public ServerResponse parseNetworkResponse(Response response, int id) {
                Log.i(TAG, url + " ==> " + "parseNetworkResponse");
                ServerResponse serverResponse = response2ServerResponse(response, clz);
                return serverResponse;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, url + " ==> " + "onError exception：" + e.getMessage());
                ServerResponse response = ServerResponse.createErrorResponse(HttpConstant.Status.LOCAL_STATUS_REQUEST_FAIL, "请求失败");
                doCallback(callbackInUiThread, response, params, url, httpCallback);
            }

            @Override
            public void onResponse(ServerResponse response, int id) {
                Log.d(TAG, url + " ==> " + "解析ServerResponse结果：" + response);
                doCallback(callbackInUiThread, response, params, url, httpCallback);
            }
        };
    }

    /**
     * 处理界面回调
     *
     * @param callbackInUiThread 是否需要在UI线程回调
     * @param response
     * @param params
     * @param url
     * @param httpCallback
     */
    private void doCallback(boolean callbackInUiThread, final ServerResponse response, final Map<String, String> params, final String url, final HttpCallback httpCallback) {
        response.setRequestTagMap(tagMap);
        if (callbackInUiThread) {
            if (httpCallback != null) {
                httpCallback.onHttpFinish(response, params, url);
            }
        } else {
            new Thread() {
                @Override
                public void run() {
                    if (httpCallback != null) {
                        httpCallback.onHttpFinish(response, params, url);
                    }
                }
            }.start();
        }
    }

    /**
     * OKhttp请求响应对象转换成界面需要的响应对象
     *
     * @param response
     * @param clz
     * @return
     * @throws IOException
     * @throws FromJsonToBeanFailException
     */
    private ServerResponse response2ServerResponse(Response response, Class<?> clz) {
        ServerResponse serverResponse = null;
        Log.i(TAG, url + " ==> " + "服务器响应：" + response);
        try {
            if (response != null && response.isSuccessful()) {
                String body = response.body().string(); // 响应数据
                Log.e(TAG, url + " ==> " + "response body：" + body);
                if (clz.isArray()) { //根据调用者的需求判断是解析成数组还是bean
                    //如果是数组需要clz.getComponentType()获取数组实际存放的数据类型clz再解析成ServerResponseArray
                    serverResponse = JsonUtils.json2Bean(body, ServerResponseArray.class, clz.getComponentType());
                } else {
                    if (clz == null) {
                        clz = String.class;
                    }
                    serverResponse = JsonUtils.json2Bean(body, ServerResponseBean.class, clz);
                }
            } else {
                serverResponse = ServerResponse.createErrorResponse(HttpConstant.Status.LOCAL_STATUS_REQUEST_FAIL, "response：" + response);
            }
        } catch (IOException e) {
            Log.e(TAG, url + " ==> " + "解析数据异常IOException：" + e.getMessage());
            serverResponse = ServerResponse.createErrorResponse(HttpConstant.Status.LOCAL_STATUS_REQUEST_FAIL, e.getMessage());
        } catch (FromJsonToBeanFailException e) {
            e.printStackTrace();
            Log.e(TAG, url + " ==> " + "解析数据异常FromJsonToBeanFailException：" + e.getMessage());
            serverResponse = ServerResponse.createErrorResponse(HttpConstant.Status.LOCAL_STATUS_FROM_JSON_TO_BEAN_FAIL, "FromJsonToBeanFailException~");
        } catch (Exception e) {
            Log.e(TAG, url + " ==> " + "其他异常Exception：" + e.getMessage());
        }
        if (serverResponse == null) {
            serverResponse = ServerResponse.createErrorResponse(HttpConstant.Status.LOCAL_STATUS_OTHERS, "other failure~");
        }
        HttpManager.getInstance().doHttpFilter(serverResponse); //http过滤
        return serverResponse;
    }


/**
 *****************************************************************以下是对外公开方法*****************************************************************
 */


    /**
     * 发送HTTP异步请求，返回服务端原始响应<br/>
     *
     * @param httpCallback     界面回调
     * @param callbackUIThread 是否回调到UI线程
     * @author jiangtianming
     */
    public void sendAsyncHttpRequest(HttpCallback httpCallback, boolean callbackUIThread) {
        Log.i(TAG, url + "?" + getUrlParamsByMap(params));
        Log.i(TAG, url + " ==> " + "method：" + methodType);
        Log.i(TAG, url + " ==> " + "clz：" + clz);
        Log.i(TAG, url + " ==> " + "headParams：" + headParams);
        OkHttpRequestBuilder requestBuilder = createOkHttpRequestBuilder();
        Callback callback = createOkHttpCallback(params, url, httpCallback, clz, callbackUIThread);
        try {
            requestBuilder.url(url)
                    .build()
                    .execute(callback);
        } catch (Exception e) {
            callback.onError(null, e, 0);
            Log.e(TAG, url + " ==> " + "Exception：" + e.getMessage());
        }
    }

    /**
     * 发送HTTP异步请求，返回服务端原始响应（默认回调到UI线程）<br/>
     *
     * @param httpCallback 界面回调
     * @author jiangtianming
     */
    public void sendAsyncHttpRequest(final HttpCallback httpCallback) {
        sendAsyncHttpRequest(httpCallback, true);
    }

    /**
     * 发送HTTP同步请求，返回服务端原始响应<br/>
     *
     * @author jiangtianming
     */
    public ServerResponse sendSyncHttpRequest() {
        Log.i(TAG, url + "?" + getUrlParamsByMap(params));
        Log.i(TAG, url + " ==> " + "method：" + methodType);
        Log.i(TAG, url + " ==> " + "clz：" + clz);
        Log.i(TAG, url + " ==> " + "headParams：" + headParams);
        OkHttpRequestBuilder requestBuilder = createOkHttpRequestBuilder();
        Response response = null;
        try {
            response = requestBuilder.url(url).build().execute();
        } catch (Exception e) {
            Log.e(TAG, url + " ==> " + "Exception：" + e.getMessage());
        }
        ServerResponse serverResponse = response2ServerResponse(response, clz);
        Log.d(TAG, url + " ==> " + "解析ServerResponse结果：" + response);
        return serverResponse;
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.lastIndexOf("&"));
        }
        return s;
    }

}
