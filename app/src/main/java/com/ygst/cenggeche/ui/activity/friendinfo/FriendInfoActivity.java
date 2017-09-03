package com.ygst.cenggeche.ui.activity.friendinfo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.UserBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendoperate.FriendOperateActivity;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.MyTextDrawable;
import com.ygst.cenggeche.ui.widget.TextDrawable;
import com.ygst.cenggeche.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendInfoActivity extends MVPBaseActivity<FriendInfoContract.View, FriendInfoPresenter> implements FriendInfoContract.View{

    private UserBean.DataBean friendInfo;
    private String targetUsername;

    private GridViewAdapter mGridViewAdapter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.gv_pic)
    GridView mGvPic;
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

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }
    @OnClick(R.id.iv_menu)
    public void friendOperate(){
        Intent intent = new Intent(this, FriendOperateActivity.class);
        intent.putExtra("myusername", AppData.getUserName());
        intent.putExtra("targetUsername", targetUsername);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_info;
    }

    private Integer[] mImageIds = { R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.f,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        mTvTitle.setText("详细资料");
        mIvMenu.setVisibility(View.VISIBLE);
        mGridViewAdapter = new GridViewAdapter(this,mImageIds);
        mGvPic.setAdapter(mGridViewAdapter);
    }

    private void initData(){
        Intent intent = getIntent();
        String friendUsername =intent.getStringExtra("friendsUsername");
        mPresenter.getFriendInfo(friendUsername);
    }

    @Override
    public void getFriendInfoSuccess(UserBean.DataBean dataBean) {
        ToastUtil.show(this,"获取成功");
        setFriendInfo(dataBean);
    }

    private void setFriendInfo(UserBean.DataBean friendInfo){
        targetUsername = friendInfo.getUsername();
        String name = "";
        if(!TextUtils.isEmpty(friendInfo.getNickname())){
            name =friendInfo.getNickname();
        }else if(!TextUtils.isEmpty(friendInfo.getUsername())){
            name =friendInfo.getNickname();
        }
        //名字
        mTvName.setText(name);
        //头像
        if(!TextUtils.isEmpty(friendInfo.getUserPic())){
            Uri uri = Uri.parse(friendInfo.getUserPic());
            mIvAvatar.setImageURI(uri);
        }else {
            TextDrawable drawable = MyTextDrawable.getTextDrawable(name);
            mIvAvatar.setImageDrawable(drawable);
        }
        //性别符号
        if(friendInfo.getGender() == 0){
            mIvGender.setImageResource(R.mipmap.icon_girl);
        }else if(friendInfo.getGender() == 1){
            mIvGender.setImageResource(R.mipmap.icon_boy);
        }
        mTvAge.setText(friendInfo.getAge()+"岁");
    }

    @Override
    public void getFriendInfoError() {

    }
}
