package mvp.pad.csc.salim.com.mvpokhttputils.presenter.factory;

import mvp.pad.csc.salim.com.mvpokhttputils.presenter.IBasePresenter;

/**
 * 创建Presenter对象
 * @author jiangtianming
 * @date 2016/6/2 13:35
 */
public interface APresenterFactory<T extends IBasePresenter> {
    /**
     * 创建实例方法
     * @return
     */
    T create();
}
