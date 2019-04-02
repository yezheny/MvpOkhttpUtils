package mvp.pad.csc.salim.com.mvpokhttputils.presenter.impl;

import com.pcjz.http.okhttp.callback.HttpCallback;
import com.pcjz.http.okhttp.helper.ServerResponse;
import com.pcjz.http.okhttp.utils.HttpConstant;

import java.util.List;
import java.util.Map;

import mvp.pad.csc.salim.com.mvpokhttputils.StringUtils;
import mvp.pad.csc.salim.com.mvpokhttputils.contract.MainContract;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.DeviceInfo;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.ScreenBean;
import mvp.pad.csc.salim.com.mvpokhttputils.model.impl.MainInteractor;

/**
 * created by yezhengyu on 2018/6/25 18:55
 */

public class MainImpl implements MainContract.MainPresenter, HttpCallback {

    MainInteractor mainInteractor;
    private MainContract.MainView mView;

    public MainImpl() {
        mainInteractor = new MainInteractor();
    }

    @Override
    public void onViewAttached(MainContract.MainView view) {
        this.mView = view;
    }

    @Override
    public void onViewDetached() {

    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
        if (requestUrl.contains("customer/device_info")) {
            if (StringUtils.equals(serverResponse.getStatus() + "", "1000")) {
                DeviceInfo deviceInfo = (DeviceInfo) serverResponse.getResult();
                mView.setDeviceInfo(deviceInfo);
            } else if(serverResponse.getStatus()== HttpConstant.Status.LOCAL_STATUS_REQUEST_FAIL){

            } else {

            }
        } else if (requestUrl.contains("customer/device_screen")) {
            if (StringUtils.equals(serverResponse.getStatus() + "", "1000")) {
                List<ScreenBean> beans = (List<ScreenBean>)  serverResponse.getResult();
                mView.setDreams(beans);
            }
        } else if (requestUrl.contains("common/sync_jpush_status")) {
            if (StringUtils.equals(serverResponse.getStatus() + "", "1000")) {
                mView.setPushStatus();
            }
        } else if (requestUrl.contains("client/img/upload")) {
            if (StringUtils.equals(serverResponse.getStatus() + "", "1000")) {
                String imgPath = (String) serverResponse.getResult();
                mView.setUploadStatus(imgPath);
            }
        }
    }

    @Override
    public void getDeviceInfo(String deviceId) {
        mainInteractor.getDeviceInfo(deviceId, this);
    }

    @Override
    public void getPushStatus() {
        mainInteractor.getPushStatus(this);
    }

    @Override
    public void getDreams() {
        mainInteractor.getDreams(this);
    }

    @Override
    public void uploadImg() {
        mainInteractor.uploadImg(this);
    }
}
