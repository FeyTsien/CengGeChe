package com.ygst.cenggeche.ui.activity.setting;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.NewAppVersionBean;
import com.ygst.cenggeche.download.bean.Download;
import com.ygst.cenggeche.download.service.DownloadService;
import com.ygst.cenggeche.download.utils.StringUtils;
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

    public static final String MESSAGE_PROGRESS = "message_progress";
    //使用指南
    private final String URL_USER_GUIDE= UrlUtils.URL_H5+"/cenggeche/pages/help/help.html";
    //关于我们
    private final String URL_ABOUT_US= UrlUtils.URL_H5+"/cenggeche/pages/about/about.html";
    //意见反馈
    private final String URL_FEEDBACK= UrlUtils.URL_H5+"/cenggeche/pages/feedback/feedback.html";

    List<Conversation> mListConversation;

    private LocalBroadcastManager bManager;
    private ProgressBar progress;
    private TextView progress_text;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_clear_cache)
    TextView mTvClearCache;
    @BindView(R.id.tv_new_app)
    TextView mTvNewApp;
    @BindView(R.id.ll_update_app)
    LinearLayout mLlUpdateApp;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                progress.setProgress(download.getProgress());
                if (download.getProgress() == 100) {

                    progress_text.setText("File Download Complete");

                } else {

                    progress_text.setText(
                            StringUtils.getDataSize(download.getCurrentFileSize())
                                    + "/" +
                                    StringUtils.getDataSize(download.getTotalFileSize()));

                }
            }
        }
    };
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
        progress = (ProgressBar) findViewById(R.id.progress);
        progress_text = (TextView) findViewById(R.id.progress_text);
        //注册广播
        registerReceiver();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册时，使用注册时的manager解绑
        bManager.unregisterReceiver(broadcastReceiver);
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
    String url;
    @Override
    public void getNewAppVersionSuccess(NewAppVersionBean newAppVersionBean) {
        //获取当前应用版本信息
        getAppVersionName(this);
        String code = newAppVersionBean.getData().getVersion();
        String name = newAppVersionBean.getData().getVersionName();
        String updateDate = newAppVersionBean.getData().getUpdateDate();
        url = newAppVersionBean.getData().getPath();
        if (Double.parseDouble(code) > versioncode) {
            AppData.setIsNewApp(true);
            mTvNewApp.setText("点击更新最新版本");
            CommonUtils.showInfoDialog(this, "新版本号： "+name+"\n新版日期： "+updateDate, "发现新版本", "更新", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mLlUpdateApp.setVisibility(View.VISIBLE);
                    //执行下载
                    Intent intent = new Intent(SettingActivity.this, DownloadService.class);
                    intent.putExtra("url","https://pro-app-qn.fir.im/2bfccf3789a41da17b9bec41c93d811d7d41ce2c.apk?attname=app-yiyongche-release.apk_1.0.3.apk&e=1505502883&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:QFpO80MNGSBBMOG4w0QLGX4sYW4=");
                    startService(intent);
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


    private void registerReceiver() {

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }
}
