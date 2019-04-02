package mvp.pad.csc.salim.com.mvpokhttputils.presenter;

/**
 * created by yezhengyu on 2019/4/2 11:01
 */

public abstract class Creator {
    public abstract <T extends IBasePresenter> T create(Class<T> clazz);
}
