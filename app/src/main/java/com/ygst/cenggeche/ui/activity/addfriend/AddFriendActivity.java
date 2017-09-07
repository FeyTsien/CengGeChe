package com.ygst.cenggeche.ui.activity.addfriend;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.ApplyBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddFriendActivity extends MVPBaseActivity<AddFriendContract.View, AddFriendPresenter> implements AddFriendContract.View {

    private String TAG = "AddFriendActivity";

    String targetUserName;
    String targetAppKey;
    String reason;
    List<ApplyBean> mListApplyBean;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_reason)
    EditText mEditTextReason;

    @BindView(R.id.bt_submit)
    Button mBtnSubmit;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
        ;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friends;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("申请好友");
        mListApplyBean = (List<ApplyBean>) mCache.getAsObject(JMessageUtils.APPLE_BEAN);
        if (mListApplyBean == null) {
            mListApplyBean = new ArrayList<>();
        }

    }

    /**
     * 发送申请
     */
    @OnClick(R.id.bt_submit)
    public void addFriendSubmit() {
        Intent intent = this.getIntent();
        targetUserName = intent.getStringExtra(JMessageUtils.TARGET_USERNAME);
        targetAppKey = intent.getStringExtra(JMessageUtils.TARGET_APP_KEY);
        reason = mEditTextReason.getText().toString();
        for (int i = 0; i < mListApplyBean.size(); i++) {
            if (targetUserName.equals(mListApplyBean.get(i).getFromUsername()) && mListApplyBean.get(i).getIsAgree() == 3) {
                ToastUtil.show(AddFriendActivity.this, "已收到好友发的申请，直接同意");
                mPresenter.yesAgree(AppData.getUserName(), targetUserName,i);
                return;
            }
        }
        ContactManager.sendInvitationRequest(targetUserName, targetAppKey, reason, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                LogUtils.i(TAG, "responseMessage: " + responseMessage);
                if (0 == responseCode) {
                    finish();
                    ToastUtil.show(AddFriendActivity.this, "申请好友发送成功，等待对方回应");
                } else {
                    //好友请求发送失败
                    ToastUtil.show(AddFriendActivity.this, "申请好友发送失败");
                }
            }
        });
    }

    @Override
    public void yesAgreeSuccess(int position) {
        ToastUtil.show(this, "同意申请");
        mListApplyBean.get(position).setIsAgree(1);
        mListApplyBean.set(position, mListApplyBean.get(position));
        mCache.put(JMessageUtils.APPLE_BEAN, (Serializable) mListApplyBean);
        Intent mIntent = new Intent();
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public void yesAgreeError() {
        ToastUtil.show(this, "同意申请失败了，请重试");
    }


//    @Override
//    public void sendSucceed() {
//
//        ContactManager.sendInvitationRequest(targetUserName, targetAppKey, reason, new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String responseMessage) {
//                LogUtils.i(TAG, "responseMessage: " + responseMessage);
//                if (0 == responseCode) {
//                    finish();
//                    ToastUtil.show(AddFriendActivity.this, "申请好友发送成功，等待对方回应");
//                } else {
//                    //好友请求发送失败
//                    ToastUtil.show(AddFriendActivity.this, "申请好友发送失败");
//                }
//            }
//        });
//    }
//
//    @Override
//    public void sendFail() {
//        ToastUtil.show(this, "申请好友发送失败了");
//    }
}
