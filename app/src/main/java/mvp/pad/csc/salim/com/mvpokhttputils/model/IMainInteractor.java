package mvp.pad.csc.salim.com.mvpokhttputils.model;

import com.pcjz.http.okhttp.callback.HttpCallback;

/**
 * created by yezhengyu on 2018/6/25 14:28
 */

public interface IMainInteractor{
    /**
     * 提交获取device_info
     * @param callback
     */
    void getDeviceInfo(String deviceId, HttpCallback callback);

    /**
     * 提交获取push_status
     */
    void getPushStatus(HttpCallback callback);

    /**
     * 提交获取屏保数据
     */
    void getDreams(HttpCallback callback);

    /**
     * 上传图片
     */
    void uploadImg(HttpCallback callback);

}
