package com.ygst.cenggeche.ui.activity.register;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.registerinfo.RegisterInfoActivity;
import com.ygst.cenggeche.ui.activity.resetpwd.ResetPwdActivity;
import com.ygst.cenggeche.ui.widget.TimeCount;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.TextViewUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {

    private String TAG = "RegisterActivity";
    public static String TYPE="type";
    private String type;
    private TimeCount timeCount;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_sms_code)
    EditText mEtSmsCode;
    @BindView(R.id.btn_get_sms_code)
    Button mBtnGetSmsCode;
    @BindView(R.id.tv_tishi)
    TextView mTvTiShi;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    //获取验证
    @OnClick(R.id.btn_get_sms_code)
    public void getSmsCode() {
        String username = TextViewUtils.getText(mEtPhone);
        if (!TextUtils.isEmpty(username)) {
            if (CommonUtils.isUserNumber(username)) {
                //先校验账号是否被注册,成功后在获取验证码
                try {
                    if(type.equals("register")){
                        //如果是注册账号则走此接口
                        mPresenter.checkIsRegist(mEtPhone.getText().toString());
                    }else{
                        //不是注册的时候，直接走获取验证码接口
                        timeCount.start();
                        mPresenter.getSMSCode(mEtPhone.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.showInfoDialog(this, "请输入正确的手机号码");
            }
        } else {
            ToastUtil.show(this, "请输入您的手机号码");
        }
    }

    //下一步（校验验证码是否正确）
    @OnClick(R.id.btn_next)
    public void nextStep() {
        final String username = TextViewUtils.getText(mEtPhone);
        final String code = TextViewUtils.getText(mEtSmsCode);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(code)) {
            if (CommonUtils.isUserNumber(username)) {
                try {
                    mPresenter.checkSMSCode(username, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.showInfoDialog(this, "请输入正确的手机号码");
            }
        } else {
            ToastUtil.show(this, "手机号码或验证码不能为空");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        type = getIntent().getStringExtra(TYPE);
        if(type.equals("register")){
            mTvTitle.setText("注册账号");
        }else if(type.equals("resetPwd")){
            mTvTitle.setText("忘记密码");
            mTvTiShi.setVisibility(View.INVISIBLE);
        }
        timeCount = new TimeCount(60000, 1000);
        timeCount.setButton(mBtnGetSmsCode);
    }

    /**
     * 手机号未被注册，可以获取验证码
     */
    @Override
    public void checkIsRegistSuccess() {
        LogUtils.i(TAG, "账号未被注册，可以使用");
        try {
            timeCount.start();
            mPresenter.getSMSCode(mEtPhone.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkIsRegistError() {
        ToastUtil.show(this, "账号已被注册,请更换账号");
    }

    @Override
    public void getSMSCodeSuccess(CodeBean codeBean) {
        ToastUtil.show(this, "验证码已发送");
        timeCount.cancel();
        timeCount.onFinish();
    }

    @Override
    public void getSMSCodeError() {
        ToastUtil.show(this, "验证码发送失败");
        timeCount.cancel();
        timeCount.onFinish();
    }

    @Override
    public void checkSMSCodeSuccess(CodeBean codeBean) {
        ToastUtil.show(this, "校验成功");
        Intent intent = new Intent();
        intent.putExtra("username", mEtPhone.getText().toString());
        if(type.equals("register")){
            //注册账号
            intent.setClass(this, RegisterInfoActivity.class);
        }else if(type.equals("resetPwd")){
            //重置密码
            intent.setClass(this, ResetPwdActivity.class);
        }
        startActivity(intent);
        CommonUtils.finishActivity(this);
    }

    @Override
    public void checkSMSCodeError() {
        ToastUtil.show(this, "校验失败");
    }
}
