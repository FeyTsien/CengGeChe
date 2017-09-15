package com.ygst.cenggeche.ui.activity.systemnotify;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.systemNotify.SystemNotityListBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SystemNotifyActivity extends MVPBaseActivity<SystemNotifyContract.View, SystemNotifyPresenter> implements SystemNotifyContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_notify;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mTvTitle.setText("系统通知");
        mPresenter.getSystemNotifyList();
    }

    @Override
    public void getSystemNotifyListSuccess(SystemNotityListBean systemNotityListBean) {
        ToastUtil.show(this,"获取系统通知成功");
    }

    @Override
    public void getSystemNotifyListError() {

    }
}
