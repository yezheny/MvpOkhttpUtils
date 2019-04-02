package com.pcjz.http.okhttp.server.upload;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;

import com.pcjz.http.okhttp.OkHttpUtils;
import com.pcjz.http.okhttp.builder.OkHttpRequestBuilder;
import com.pcjz.http.okhttp.callback.AbsCallback;
import com.pcjz.http.okhttp.callback.StringCallback;
import com.pcjz.http.okhttp.server.listener.UploadListener;
import com.pcjz.http.okhttp.server.task.PriorityAsyncTask;
import com.pcjz.http.okhttp.utils.L;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 *上传任务类
 */
public class UploadTask<T> extends PriorityAsyncTask<Void, UploadInfo, UploadInfo> {

    private UploadUIHandler mUploadUIHandler;    //下载UI回调
    private UploadInfo mUploadInfo;              //当前任务的信息

    public UploadTask(UploadInfo downloadInfo, Context context, UploadListener<T> uploadListener) {
        mUploadInfo = downloadInfo;
        mUploadInfo.setListener(uploadListener);
        mUploadUIHandler = UploadManager.getInstance(context).getHandler();
        //将当前任务在定义的线程池中执行
        executeOnExecutor(UploadManager.getInstance(context).getThreadPool().getExecutor());
    }

    /** 每个任务进队列的时候，都会执行该方法 */
    @Override
    protected void onPreExecute() {
        L.e("onPreExecute:" + mUploadInfo.getResourcePath());

        //添加成功的回调
        UploadListener listener = mUploadInfo.getListener();
        if (listener != null) listener.onAdd(mUploadInfo);

        mUploadInfo.setNetworkSpeed(0);
        mUploadInfo.setState(UploadManager.WAITING);
        postMessage(null, null, null);
    }

    /** 一旦该方法执行，意味着开始下载了 */
    @Override
    protected UploadInfo doInBackground(Void... params) {
        if (isCancelled()) return mUploadInfo;
        L.e("doInBackground:" + mUploadInfo.getResourcePath());
        mUploadInfo.setNetworkSpeed(0);
        mUploadInfo.setState(UploadManager.UPLOADING);
        postMessage(null, null, null);

        //构建请求体,默认使用post请求上传
        Response response;
        try {
         /*   OkHttpRequestBuilder requestBuilder = methodType.equals(HTTP_REQUEST_METHOD_POST) ? OkHttpUtils.post().params(requestParams) : OkHttpUtils.get().params(requestParams);
            requestBuilder.url(url)
                    .build()
                    .execute(createOkHttpCallback(params, url, httpCallback, clz,isUiThread));*/
            File resource = new File(mUploadInfo.getResourcePath());
            if (TextUtils.isEmpty(mUploadInfo.getFileName())) {
                mUploadInfo.setFileName(resource.getName());
            }
            OkHttpRequestBuilder postRequest = OkHttpUtils.post()
                    .addFile("file", mUploadInfo.getFileName(),resource)
                    .url(mUploadInfo.getUrl())
                    .addHeader("fileName",mUploadInfo.getFileName());
            //接口对接，数据回调
            response = postRequest.build().buildCall(new MyStringCallback()).execute();

        } catch (IOException e) {
            e.printStackTrace();
            mUploadInfo.setNetworkSpeed(0);
            mUploadInfo.setState(UploadManager.ERROR);
            postMessage(null, "网络异常", e);
            return mUploadInfo;
        }

        if (response.isSuccessful()) {
            //解析过程中抛出异常，一般为 json 格式错误，或者数据解析异常
            try {
                mUploadInfo.setNetworkSpeed(0);
                mUploadInfo.setState(UploadManager.FINISH); //上传成功
                T t = (T) mUploadInfo.getListener().parseNetworkResponse(response,mUploadInfo);

                postMessage(t, null, null);
                return mUploadInfo;
            } catch (Exception e) {
                e.printStackTrace();
                mUploadInfo.setNetworkSpeed(0);
                mUploadInfo.setState(UploadManager.ERROR);
                postMessage(null, "解析数据对象出错", e);
                return mUploadInfo;
            }
        } else {
            mUploadInfo.setNetworkSpeed(0);
            mUploadInfo.setState(UploadManager.ERROR);
            postMessage(null, "数据返回失败", null);
            return mUploadInfo;
        }
    }



    public class MyStringCallback extends StringCallback
    {

        private long lastRefreshUiTime;

        public MyStringCallback() {
            lastRefreshUiTime = System.currentTimeMillis();
        }
        @Override
        public void onBefore(Request request, int id)
        {
        }

        @Override
        public void onAfter(int id)
        {
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id)
        {
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            long curTime = System.currentTimeMillis();
            //每100毫秒刷新一次数据
            if (curTime - lastRefreshUiTime >= 100 || progress == 1.0f) {
                mUploadInfo.setState(UploadManager.UPLOADING);
                mUploadInfo.setTotalLength(total);
                mUploadInfo.setProgress(progress);
                postMessage(null, null, null);
                lastRefreshUiTime = System.currentTimeMillis();
            }
        }
    }

    private class MergeListener extends AbsCallback<T> {

        private long lastRefreshUiTime;

        public MergeListener() {
            lastRefreshUiTime = System.currentTimeMillis();
        }

        //只有这个方法会被调用，主要是为了对接接口，获取进度
        @Override
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            long curTime = System.currentTimeMillis();
            //每100毫秒刷新一次数据
            if (curTime - lastRefreshUiTime >= 100 || progress == 1.0f) {
                mUploadInfo.setState(UploadManager.UPLOADING);
                mUploadInfo.setUploadLength(currentSize);
                mUploadInfo.setTotalLength(totalSize);
                mUploadInfo.setProgress(progress);
                mUploadInfo.setNetworkSpeed(networkSpeed);
                postMessage(null, null, null);
                lastRefreshUiTime = System.currentTimeMillis();
            }
        }

        @Override
        public T parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onResponse(boolean isFromCache, T t, Request request, Response response) {
        }
    }

    private void postMessage(T data, String errorMsg, Exception e) {
        UploadUIHandler.MessageBean<T> messageBean = new UploadUIHandler.MessageBean<>();
        messageBean.uploadInfo = mUploadInfo;
        messageBean.errorMsg = errorMsg;
        messageBean.e = e;
        messageBean.data = data;
        Message msg = mUploadUIHandler.obtainMessage();
        msg.obj = messageBean;
        mUploadUIHandler.sendMessage(msg);
    }
}