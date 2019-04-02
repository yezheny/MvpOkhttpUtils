package mvp.pad.csc.salim.com.mvpokhttputils.contract;

import java.util.List;

import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.DeviceInfo;
import mvp.pad.csc.salim.com.mvpokhttputils.base.viewinterface.IBaseView;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.ScreenBean;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.IBasePresenter;

/**
 * created by yezhengyu on 2018/6/25 14:22
 */

public interface MainContract {

    /**
     * 用户view相关的接口
     */
    interface MainView extends IBaseView {
        /**
         * 获取device_info
         */
        void setDeviceInfo(DeviceInfo info);

        /**
         * 获取push_status
         */
        void setPushStatus();

        /**
         * 获取屏保数据
         */
        void setDreams(List<ScreenBean> beans);

        /**
         * 获取上传图片状态
         */
        void setUploadStatus(String img);
    }

    /**
     * 用户业务接口
     */
    interface MainPresenter extends IBasePresenter<MainView> {

        /**
         * 提交获取device_info
         */
        void getDeviceInfo(String deviceId);

        /**
         * 提交获取push_status
         */
        void getPushStatus();

        /**
         * 提交获取屏保数据
         */
        void getDreams();

        /**
         * 上传图片
         */
        void uploadImg();
    }
}
