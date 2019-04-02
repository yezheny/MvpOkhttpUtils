package com.pcjz.http.okhttp.server.upload;

import android.os.Handler;
import android.os.Message;

import com.pcjz.http.okhttp.server.listener.UploadListener;
import com.pcjz.http.okhttp.utils.L;

/**
 *用于在主线程回调更新UI
 */
public class UploadUIHandler extends Handler {

    private UploadListener mGlobalUploadListener;

    @Override
    public void handleMessage(Message msg) {
        MessageBean messageBean = (MessageBean) msg.obj;
        if (messageBean != null) {
            UploadInfo info = messageBean.uploadInfo;
            String errorMsg = messageBean.errorMsg;
            Exception e = messageBean.e;
            Object t = messageBean.data;
            if (mGlobalUploadListener != null) {
                executeListener(mGlobalUploadListener, info, t, errorMsg, e);
            }
            UploadListener listener = info.getListener();
            if (listener != null) executeListener(listener, info, t, errorMsg, e);
        } else {
            L.e("UploadUIHandler UploadInfo null");
        }
    }

    private void executeListener(UploadListener listener, UploadInfo info, Object t, String errorMsg, Exception e) {
        int state = info.getState();
        switch (state) {
            case UploadManager.NONE:
            case UploadManager.WAITING:
            case UploadManager.UPLOADING:
                listener.onProgress(info);
                break;
            case UploadManager.FINISH:
                listener.onProgress(info);   //结束前再次回调进度，避免最后一点数据没有刷新
                listener.onFinish(t,info);
                break;
            case UploadManager.ERROR:
                listener.onProgress(info);   //结束前再次回调进度，避免最后一点数据没有刷新
                listener.onError(info, errorMsg, e);
                break;
        }
    }

    public void setGlobalDownloadListener(UploadListener uploadListener) {
        this.mGlobalUploadListener = uploadListener;
    }

    public static class MessageBean<T> {
        public UploadInfo uploadInfo;
        public T data;
        public String errorMsg;
        public Exception e;
    }
}
