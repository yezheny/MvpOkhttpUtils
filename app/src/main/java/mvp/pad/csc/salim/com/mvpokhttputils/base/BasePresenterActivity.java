package mvp.pad.csc.salim.com.mvpokhttputils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mvp.pad.csc.salim.com.mvpokhttputils.base.viewinterface.IBaseView;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.Creator;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.IBasePresenter;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.PresenterFactory;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.factory.APresenterFactory;

/**
 * Created by jiangtianming on 2016/6/3
 */
public abstract class BasePresenterActivity<P extends IBasePresenter<V>, V extends IBaseView> extends AppCompatActivity {

    private static final String TAG = BasePresenterActivity.class.getSimpleName();
    /**
     * 业务对象引用
     */
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        mPresenter = getPresenterFactory().create();

        Creator factory = new PresenterFactory();
        /*mPresenter = (P) factory.create(createPresenter());
        mPresenter.onViewAttached(getPresenterView());*/
        onPresenterCreate(savedInstanceState);
    }

    /**
     * 相当于onCreate生命周期
     *
     * @param savedInstanceState
     */
    protected void onPresenterCreate(@Nullable Bundle savedInstanceState) {
    }


    /**
     * 获取子类Presenter工厂对象
     *
     * @return
     * @author tmjiang
     */
    protected APresenterFactory<P> getPresenterFactory() {
        return new APresenterFactory<P>() {
            @Override
            public P create() {
                return createAPresenter();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDetached(); //页面与界面解除联系
    }

    /**
     * 创建主业务对象
     * 反射得到具体activity的presenter
     * @return
     */
    protected abstract Class createPresenter();

    protected abstract P createAPresenter();

    /**
     * 获取业务相关View，子类必须实现对应MVP V层界面接口
     *
     * @return
     * @author tmjiang
     */
    protected V getPresenterView() {
        return (V) this;
    }

    /**
     * 获取业务对象引用
     *
     * @return
     * @author tmjiang
     */
    protected P getPresenter() {
        return mPresenter;
    }


}
