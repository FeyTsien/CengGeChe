package com.ygst.cenggeche.ui.activity.setnotename;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

import java.util.Map;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetNoteNameContract {
    interface View extends BaseView {
        void updateNoteNameSuccess();
        void updateNoteNameError();
    }

    interface  Presenter extends BasePresenter<View> {

        void updateNoteName(Map map);
    }
}
