package com.ygst.cenggeche.ui.activity.friendinfo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendoperate.FriendOperateActivity;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.MyTextDrawable;
import com.ygst.cenggeche.ui.widget.TextDrawable;
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

    public static final int GO_FRIEND_OPERATE = 11051;
    private UserBean.DataBean friendInfo;
    private String targetUsername;
    //目标所处自己好友名单下的状态
    private int friendStatus;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
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

    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    /**
     * 前去好友操作
     */
    @OnClick(R.id.iv_menu)
    public void friendOperate() {
        Intent intent = new Intent(this, FriendOperateActivity.class);
        intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
        intent.putExtra(JMessageUtils.TARGET_FRIENDSTATUS, friendStatus);
        startActivityForResult(intent, GO_FRIEND_OPERATE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_info;
    }

    private Integer[] mImageIds = {R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.f,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mTvTitle.setText("详细资料");
        Intent intent = getIntent();
        targetUsername = intent.getStringExtra(JMessageUtils.TARGET_USERNAME);
        friendStatus = intent.getIntExtra(JMessageUtils.TARGET_FRIENDSTATUS, 0);
        JMessageClient.getUserInfo(targetUsername, new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String s, UserInfo userInfo) {
                if (responseCode == 0) {
                    if (userInfo.isFriend()) {
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
        mRecyclerView.setAdapter(new RVAdapter(this));
        mPresenter.getFriendInfo(targetUsername);
    }

    @Override
    public void getFriendInfoSuccess(UserBean.DataBean dataBean) {
        ToastUtil.show(this, "获取成功");
        setFriendInfo(dataBean);
    }

    private void setFriendInfo(UserBean.DataBean friendInfo) {
        String name = "";
        if (!TextUtils.isEmpty(friendInfo.getNickname())) {
            name = friendInfo.getNickname();
        } else if (!TextUtils.isEmpty(friendInfo.getUsername())) {
            name = friendInfo.getNickname();
        }
        //名字
        mTvName.setText(name);
        //头像
        TextDrawable drawable = MyTextDrawable.getTextDrawable(name);
        Glide.with(this)
                .load(friendInfo.getUserPic())
                .placeholder(drawable)
                .into(mIvAvatar);
        //性别符号
        if (friendInfo.getGender() == 0) {
            mIvGender.setImageResource(R.mipmap.icon_girl);
        } else if (friendInfo.getGender() == 1) {
            mIvGender.setImageResource(R.mipmap.icon_boy);
        }
        //年龄
        mTvAge.setText(friendInfo.getAge() + "岁");
        //家乡
        mTvHometown.setText(friendInfo.getHome());
        //现居地
        mTvPresentAddress.setText(friendInfo.getLocation());
        //学历
        mTvEducation.setText(friendInfo.getEducation());
        //自我描述
        mTvMiaoShu.setText(friendInfo.getUserSign());
        //个性标签
        List<String> listTag = friendInfo.getTag();
        if (listTag != null && listTag.size() > 0) {
            for (int i = 0; i < listTag.size(); i++) {
                TextView view = new TextView(this);
                view.setText(listTag.get(i));
                view.setTextColor(Color.WHITE);
                view.setBackgroundResource(R.drawable.button_bg);
                mFlowlBiaoQian.addView(view);
            }
        }
    }

    @Override
    public void getFriendInfoError() {
        ToastUtil.show(this, "获取好友信息失败");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_FRIEND_OPERATE) {
            if (data != null) {
                friendStatus = data.getIntExtra(JMessageUtils.TARGET_FRIENDSTATUS, 0);
            }
        }
    }
}
