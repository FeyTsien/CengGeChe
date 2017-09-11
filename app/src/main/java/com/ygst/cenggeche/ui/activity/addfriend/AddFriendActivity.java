package com.ygst.cenggeche.ui.activity.addfriend;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.B2.ApplyBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
        Map<String,String> map = new HashMap();
        map.put("myusername",targetUserName);
        map.put("fusername", AppData.getUserName());
        map.put("applyInfo",reason);
        map.put("appkey",targetAppKey);
        mPresenter.sendAddFriend(map);
    }
//
//    @Override
//    public void yesAgreeSuccess(int position) {
//        ToastUtil.show(this, "同意申请");
//        ContactManager.acceptInvitation(targetUserName, targetAppKey, new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String responseMessage) {
//                if (0 == responseCode) {
//                    //接收好友请求成功
//                } else {
//                    //接收好友请求失败
//                }
//            }
//        });
//        mListApplyBean.get(position).setIsAgree(1);
//        mListApplyBean.set(position, mListApplyBean.get(position));
//        mCache.put(JMessageUtils.APPLE_BEAN, (Serializable) mListApplyBean);
//        Intent mIntent = new Intent();
//        setResult(RESULT_OK, mIntent);
//        finish();
//    }
//
//    @Override
//    public void yesAgreeError() {
//        ToastUtil.show(this, "同意申请失败了，请重试");
//    }


    @Override
    public void sendSucceed() {

        ToastUtil.show(AddFriendActivity.this, "申请好友发送成功，等待对方回应");
    }

    @Override
    public void sendFail() {
        ToastUtil.show(this, "申请好友发送失败了");
    }
}
