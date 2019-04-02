package mvp.pad.csc.salim.com.mvpokhttputils.base.viewinterface;

/**
 * MVP架构 V层 Dialog接口
 * @author jiangtianming
 * @date 2016/6/1 9:59
 */
public interface IBaseDialogView {
    /**
     * 显示dialog
     * @param strId
     * @param cancelable
     */
    void showLoading(int strId, boolean cancelable);

    /**
     * 显示dialog
     * @param content
     * @param cancelable
     */
    void showLoading(String content, boolean cancelable);

    /**
     * 隐藏dialog
     */
    void hideLoading();
}
