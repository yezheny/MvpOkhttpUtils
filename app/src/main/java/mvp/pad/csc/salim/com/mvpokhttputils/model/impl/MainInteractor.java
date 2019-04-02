package mvp.pad.csc.salim.com.mvpokhttputils.model.impl;

import android.os.Environment;

import com.pcjz.http.okhttp.callback.HttpCallback;
import com.pcjz.http.okhttp.helper.HttpInvoker;
import com.pcjz.http.okhttp.helper.HttpManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mvp.pad.csc.salim.com.mvpokhttputils.model.IMainInteractor;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.DeviceInfo;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.ScreenBean;

/**
 * created by yezhengyu on 2018/6/25 14:30
 */

public class MainInteractor implements IMainInteractor{
    @Override
    public void getDeviceInfo(String deviceId, HttpCallback callback) {
        Map<String, String> params1 = new HashMap<>();
        params1.put("device_id", deviceId);
        HttpInvoker.createBuilder("http://192.168.39.128:8080/" + "customer/device_info")
                .setParams(params1)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(DeviceInfo.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void getPushStatus(HttpCallback callback) {
        Map<String, String> params1 = new HashMap<>();
        params1.put("device_id", "2001030001");
        params1.put("event_id", "13");
        params1.put("msg_id", "15301527741280");
        params1.put("sn", "HNB4T17722000060");
        params1.put("status","1");
        HttpInvoker.createBuilder("http://192.168.39.128:8080/" + "common/sync_jpush_status")
                .setParams(params1)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void getDreams(HttpCallback callback) {
        Map<String, String> params1 = new HashMap<>();
        params1.put("device_id", "2001030001");
        params1.put("group_id", "7");
        params1.put("version", "1");
        HttpInvoker.createBuilder("http://192.168.39.128:8080/" + "customer/device_screen")
                .setParams(params1)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(ScreenBean[].class)
                .build().sendAsyncHttpRequest(callback);
    }

    @Override
    public void uploadImg(HttpCallback callback) {
        File file = new File(Environment.getExternalStorageDirectory(), "map111222.jpg");
        Map<String, File> params = new HashMap<>();
        params.put("file", file);
        HttpInvoker.createBuilder("http://116.7.226.222:10001/safety/" + "client/img/upload")
                .setFileParams(params)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_FILEUP)
                .setClz(String.class)
                .build().sendAsyncHttpRequest(callback);
    }
}
