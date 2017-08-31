package com.ygst.cenggeche.ui.activity.registerinfo;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.utils.MD5Util;
import com.ygst.cenggeche.utils.TextViewUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterInfoActivity extends MVPBaseActivity<RegisterInfoContract.View, RegisterInfoPresenter> implements RegisterInfoContract.View {

    private String TAG = "RegisterInfoActivity";
    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    private String userName;
    private String nickname;
    private String birthday;
    private String pwd;
    private String confirmPWD;
    private int gender;


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_nickname)
    EditText mEtNickname;
    @BindView(R.id.tv_birthdate)
    TextView mTvBirthdate;
    @BindView(R.id.iv_boy)
    ImageView mIvBoy;
    @BindView(R.id.iv_girl)
    ImageView mIvGirl;
    @BindView(R.id.et_pwd)
    EditText mEtPWD;
    @BindView(R.id.et_confirm_pwd)
    EditText mEtConfirmPWD;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    /**
     * 选择日期
     */
    @OnClick(R.id.tv_birthdate)
    public void setBirthday() {
        showDialog(DATE_DIALOG);
    }

    /**
     * 选择性别--男
     */
    @OnClick(R.id.iv_boy)
    public void onClickBoy() {
        gender = 1;
        mIvBoy.setImageResource(R.mipmap.icon_boy_radio);
        mIvGirl.setImageResource(R.mipmap.icon_girl_radio_un);
    }

    /**
     * 选择性别--女
     */
    @OnClick(R.id.iv_girl)
    public void onClickGirl() {
        gender = 0;
        mIvBoy.setImageResource(R.mipmap.icon_boy_radio_un);
        mIvGirl.setImageResource(R.mipmap.icon_girl_radio);
    }

    /**
     * 提交注册信息
     */
    @OnClick(R.id.btn_submit)
    public void registrationConfirm() {
        nickname = mEtNickname.getText().toString();
        pwd = TextViewUtils.getText(mEtPWD);
        confirmPWD = TextViewUtils.getText(mEtConfirmPWD);
        birthday = TextViewUtils.getText(mTvBirthdate);
        if (TextUtils.isEmpty(nickname)) {
            ToastUtil.show(this, "请输入正确昵称");
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show(this, "请输入正确密码");
        } else if (!pwd.equals(confirmPWD)) {
            ToastUtil.show(this, "两次密码输入不一致");
        } else {
//            JMessageClient.register(userName, JMessageUtils.JMESSAGE_LOGIN_PASSWROD, new BasicCallback() {
//                @Override
//                public void gotResult(int responseCode, String responseMessage) {
//                    LogUtils.i(TAG, "s:" + responseMessage+" code:"+responseCode);
//                    if (responseCode == 0) {
//                        LogUtils.i(TAG, "极光注册成功了");
                        String password = MD5Util.string2MD5(pwd);
                        Map<String, String> map = new HashMap<>();
                        map.put("username", userName);
                        map.put("password", password);
                        map.put("nickname", nickname);
                        map.put("birthday", birthday);
                        map.put("gender", gender + "");
                        map.put("registrationId", AppData.getRegistrationId());
                        mPresenter.registrationConfirm(map);
//                    } else {
//
//                        ToastUtil.show(RegisterInfoActivity.this, "极光注册失败"+responseMessage);
//                    }
//                }
//            });

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("注册信息");

        //也可获取当前日期
//        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
//        t.setToNow(); // 取得系统时间。
//        int year = t.year;
//        int month = t.month;
//        int date = t.monthDay;
        //获取当前日期
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //显示当前日期
        display();
        //性别选项默认为女
        onClickGirl();
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
    }


    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        mTvBirthdate.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
    }


    @Override
    public void registrationSuccess() {
        Intent intent = new Intent();
        intent.putExtra("username",userName);
        intent.putExtra("password",pwd);
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        finish();
        ToastUtil.show(this, "欢迎您的加入");
    }

    @Override
    public void registrationError() {
        ToastUtil.show(this, "注册失败了");
    }
}
