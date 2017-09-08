package com.ygst.cenggeche.ui.activity.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter> implements SettingContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 清除缓存
     */
    @OnClick(R.id.rl_clear_cache)
    public void clearCache(){

    }

    /**
     * 是否接收消息推送
     */
    public void push(){

    }

    /**
     * 清除聊天记录
     */
    @OnClick(R.id.tv_clear_chat)
    public void clearChat(){

    }

    /**
     * 版本更新
     */
    @OnClick(R.id.tv_update)
    public void update(){

    }

    /**
     * 使用指南
     */
    @OnClick(R.id.tv_user_guide)
    public void userGuide(){

    }

    /**
     * 关于我们
     */
    @OnClick(R.id.tv_user_guide)
    public void aboutUs(){

    }

    /**
     * 意见反馈
     */
    @OnClick(R.id.tv_feedback)
    public void feedback(){

    }

    /**
     * 退出登录
     */
    @OnClick(R.id.tv_login_out)
    public void Loginout(){
        mPresenter.loginOut();
    }

    @Override
    public void loginOutSuccess() {
        JMessageClient.logout();
        MyApplication.clearLogin();
        finish();
    }

    @Override
    public void loginOutError() {
        ToastUtil.show(this, "退出失败");
    }
}
