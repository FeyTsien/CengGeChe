package com.ygst.cenggeche.ui.activity.resetpwd;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UsernamePwdUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ResetPwdActivity extends MVPBaseActivity<ResetPwdContract.View, ResetPwdPresenter> implements ResetPwdContract.View {

    private String newPwd;
    private String confirmPwd;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_new_pwd)
    EditText mEtNewPWD;
    @BindView(R.id.et_confirm_pwd)
    EditText mEtConfirmPWD;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("重置密码");
    }

    /**
     * 重置密码
     */
    @OnClick(R.id.btn_submit)
    public void resetPwd(){
        newPwd = mEtNewPWD.getText().toString();
        confirmPwd = mEtConfirmPWD.getText().toString();
        if(UsernamePwdUtils.isPasswordStandard(newPwd)){
            if(!newPwd.equals(confirmPwd)){
                ToastUtil.show(this,"两次密码输入不一致");
            }else{
                mPresenter.resetPwd(getIntent().getStringExtra("username"),newPwd);
            }
        }else{
            CommonUtils.showInfoDialog(this, "密码只能为8至16位的字母和数字的组合", "提示", "知道了", "", null, null);
        }
    }

    @Override
    public void resetPwdSuccess() {
        finish();
        ToastUtil.show(this,"重置密码成功");
    }

    @Override
    public void resetPwdError() {
        ToastUtil.show(this,"重置密码失败");
    }
}
