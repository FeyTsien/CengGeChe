package com.ygst.cenggeche.ui.activity.friendinfo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendoperate.FriendOperateActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FriendInfoActivity extends MVPBaseActivity<FriendInfoContract.View, FriendInfoPresenter> implements FriendInfoContract.View {

    public static FriendInfoActivity instance;
    public static final int GO_FRIEND_OPERATE = 110;

    private UserInfo mUserInfo;
    private boolean isFriend;
    private int theBtnSendMsgCode = -1;
    private String targetUsername = "";
    private String targetAppKey;
    private String UserAvatarUri;
    private String targetName;
    //目标所处自己好友名单下的状态
    private int isBlack;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.iv_gender)
    ImageView mIvGender;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_hometown)
    TextView mTvHometown;
    @BindView(R.id.tv_present_address)
    TextView mTvPresentAddress;
    @BindView(R.id.tv_education)
    TextView mTvEducation;
    @BindView(R.id.flow_layout_biaoqian)
    FlowLayout mFlowlBiaoQian;
    @BindView(R.id.tv_miaoshu)
    TextView mTvMiaoShu;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mTvTitle.setText("详细资料");
        mBtnSendMsg.setText("发消息");
        Intent intent = getIntent();
        targetUsername = intent.getStringExtra(JMessageUtils.TARGET_USERNAME);

        if (targetUsername.equals(AppData.getUserName())) {
            //如果看的是自己的资料隐藏此按钮
            mBtnSendMsg.setVisibility(View.GONE);
        }
        JMessageClient.getUserInfo(targetUsername, new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String s, UserInfo userInfo) {
                if (responseCode == 0) {
                    targetAppKey = userInfo.getAppKey();
                    isFriend = userInfo.isFriend();
                    if (isFriend) {
                        if (userInfo.getBlacklist() == 1) {
                            //在黑名单中
                            isBlack = 1;
                        } else if (userInfo.getBlacklist() == 0) {
                            //不在黑名单中
                            isBlack = 2;
                        }
                        theBtnSendMsgCode = 1;
                        //是好友则显示可以好友操作菜单
                        mIvMenu.setVisibility(View.VISIBLE);
                    } else {
                        theBtnSendMsgCode = 2;
                        //不是好友不显示
                        mIvMenu.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mPresenter.getFriendInfo(targetUsername);
    }

    @Override
    public void getFriendInfoError() {
        ToastUtil.show(this, "获取信息失败");
    }

    @Override
    public void getFriendInfoSuccess(UserBean userBean) {
        setFriendInfo(userBean);
    }

    private void setFriendInfo(UserBean friendInfo) {

        mRecyclerView.setAdapter(new RVAdapter(this, friendInfo.getPic()));
        if (!TextUtils.isEmpty(friendInfo.getData().getNickname())) {
            targetName = friendInfo.getData().getNickname();
        } else if (!TextUtils.isEmpty(friendInfo.getData().getUsername())) {
            targetName = friendInfo.getData().getNickname();
        }
        //名字
        mTvName.setText(targetName);
        //头像
        UserAvatarUri = friendInfo.getData().getUserPic();
        Glide.with(this)
                .load(UserAvatarUri)
                .centerCrop()
                .placeholder(R.mipmap.icon_my_avatar)
                .into(mIvAvatar);
        //性别符号
        if (friendInfo.getData().getGender() == 0) {
            mIvAvatar.setBorderColor(getResources().getColor(R.color.colorGirl));
            mIvGender.setImageResource(R.mipmap.icon_girl);
        } else if (friendInfo.getData().getGender() == 1) {
            mIvAvatar.setBorderColor(getResources().getColor(R.color.colorBoy));
            mIvGender.setImageResource(R.mipmap.icon_boy);
        }
        //年龄
        mTvAge.setText(friendInfo.getData().getAge() + "岁");
        //家乡
        mTvHometown.setText(friendInfo.getData().getHome());
        //现居地
        mTvPresentAddress.setText(friendInfo.getData().getLocation());
        //学历
        setEducation(friendInfo.getData().getEducation());
        //自我描述
        mTvMiaoShu.setText(friendInfo.getData().getUserSign());
        //个性标签
        List<String> listTag = friendInfo.getData().getTag();
        if (listTag != null && listTag.size() > 0) {
            for (int i = 0; i < listTag.size(); i++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.tag_bg);
                textView.setText(listTag.get(i));
                mFlowlBiaoQian.addView(textView);
            }
        }
    }

    /**
     * 填写学历
     *
     * @param education
     */
    private void setEducation(int education) {
        if (education == 1) {
            mTvEducation.setText("小学");
        } else if (education == 10) {
            mTvEducation.setText("初中");
        } else if (education == 15) {
            mTvEducation.setText("中专");
        } else if (education == 20) {
            mTvEducation.setText("高中");
        } else if (education == 25) {
            mTvEducation.setText("大专");
        } else if (education == 30) {
            mTvEducation.setText("本科");
        } else if (education == 40) {
            mTvEducation.setText("硕士");
        } else if (education == 50) {
            mTvEducation.setText("博士");
        } else if (education == 60) {
            mTvEducation.setText("博士后");
        }
    }

    @OnClick(R.id.iv_avatar)
    public void avatarOnClick() {
        Intent intent = new Intent(this, BigPicActivity.class);
        intent.putExtra("pic_uri", UserAvatarUri);
        startActivity(intent);
    }

    /**
     * 点击发消息/加好友 事件
     */
    @OnClick(R.id.btn_send_msg)
    public void btnOnClick() {
//        if (theBtnSendMsgCode == 1) {
        if (!AppData.isLoginEd() || JMessageClient.getMyInfo() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            //去发消息
            Intent intent = new Intent();
            intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
            intent.putExtra(JMessageUtils.TARGET_APP_KEY, targetAppKey);
            intent.putExtra(JMessageUtils.IS_FRIEND, isFriend);
            intent.setClass(this, MyChatActivity.class);
            startActivity(intent);
        }
//        } else if (theBtnSendMsgCode == 2) {
//            //去加好友
//            Intent intent = new Intent();
//            intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
//            intent.putExtra(JMessageUtils.TARGET_APP_KEY, targetAppKey);
//            intent.setClass(this, AddFriendActivity.class);
//            startActivity(intent);
//        }
    }

    /**
     * 前去好友操作
     */
    @OnClick(R.id.iv_menu)
    public void friendOperate() {
        Intent intent = new Intent(this, FriendOperateActivity.class);
        intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
        intent.putExtra(JMessageUtils.TARGET_APP_KEY, targetAppKey);
        intent.putExtra(JMessageUtils.IS_BLACK, isBlack);
        startActivityForResult(intent, GO_FRIEND_OPERATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GO_FRIEND_OPERATE:
                if (data != null) {
                    isBlack = data.getIntExtra(JMessageUtils.IS_BLACK, 0);
                }
                break;
        }
    }
}
