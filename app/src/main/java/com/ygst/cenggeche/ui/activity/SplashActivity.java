package com.ygst.cenggeche.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.tsien.myapplication.asimplecachedemo.ACacheMainActivity;
import com.umeng.analytics.MobclickAgent;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.activity.base.BaseActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.main.MainActivity1;
import com.ygst.cenggeche.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SplashActivity extends BaseActivity {
    String url =  "http://m.1yongche.com/page/merchant/history.html?uid=0&sid=1";
    //ButterKnife是一个专注于Android系统的View注入框架,可以减少大量的findViewById以及setOnClickListener代码，可视化一键生成。
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private Unbinder bind;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //取消状态栏(已在style文件中设置了)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LogUtils.Builder builder = new LogUtils.Builder();
        //logSwitch为false关闭日志
        builder.setLogSwitch(true).create();

        /** 设置是否对日志信息进行加密, 默认false(不加密).------友盟 */
//        AnalyticsConfig.enableEncrypt(false);//6.0.0版本以前
        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后

        //绑定activity
        bind = ButterKnife.bind(this);
        ivPic.setImageResource(R.drawable.img);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initData();
//            }
//        }, 2000);

    }

    private void toMainActivity() {
//        if(MyApplication.isLoginEd()){
            CommonUtils.startActivity(this,LoginActivity.class);
//        }else{
//            CommonUtils.startActivity(this,LoginActivity.class);
//        }
        finish();
    }

    public void btnMain(View view){
        initData();
    }
    public void btnJChat(View view){
        toIMActivity();
//        CommonUtils.startActivity(this, com.jarek.imageselect.activity.MainActivity.class);
    }

    private void initData() {
        CommonUtils.startActivity(this,MainActivity1.class);
        finish();
    }

    private void toIMActivity(){
        CommonUtils.startActivity(this, ACacheMainActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }
}
