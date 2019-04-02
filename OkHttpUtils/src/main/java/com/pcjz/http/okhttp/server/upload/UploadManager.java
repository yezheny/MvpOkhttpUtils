package com.pcjz.http.okhttp.server.upload;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pcjz.http.okhttp.server.listener.UploadListener;
import com.pcjz.http.okhttp.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * 全局的上传管理
 */
public class UploadManager {
    //定义上传状态常量
    public static final int NONE = 0;         //无状态  --> 等待
    public static final int WAITING = 1;      //等待    --> 下载
    public static final int UPLOADING = 2;    //下载中  --> 完成，错误
    public static final int FINISH = 3;       //完成    --> 重新上传
    public static final int ERROR = 4;        //错误    --> 等待

    private List<UploadInfo> mUploadInfoList;       //维护了所有下载任务的集合
    private UploadUIHandler mUploadUIHandler;       //主线程执行的handler
    private Context mContext;                       //上下文
    private static UploadManager mInstance;         //使用单例模式
    private UploadThreadPool threadPool;            //上传的线程池

    public static UploadManager getInstance(Context context) {
        if (null == mInstance) {
            synchronized (UploadManager.class) {
                if (null == mInstance) {
                    mInstance = new UploadManager(context);
                }
            }
        }
        return mInstance;
    }

    private UploadManager(Context context) {
        mContext = context;
        mUploadInfoList = Collections.synchronizedList(new ArrayList<UploadInfo>());
        mUploadUIHandler = new UploadUIHandler();
        threadPool = new UploadThreadPool();
    }

    public UploadThreadPool getThreadPool() {
        return threadPool;
    }

    public UploadUIHandler getHandler() {
        return mUploadUIHandler;
    }

    public List<UploadInfo> getAllTask() {
        return mUploadInfoList;
    }

    public UploadInfo getTaskByResourcePath(@NonNull String resourcePath) {
        for (UploadInfo uploadInfo : mUploadInfoList) {
            if (resourcePath.equals(uploadInfo.getResourcePath())) {
                return uploadInfo;
            }
        }
        return null;
    }

    public void removeTaskByUrl(@NonNull String url) {
        ListIterator<UploadInfo> iterator = mUploadInfoList.listIterator();
        while (iterator.hasNext()) {
            UploadInfo info = iterator.next();
            if (url.equals(info.getUrl())) {
                UploadListener listener = info.getListener();
                if (listener != null) listener.onRemove(info);
                info.removeListener();     //清除回调监听
                iterator.remove();         //清除任务
                break;
            }
        }
    }
    public void removeTasksByUrl(@NonNull String url) {
        ListIterator<UploadInfo> iterator = mUploadInfoList.listIterator();
        while (iterator.hasNext()) {
            UploadInfo info = iterator.next();
            if (url.equals(info.getUrl())) {
                UploadListener listener = info.getListener();
                if (listener != null) listener.onRemove(info);
                info.removeListener();     //清除回调监听
                iterator.remove();         //清除任务
            }
        }
    }

    /**
     * 添加一个上传任务
     *
     * @param url      上传地址
     * @param listener 上传监听
     */
    public <T> void addTask(@NonNull String url, @NonNull File resource, @NonNull String upId, UploadListener<T> listener) {
        UploadInfo uploadInfo = getTaskByResourcePath(resource.getAbsolutePath());
        if (uploadInfo == null) {
            uploadInfo = new UploadInfo();
            uploadInfo.setUrl(url);
            uploadInfo.setState(UploadManager.NONE);
            uploadInfo.setResourcePath(resource.getAbsolutePath());
            uploadInfo.setUpId(upId);
            uploadInfo.setKey("file");
            mUploadInfoList.add(uploadInfo);
        }
        //无状态，暂停，错误才允许开始上传
        if (uploadInfo.getState() == UploadManager.NONE || uploadInfo.getState() == UploadManager.ERROR||uploadInfo.getState() == UploadManager.FINISH) {
            //构造即开始执行
            UploadTask uploadTask = new UploadTask<T>(uploadInfo, mContext, listener);
            uploadInfo.setTask(uploadTask);
        } else {
            L.e("任务正在上传或等待中 url:" + url);
        }
    }
}
