package mvp.pad.csc.salim.com.mvpokhttputils.presenter;


import mvp.pad.csc.salim.com.mvpokhttputils.base.viewinterface.IBaseView;

/**
 * Presenter接口
 * @author jiangtianming
 * @date 2016/5/31 15:26
 */
public interface IBasePresenter<T extends IBaseView>{
    void onViewAttached(T view);
    void onViewDetached();
    void onDestroyed();
}
