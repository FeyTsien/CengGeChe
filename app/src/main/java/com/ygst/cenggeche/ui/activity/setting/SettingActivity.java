package com.ygst.cenggeche.ui.activity.setting;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter> implements SettingContract.View {

    List<Conversation> mListConversation;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("设置");
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

        mListConversation = JMessageClient.getConversationList();
        CommonUtils.showInfoDialog(this, "确定要清空聊天记录吗？", "提示", "清空", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(Conversation conversation:mListConversation){
                    conversation.deleteAllMessage();
                }
                ToastUtil.show(SettingActivity.this,"清空成功");
            }
        }, null);
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
    @OnClick(R.id.tv_about_us)
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
        CommonUtils.showInfoDialog(this, "确定要退出登录吗？", "提示", "退出", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.loginOut();
            }
        }, null);
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