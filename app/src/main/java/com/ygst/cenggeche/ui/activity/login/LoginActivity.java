package com.ygst.cenggeche.ui.activity.login;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.bean.LoginBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.register.RegisterActivity;
import com.ygst.cenggeche.ui.widget.TimeCount;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.MD5Util;
import com.ygst.cenggeche.utils.TextViewUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    private static String TAG = "LoginActivity";
    private String checkType = LoginBean.PWD_TO_LOGIN;
    private TimeCount timeCount;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_username)
    EditText mEtUserName;
    @BindView(R.id.et_pwd_code)
    EditText mEtPwdCode;
    @BindView(R.id.tv_forgot_pwd)
    TextView mTvForgotPwd;
    @BindView(R.id.btn_getCode)
    Button mBtnGetCode;
    @BindView(R.id.btn_login_type)
    Button mBtnLoginType;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    /**
     * 切换登录方式
     */
    @OnClick(R.id.btn_login_type)
    public void setLoginType() {
        //如果本身是密码登录，点击切换成密码登录，反之同理
        if (checkType.equals(LoginBean.PWD_TO_LOGIN)) {
            //变成了验证码登录
            checkType = LoginBean.CODE_TO_LOGIN;
            mEtPwdCode.setHint("请输入验证码");
            //输入框变成只能输入手机号的模式
            mEtPwdCode.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            mBtnGetCode.setVisibility(View.VISIBLE);
            mTvForgotPwd.setVisibility(View.GONE);
            mBtnLoginType.setText("切换密码登录");
        } else {
            //变成了密码登录
            checkType = LoginBean.PWD_TO_LOGIN;
            mEtPwdCode.setHint("请输入密码");
            mEtPwdCode.setInputType(EditorInfo.IME_ACTION_NONE);
            mEtPwdCode.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            mBtnGetCode.setVisibility(View.GONE);
            mTvForgotPwd.setVisibility(View.VISIBLE);
            mBtnLoginType.setText("切换验证码登录");
        }
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.btn_getCode)
    public void getCode() {
        String username = TextViewUtils.getText(mEtUserName);
        final String pwdOrCode = TextViewUtils.getText(mEtPwdCode);
        if (!TextUtils.isEmpty(username)) {
            if (CommonUtils.isUserNumber(username)) {
                try {
                    mPresenter.getSMSCode(username);
                    timeCount.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.showInfoDialog(this, "请输入正确的手机号码");
//                ToastUtil.show(this, "请输入正确的手机号码");
            }
        } else {
//            CommonUtils.showInfoDialog(this, "请输入您的手机号码");
            ToastUtil.show(this, "请输入您的手机号码");
        }
    }

    /**
     * 去登录
     */
    @OnClick(R.id.btn_login)
    public void login() {
        final String username = TextViewUtils.getText(mEtUserName);
        final String pwdOrCode = TextViewUtils.getText(mEtPwdCode);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwdOrCode)) {
            //先校验账号是否被注册,成功后在获取验证码
            try {
                mPresenter.checkIsRegist(username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (checkType.equals(LoginBean.PWD_TO_LOGIN)) {
//                CommonUtils.showInfoDialog(this, "手机号码或密码不能为空");
                ToastUtil.show(this, "手机号码或密码不能为空");
            } else {
//                CommonUtils.showInfoDialog(this, "手机号码或验证码不能为空");
                ToastUtil.show(this, "手机号码或验证码不能为空");
            }
        }

    }

    /**
     * 去注册账号
     */
    @OnClick(R.id.rl_register)
    public void register() {
        timeCount.cancel();
        timeCount.onFinish();
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra(RegisterActivity.TYPE,"register");
        startActivity(intent);
    }

    /**
     *忘记密码
     */
    @OnClick(R.id.tv_forgot_pwd)
    public void resetPwd(){
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra(RegisterActivity.TYPE,"resetPwd");
        startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //找控件
        initView();
    }

    private void initView() {
        mTvTitle.setText("登录");
        Intent intent = getIntent();
        mEtUserName.setText(intent.getStringExtra("username"));
        mEtPwdCode.setText(intent.getStringExtra("password"));
        timeCount = new TimeCount(60000, 1000);
        timeCount.setButton(mBtnGetCode);
    }


    /**
     * 未注册，需先注册，才可登录
     */
    @Override
    public void unregistered() {
        CommonUtils.showInfoDialog(this, "账号未注册，请先注册", "小蹭提示", "前往", "不去", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonUtils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        }, null);
    }

    /**
     * 已注册，可以登录
     */
    @Override
    public void registered() {
        String username = TextViewUtils.getText(mEtUserName);
        String pwdOrCode = TextViewUtils.getText(mEtPwdCode);

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("checkType", checkType);
        if (checkType.equals("1")) {
            //密码登录
            String password = MD5Util.string2MD5(pwdOrCode);
            map.put("password", password);
        } else {
            //验证码登录
            map.put("smsCode", pwdOrCode);
        }
        map.put("registrationId", AppData.getRegistrationId());
        mPresenter.login(map);
    }


    @Override
    public void getSMSCodeSuccess(CodeBean codeBean) {
        ToastUtil.show(this, "获取验证码成功");
    }

    @Override
    public void getSMSCodeError() {
        timeCount.cancel();
        timeCount.onFinish();
        ToastUtil.show(this, "获取验证码失败");
    }

    @Override
    public void loginSuccess(final LoginBean loginBean) {
        String username = TextViewUtils.getText(mEtUserName);
        JMessageClient.login(username, JMessageUtils.JMESSAGE_LOGIN_PASSWROD, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String LoginDesc) {
                if (responseCode == 0) {
                    ToastUtil.show(LoginActivity.this, "登录成功");
                    timeCount.cancel();
                    timeCount.onFinish();
                    MyApplication.clearLogin();
                    // 储存登陆状态
                    AppData.setIsLoginEd(true);
                    // 保存 uid
                    AppData.saveUid(loginBean.getData().getId() + "");
                    // 保存 username
                    AppData.saveUserName(loginBean.getData().getUsername());
                    // 保存 nickname
                    AppData.saveUserName(loginBean.getData().getUsername());
                    // 保存昵称 nickname
                    AppData.saveNickname(loginBean.getData().getNickname());

                    //开启友盟账号统计
                    //（如果是使用第三方账号登录时，如新浪微博：MobclickAgent.onProfileSignIn("WB"，"userID")）;
                    MobclickAgent.onProfileSignIn(loginBean.getData().getId() + "");
                    CommonUtils.finishActivity(LoginActivity.this);
                } else {
                    ToastUtil.show(LoginActivity.this, "登录失败");
                }
            }
        });

    }

    @Override
    public void loginError() {
        LogUtils.i(TAG,"登录失败了");
    }
}
