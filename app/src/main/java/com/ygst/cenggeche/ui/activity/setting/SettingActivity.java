package com.ygst.cenggeche.ui.activity.setting;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.NewAppVersionBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.main.MainActivity1;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.DataCleanManager;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

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

    //使用指南
    private final String URL_USER_GUIDE= UrlUtils.URL_H5+"/cenggeche/pages/help/help.html";
    //关于我们
    private final String URL_ABOUT_US= UrlUtils.URL_H5+"/cenggeche/pages/about/about.html";
    //意见反馈
    private final String URL_FEEDBACK= UrlUtils.URL_H5+"/cenggeche/pages/feedback/feedback.html";

    List<Conversation> mListConversation;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_clear_cache)
    TextView mTvClearCache;
    @BindView(R.id.tv_new_app)
    TextView mTvNewApp;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }
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
        try{
            mTvClearCache.setText(DataCleanManager.getTotalCacheSize(this));
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(AppData.isNewApp()){
            mTvNewApp.setText("点击更新最新版本");
        }else{
            mTvNewApp.setText("当前已是最新版本");
        }
    }
    /**
     * 清除缓存
     */
    @OnClick(R.id.rl_clear_cache)
    public void clearCache(){
        mListConversation = JMessageClient.getConversationList();
        CommonUtils.showInfoDialog(this, "确定要清空缓存吗？", "提示", "清空", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataCleanManager.clearAllCache(SettingActivity.this);
                mTvClearCache.setText("0K");
                ToastUtil.show(SettingActivity.this,"清空成功");
            }
        }, null);
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
    @OnClick(R.id.rl_update_app)
    public void updateApp(){
        mPresenter.getNewAppVersion();
    }

    /**
     * 使用指南
     */
    @OnClick(R.id.tv_user_guide)
    public void userGuide(){
        String url = URL_USER_GUIDE+"?deviceId="+ AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
        WebViewActivity.loadUrl(this,url,"");
    }

    /**
     * 关于我们
     */
    @OnClick(R.id.tv_about_us)
    public void aboutUs(){
        String url = URL_ABOUT_US+"?deviceId="+ AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
        WebViewActivity.loadUrl(this,url,"");
    }

    /**
     * 意见反馈
     */
    @OnClick(R.id.tv_feedback)
    public void feedback(){
        String url = URL_FEEDBACK+"?deviceId="+ AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
        WebViewActivity.loadUrl(this,url,"");
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
    public void getNewAppVersionSuccess(NewAppVersionBean newAppVersionBean) {
        //获取当前应用版本信息
        getAppVersionName(this);
        String code = newAppVersionBean.getData().getVersion();
        String name = newAppVersionBean.getData().getVersionName();
        String updateDate = newAppVersionBean.getData().getUpdateDate();
        final String url = newAppVersionBean.getData().getPath();
        if (Double.parseDouble(code) > versioncode) {
            AppData.setIsNewApp(true);
            mTvNewApp.setText("点击更新最新版本");
            CommonUtils.showInfoDialog(this, "新版本号： "+name+"\n新版日期： "+updateDate, "发现新版本", "更新", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //执行下载
                    download(url);
                }
            }, null);
        }else{
            AppData.setIsNewApp(false);
            mTvNewApp.setText("当前已是最新版本");
            CommonUtils.showInfoDialog(SettingActivity.this, "当前已经是最新版本", "提示", "知道了", "", null, null);
        }
    }

    @Override
    public void loginOutSuccess() {
        JMessageClient.logout();
        MyApplication.clearLogin();
        MainActivity1.instance.setPagerOne();
        finish();
    }

    @Override
    public void loginOutError() {
        ToastUtil.show(this, "退出失败");
    }
}
