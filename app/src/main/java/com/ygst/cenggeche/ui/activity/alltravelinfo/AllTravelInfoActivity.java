package com.ygst.cenggeche.ui.activity.alltravelinfo;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.CheckPeerBean;
import com.ygst.cenggeche.bean.UserDetailsInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendinfo.RVAdapter;
import com.ygst.cenggeche.ui.activity.friendoperate.FriendOperateActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.activity.peerrequest.PeerRequestActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.ReleaseplanActivity;
import com.ygst.cenggeche.ui.activity.suretravel.SureTravelActivity;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ygst.cenggeche.R.id.btn_together_go;
import static com.ygst.cenggeche.R.id.start;
import static com.ygst.cenggeche.R.id.up;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AllTravelInfoActivity extends MVPBaseActivity<AllTravelInfoContract.View, AllTravelInfoPresenter> implements AllTravelInfoContract.View {
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(btn_together_go)
    Button mBtnSendMsg;
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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_start_location)
    TextView tvStartLocation;
    @BindView(R.id.tv_end_location)
    TextView tvEndLocation;
    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.rl_target)
    LinearLayout rlTarget;
    private UserDetailsInfoBean userDetailsInfoBean;
    private String request;
    private String uid;
    private String id;
    private CheckPeerBean checkPeerBean;
    private String sid;
    private final String URL_OWNER_AUTH= UrlUtils.URL_H5+"/cenggeche/pages/carrz/carrz.html";

    @OnClick(R.id.btn_together_go)
    public void sendmessage() {
        int userStatus = AppData.getUserStatus();
        if(userStatus!=1){

            CommonUtils.showInfoDialog(this, "您还没有车主认证？", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String  url3 = URL_OWNER_AUTH+"?deviceId="+AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();

                    WebViewActivity.loadUrl(AllTravelInfoActivity.this,url3,"");
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   finish();
                }
            });
        }else{
            mPresenter.checkApplyPeerInfo(userDetailsInfoBean.getData().getUserFlag()+"",userDetailsInfoBean.getData().getId()+"");
        }
    }


    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_alltravelinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mTvTitle.setText("详细资料");
        Intent intent = getIntent();
        sid = intent.getStringExtra("SID");
        String EndAddr = intent.getStringExtra("EndAddr");
        String StartAddr = intent.getStringExtra("StartAddr");
        String PostedTime = intent.getStringExtra("PostedTime");
        request = intent.getStringExtra("REQUEST");
        if(request.equals("2")){
            id = intent.getStringExtra("id");
            uid = intent.getStringExtra("uid");
        }

        tvReleaseDate.setText(PostedTime);
        tvStartLocation.setText(StartAddr);
        tvEndLocation.setText(EndAddr);
        mPresenter.getUserInfo(sid);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    //用户信息
    @Override
    public void getUserInfoSuccess(UserDetailsInfoBean userDetailsInfoBean) {
        this.userDetailsInfoBean =userDetailsInfoBean;
        setFriendInfo(userDetailsInfoBean);
    }

    @Override
    public void getUserInfoFail() {

    }

    //检测是否可以申请
    @Override
    public void getcheckInfoSuccess(CheckPeerBean checkPeerBean) {
        this.checkPeerBean =checkPeerBean;
        Intent intent = new Intent(this, SureTravelActivity.class);
        String genders = AppData.getGenders();

        //得到点击该用户的状态 和使用者相反
        int userFlag1 = userDetailsInfoBean.getData().getUserFlag();
        LogUtils.i(TAG,userFlag1+"==userFlag1");
        //乘客蹭车 判断男女
        if(userFlag1==1){
            intent.putExtra("CARTYPE", 2+"");
        }
        //车主状态
        if(userFlag1==2){
            if(Integer.parseInt(genders)==1){
                ToastUtil.show(AllTravelInfoActivity.this,"男性用户不能蹭车");
                return ;
            }
            intent.putExtra("CARTYPE", 1+"");

        }

        //判断是否自己有行程
        if(checkPeerBean.getData().getUid()!=0){
            //当前用户为乘客状态
            intent.putExtra("REQUEST", 2 + "");
            intent.putExtra("endAddr", checkPeerBean.getData().getEndAddr());
            intent.putExtra("startAddr", checkPeerBean.getData().getStartAddr());
            intent.putExtra("postedTime", checkPeerBean.getData().getDeparTime());
            intent.putExtra("strokeFlag", "2");
            intent.putExtra("comment", checkPeerBean.getData().getComments());
            intent.putExtra("id", checkPeerBean.getData().getId() + "");
            intent.putExtra("sid", sid + "");
            intent.putExtra("uid", uid + "");

        }else{
            intent.putExtra("strokeFlag", "3");
            intent.putExtra("REQUEST", 2 + "");
            intent.putExtra("endAddr", userDetailsInfoBean.getData().getEndAddr());
            intent.putExtra("startAddr", userDetailsInfoBean.getData().getStartAddr());
            intent.putExtra("postedTime", userDetailsInfoBean.getData().getDeparTime());
            intent.putExtra("comment", userDetailsInfoBean.getData().getComments());
            intent.putExtra("id", "");
            intent.putExtra("uid", uid + "");
            intent.putExtra("sid", sid + "");

        }

        startActivity(intent);
            finish();


    }

    @Override
    public void getcheckInfoFail(String msg) {

    }




    private void setFriendInfo(UserDetailsInfoBean friendInfo) {

        mRecyclerView.setAdapter(new RVAdapter(this, friendInfo.getPic()));
        if (!TextUtils.isEmpty(friendInfo.getUser().getNickname())) {
            mTvName.setText(friendInfo.getUser().getNickname());
        } else if (!TextUtils.isEmpty(friendInfo.getUser().getUsername())) {
            mTvName.setText(friendInfo.getData().getNickname());
        }
        //名字
        //头像
        Glide.with(this)
                .load(friendInfo.getUser().getUserPic())
                .centerCrop()
                .into(mIvAvatar);
        //性别符号
        if (friendInfo.getUser().getGender() == 0) {
            mIvGender.setImageResource(R.mipmap.icon_girl);
        } else if (friendInfo.getUser().getGender() == 1) {
            mIvGender.setImageResource(R.mipmap.icon_boy);
        }
        //年龄
        mTvAge.setText(friendInfo.getUser().getAge() + "岁");
        //家乡
        mTvHometown.setText(friendInfo.getUser().getHome());
        //现居地
        mTvPresentAddress.setText(friendInfo.getUser().getLocation());
        //学历
        setEducation(friendInfo.getUser().getEducation());
        //自我描述
        mTvMiaoShu.setText(friendInfo.getUser().getUserSign());
        //个性标签
        List<String> listTag = friendInfo.getUser().getTag();
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


}
