package com.ygst.cenggeche.ui.activity.friendoperate;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendinfo.FriendInfoActivity;
import com.ygst.cenggeche.ui.activity.setnotename.SetNoteNameActivity;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FriendOperateActivity extends MVPBaseActivity<FriendOperateContract.View, FriendOperatePresenter> implements FriendOperateContract.View {

    public static final int GO_SET_NOTENAME = 111;

    private UserInfo mUserInfo;
    private String targetUsername;
    private String targetAppKey;
    private String noteName;
    private String nickname;
    private int isBlack;
    private boolean myIsChecked;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_name)
    TextView mTvNotename;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.iv_gender)
    ImageView mIvGender;

    @BindView(R.id.sbtn_add_blacklist)
    Switch SBtnAddBlackList;

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
        isBlack = getIntent().getIntExtra(JMessageUtils.IS_BLACK, 0);
        targetUsername = getIntent().getStringExtra(JMessageUtils.TARGET_USERNAME);
        targetAppKey = getIntent().getStringExtra(JMessageUtils.TARGET_APP_KEY);
        JMessageClient.getUserInfo(targetUsername, targetAppKey, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                mUserInfo = userInfo;
                if (mUserInfo != null) {
                    //显示头像
                    mUserInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int status, String desc, Bitmap bitmap) {
                            if (status == 0) {
                                mCivAvatar.setImageBitmap(bitmap);
                            } else {
                                mCivAvatar.setImageResource(R.mipmap.icon_my_avatar);
                            }
                        }
                    });
                    ///显示性别
                    if (mUserInfo.getGender().equals(UserInfo.Gender.female)) {
                        mCivAvatar.setBorderColor(getResources().getColor(R.color.colorSub2));
                        mIvGender.setImageResource(R.mipmap.icon_girl);
                    } else if (mUserInfo.getGender().equals(UserInfo.Gender.male)) {
                        mCivAvatar.setBorderColor(getResources().getColor(R.color.colorSub));
                        mIvGender.setImageResource(R.mipmap.icon_boy);
                    } else {
                    }
                    if (!TextUtils.isEmpty(mUserInfo.getNotename())){
                        noteName = mUserInfo.getNotename();
                        mTvNickname.setText("昵称: " + mUserInfo.getNickname());
                    } else if (!TextUtils.isEmpty(mUserInfo.getNickname())) {
                        noteName = mUserInfo.getNickname();
                    } else {
                        noteName = mUserInfo.getUserName();
                    }
                    mTvNotename.setText(noteName);
                }
            }
        });


        if (isBlack == 2) {
            //为1时是正常好友状态，不显示处于黑名单状态
            SBtnAddBlackList.setChecked(false);
        } else if (isBlack == 1) {
            //为3时显示处于黑名单状态
            SBtnAddBlackList.setChecked(true);
        }

        SBtnAddBlackList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //加入黑名单
                    CommonUtils.showInfoDialog(FriendOperateActivity.this, "确定要拉黑该好友吗？", "提示", "拉黑", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isBlack = 1;
                            mPresenter.addBlackList(AppData.getUserName(), targetUsername);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SBtnAddBlackList.setChecked(false);
                        }
                    });
                } else {
                    if (isBlack == 1) {
                        isBlack = 2;
                        //移除黑名单
                        mPresenter.removeBlackList(AppData.getUserName(), targetUsername);
                    }
                }
            }
        });
    }

    /**
     * 修改备注名
     */
    @OnClick(R.id.tv_set_note)
    public void goSetNote() {
        Intent intent = new Intent(this, SetNoteNameActivity.class);
        intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
        intent.putExtra("NoteName", noteName);
        startActivityForResult(intent, GO_SET_NOTENAME);
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
        if (FriendInfoActivity.instance != null) {
            FriendInfoActivity.instance.finish();
        }
    }

    @Override
    public void delFriendError() {

    }

    @Override
    public void addBlackListSuccess() {
//        if (!SBtnAddBlackList.isChecked()) {
//            SBtnAddBlackList.setChecked(true);
//        }
    }

    @Override
    public void addBlackListError() {
        isBlack = 2;
//        if (SBtnAddBlackList.isChecked()) {
//            SBtnAddBlackList.setChecked(false);
//        }
    }

    @Override
    public void removeBlackListSuccess() {
        isBlack = 2;
//        if (SBtnAddBlackList.isChecked()) {
//            SBtnAddBlackList.setChecked(false);
//        }
    }

    @Override
    public void removeBlackListError() {
        isBlack = 1;
//        if (!SBtnAddBlackList.isChecked()) {
//            SBtnAddBlackList.setChecked(true);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GO_SET_NOTENAME:
                if (data != null) {
                    noteName = data.getStringExtra("NoteName");
                    mTvNotename.setText(noteName);
                    mTvNickname.setText("昵称: " + mUserInfo.getNickname());
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(JMessageUtils.IS_BLACK, isBlack);
        intent.putExtra("NoteName", noteName);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
