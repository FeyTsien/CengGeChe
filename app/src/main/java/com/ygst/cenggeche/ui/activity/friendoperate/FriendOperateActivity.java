package com.ygst.cenggeche.ui.activity.friendoperate;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.widget.SwitchButton;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FriendOperateActivity extends MVPBaseActivity<FriendOperateContract.View, FriendOperatePresenter> implements FriendOperateContract.View {

    private String targetUsername;
    private int isBlack;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.sbtn_add_blacklist)
    SwitchButton SBtnAddBlackList;

    @OnClick(R.id.iv_back)
    public void goBack() {
        Intent intent = new Intent();
        intent.putExtra(JMessageUtils.IS_BLACK, isBlack);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_operate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvTitle.setText("好友设置");
        targetUsername = getIntent().getStringExtra(JMessageUtils.TARGET_USERNAME);
        isBlack = getIntent().getIntExtra(JMessageUtils.IS_BLACK, 0);
        if (isBlack == 2) {
            //为1时是正常好友状态，不显示处于黑名单状态
            SBtnAddBlackList.setChecked(false);
        } else if (isBlack == 1) {
            //为3时显示处于黑名单状态
            SBtnAddBlackList.setChecked(true);
        }

        SBtnAddBlackList.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    //加入黑名单
                    CommonUtils.showInfoDialog(FriendOperateActivity.this, "确定要拉黑该好友吗？", "提示", "拉黑", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isBlack = 1;
                            mPresenter.addBlackList(AppData.getUserName(), targetUsername);
                        }
                    }, null);
                } else {
                    isBlack = 2;
                    //移除黑名单
                    mPresenter.removeBlackList(AppData.getUserName(), targetUsername);
                }
            }
        });
    }

    /**
     * 删除好友
     */
    @OnClick(R.id.btn_del_friend)
    public void delFriend() {
        showDelFriendDialog();
    }

    /**
     * 删除好友提示框
     */
    public void showDelFriendDialog() {
        CommonUtils.showInfoDialog(this, "确定要删除该好友吗？", "提示", "删除", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.delFriend(AppData.getUserName(), targetUsername);
            }
        }, null);
    }

    @Override
    public void delFriendSuccess() {
        finish();
    }

    @Override
    public void delFriendError() {

    }

    @Override
    public void addBlackListSuccess() {
        if (!SBtnAddBlackList.isChecked()) {
            SBtnAddBlackList.setChecked(true);
        }
    }

    @Override
    public void addBlackListError() {
        isBlack = 2;
        if (SBtnAddBlackList.isChecked()) {
            SBtnAddBlackList.setChecked(false);
        }
    }

    @Override
    public void removeBlackListSuccess() {
        isBlack = 2;
        if (SBtnAddBlackList.isChecked()) {
            SBtnAddBlackList.setChecked(false);
        }
    }

    @Override
    public void removeBlackListError() {
        isBlack = 1;
        if (!SBtnAddBlackList.isChecked()) {
            SBtnAddBlackList.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(JMessageUtils.IS_BLACK, isBlack);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
