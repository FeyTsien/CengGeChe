package com.ygst.cenggeche.ui.activity.newfriendlist;

import com.ygst.cenggeche.mvp.BasePresenter;
import com.ygst.cenggeche.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewFriendListContract {
    interface View extends BaseView {
//        void getApplyListSuccess(ApplyBean applyBean);
//        void getApplyListError();
//        void deleteApplyDateSuccess(int position);
//        void deleteApplyDateError();
//        void noAgreeSuccess();
//        void noAgreeError();
//        void yesAgreeSuccess();
//        void yesAgreeError();
    }

    interface  Presenter extends BasePresenter<View> {
//        //获取全部申请信息
//        void getApplyList(String username);
//        //删除一条申请信息
//        void deleteApplyDate(String myusername,String fusername,int position);
//        //拒绝对方申请
//        void noAgree(String myusername,String fusername);
        //同意对方申请
        void yesAgree(String myusername,String fusername);

    }
}
