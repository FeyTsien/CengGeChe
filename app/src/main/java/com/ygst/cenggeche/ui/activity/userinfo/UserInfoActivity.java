package com.ygst.cenggeche.ui.activity.userinfo;


import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendinfo.BigPicActivity;
import com.ygst.cenggeche.ui.activity.friendinfo.RVAdapter;
import com.ygst.cenggeche.ui.activity.friendoperate.FriendOperateActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.activity.suretravel.SureTravelActivity;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

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

public class UserInfoActivity extends MVPBaseActivity<UserInfoContract.View, UserInfoPresenter> implements UserInfoContract.View {

    //车主认证
    private final String URL_OWNER_AUTH = UrlUtils.URL_H5 + "/cenggeche/pages/carrz/carrz.html";

    public static UserInfoActivity instance;
    public static final int GO_FRIEND_OPERATE = 110;

    private UserDetailsInfoBean.DataBean mDataBean;
    private boolean isFriend;
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
    @BindView(R.id.ll_stroke)
    LinearLayout mLlStroke;
    @BindView(R.id.tv_posted_time)
    TextView mTvPostedTime;
    @BindView(R.id.tv_start_addr)
    TextView mTvStartAddr;
    @BindView(R.id.tv_end_addr)
    TextView mTvEndAddr;
    @BindView(R.id.tv_car)
    TextView mTvCar;
    @BindView(R.id.tv_depar_time)
    TextView mTvDeparTime;
    @BindView(R.id.tv_comments)
    TextView mTvComments;
    @BindView(R.id.btn_apply_together)
    Button mBtnApplyTogether;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
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
                        //是好友则显示可以好友操作菜单
                        mIvMenu.setVisibility(View.VISIBLE);
                    } else {
                        //不是好友不显示
                        mIvMenu.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mPresenter.getUserInfo(targetUsername);
    }

    @Override
    public void getUserInfoError() {
        ToastUtil.show(this, "获取信息失败");
    }

    @Override
    public void getcheckInfoSuccess(CheckPeerBean checkPeerBean, String userFlag) {

        Intent intent = new Intent(this, SureTravelActivity.class);
        intent.putExtra("CARTYPE", userFlag + "");
        intent.putExtra("uid", mDataBean.getUid() + "");
        intent.putExtra("sid", mDataBean.getId() + "");
        intent.putExtra("REQUEST", 2 + "");
        //判断是否自己有行程
        if (checkPeerBean.getData().getUid() != 0) {
            intent.putExtra("endAddr", checkPeerBean.getData().getEndAddr());
            intent.putExtra("startAddr", checkPeerBean.getData().getStartAddr());
            intent.putExtra("postedTime", checkPeerBean.getData().getDeparTime());
            intent.putExtra("strokeFlag", "2");
            intent.putExtra("comment", checkPeerBean.getData().getComments());
            intent.putExtra("id", checkPeerBean.getData().getId() + "");
        } else {
            intent.putExtra("endAddr", mDataBean.getEndAddr());
            intent.putExtra("startAddr", mDataBean.getStartAddr());
            intent.putExtra("postedTime", mDataBean.getDeparTime());
            intent.putExtra("strokeFlag", "3");
            intent.putExtra("comment", mDataBean.getComments());
            intent.putExtra("id", "");
        }
        startActivity(intent);
    }

    @Override
    public void getcheckInfoFail() {

    }

    @Override
    public void getUserInfoSuccess(UserDetailsInfoBean userDetailsInfoBean) {
        mRecyclerView.setAdapter(new RVAdapter(this, userDetailsInfoBean.getPic()));
        setUserInfo(userDetailsInfoBean.getUser());
        if (userDetailsInfoBean.getData().getId() != 0) {
            mDataBean = userDetailsInfoBean.getData();
            mLlStroke.setVisibility(View.VISIBLE);
            setStrokeInfo(userDetailsInfoBean.getData());
        }
    }

    /**
     * 显示目标用户详细资料
     *
     * @param userBean
     */
    private void setUserInfo(UserDetailsInfoBean.UserBean userBean) {
        if (!TextUtils.isEmpty(userBean.getNickname())) {
            targetName = userBean.getNickname();
        } else if (!TextUtils.isEmpty(userBean.getUsername())) {
            targetName = userBean.getUsername();
        }
        //名字
        mTvName.setText(targetName);
        //头像
        UserAvatarUri = userBean.getUserPic();
        int resourceId = R.mipmap.icon_my_avatar;
        //性别符号
        if (userBean.getGender() == 0) {
            mIvGender.setImageResource(R.mipmap.icon_girl);
            mIvAvatar.setBorderColor(getResources().getColor(R.color.colorGirl));
            resourceId = R.mipmap.icon_avatar_girl;
        } else if (userBean.getGender() == 1) {
            mIvGender.setImageResource(R.mipmap.icon_boy);
            mIvAvatar.setBorderColor(getResources().getColor(R.color.colorBoy));
            resourceId = R.mipmap.icon_avatar_boy;
        }
        //头像
        Glide.with(this)
                .load(UserAvatarUri)
                .apply(new RequestOptions()
                        .dontAnimate()//不使用glide默认动画(解决圆形图片二次加载问题)
                        .centerCrop()
                        .placeholder(resourceId))
                .into(mIvAvatar);

        //年龄
        mTvAge.setText(userBean.getAge() + "岁");
        //家乡
        mTvHometown.setText(userBean.getHome());
        //现居地
        mTvPresentAddress.setText(userBean.getLocation());
        //学历
        setEducation(userBean.getEducation());
        //自我描述
        mTvMiaoShu.setText(userBean.getUserSign());
        //个性标签
        List<String> listTag = userBean.getTag();
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

    private void setStrokeInfo(UserDetailsInfoBean.DataBean dataBean) {
        mTvPostedTime.setText(dataBean.getPostedTime() + "发布");
        mTvStartAddr.setText(dataBean.getStartAddr());
        mTvEndAddr.setText(dataBean.getEndAddr());
        //判断车辆信息是否为空
        if (!TextUtils.isEmpty(dataBean.getBrand())) {
            mTvCar.setVisibility(View.VISIBLE);
            mTvCar.setText(dataBean.getBrand() + "　" + dataBean.getColor());
        } else {
            mTvCar.setVisibility(View.GONE);
        }
        mTvDeparTime.setText(dataBean.getDeparTime());
        mTvComments.setText(dataBean.getComments());
        if (dataBean.getUserFlag() == 1) {
            mBtnApplyTogether.setText("带上TA");
        } else if (dataBean.getUserFlag() == 2) {
            mBtnApplyTogether.setText("蹭TA车");
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

    @OnClick(R.id.btn_apply_together)
    public void applyTogether() {

        if (mDataBean.getUserFlag() == 1) {
            if (AppData.getUserStatus() == 1) {
                mPresenter.checkApplyPeerInfo(2 + "", mDataBean.getId() + "");
            } else {
                CommonUtils.showInfoDialog(this, "您还没有车主认证，请前去认证？", "提示", "认证", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url3 = URL_OWNER_AUTH + "?deviceId=" + AppData.getAndroidId() + "&os=" + "android" + "&uid=" + AppData.getUid();
                        WebViewActivity.loadUrl(UserInfoActivity.this, url3, "");
                    }
                }, null);
            }
        } else {
            mPresenter.checkApplyPeerInfo(1 + "", mDataBean.getId() + "");
        }
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

