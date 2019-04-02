package mvp.pad.csc.salim.com.mvpokhttputils.presenter;

/**
 * created by yezhengyu on 2019/4/2 11:05
 */

public class PresenterFactory extends Creator{

    @Override
    public <T extends IBasePresenter> T create(Class<T> clazz) {
        IBasePresenter presenter = null;
        try {
            presenter = (IBasePresenter) Class.forName(clazz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) presenter;
    }
}
