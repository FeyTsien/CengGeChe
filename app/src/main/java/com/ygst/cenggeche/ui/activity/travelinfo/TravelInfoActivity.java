package com.ygst.cenggeche.ui.activity.travelinfo;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.NowTravelInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

import static com.ygst.cenggeche.R.id.iv_sendmessagecc;
import static com.ygst.cenggeche.R.id.tv_wait_cengchecc;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TravelInfoActivity extends MVPBaseActivity<TravelInfoContract.View, TravelInfoPresenter> implements TravelInfoContract.View {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_note_date)
    TextView tvNoteDate;
    @BindView(R.id.tv_start_location)
    TextView tvStartLocation;
    @BindView(R.id.tv_end_location)
    TextView tvEndLocation;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.user_smallicon)
    ImageView userSmallicon;
    @BindView(R.id.iv_gender)
    ImageView ivGender;
    @BindView(R.id.iv_sendmessage)
    ImageView iv_Sendmessage;

    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_userage)
    TextView tvUserage;
    @BindView(R.id.tv_usercartype)
    TextView tvUsercartype;
    @BindView(R.id.ll_t)
    LinearLayout llT;
    @BindView(R.id.tv_wait_cengche)// 确认上车
            TextView tvWaitCengche;
    @BindView(R.id.tv_sure_goal) //确认到达
            TextView tvSureGoal;

    @BindView(R.id.ll_ccpeople)
    LinearLayout llCcpeople;
    @BindView(R.id.ll_canceltravel)
    LinearLayout ll_canceltravel;

    @BindView(R.id.ll_ischezhu)
    LinearLayout ll_ischezhu;
    @BindView(R.id.tv_wait_go)
    TextView tv_wait_go;
    @BindView(R.id.tv_travelover)//乘客结束行程 按钮
            TextView tv_travelover;


    @BindView(R.id.lv_shaoren)
    ListView lvShaoren;
    //车主接受同行
    @BindView(R.id.user_smalliconcc)
    ImageView userSmalliconcc;
    @BindView(R.id.iv_gendercc)
    ImageView ivGendercc;
    @BindView(R.id.tv_nicknamecc)
    TextView tvNicknamecc;
    @BindView(R.id.tv_useragecc)
    TextView tvUseragecc;
    @BindView(iv_sendmessagecc)
    ImageView ivSendmessagecc;
    @BindView(tv_wait_cengchecc)//乘客确认上车按钮
            TextView tvWaitCengchecc;
    @BindView(R.id.ll_shaorenaccept)
    LinearLayout llShaorenaccept;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;


    private NowTravelInfoBean.DataBean data;

    private String TAG = this.getClass().getSimpleName();
    private List<NowTravelInfoBean.InfoBean> infoList;
    private NowTravelInfoBean nowTravelInfoBean;
    private TravelInfoAdapter travelInfoAdapter;
    private int ignoreposition = -1;
    private List<NowTravelInfoBean.InfoBean> infoList1;
    private final String URL_TRIP_RECORDER = UrlUtils.URL_H5 + "/cenggeche/pages/record/record.html";
    private boolean isFriend;
    private String targetAppKey;
    private String targetUsername;



    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_travelinfo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvTitle.setText("当前信息");
        mPresenter.gettravelinfo();

        getmessage();

    }

    public void getmessage(){
        Intent intent = getIntent();
        String strokeStatus = intent.getStringExtra("strokeStatus");
        Log.i("TAGS",strokeStatus+"==");
//        if(strokeStatus!=null) {
//            if (strokeStatus.equals("30")) {//表示乘客点击了确认上车 当用户为车主身份时会收到此消息
//                ll_ischezhu.setVisibility(View.GONE);
//                llShaorenaccept.setVisibility(View.VISIBLE);
//                llCcpeople.setVisibility(View.GONE);
//            } else if (strokeStatus.equals("35")) {//表示车主确认乘客上车	当用户为乘客身份时会收到此消息
//                llCcpeople.setVisibility(View.VISIBLE);
//                tvWaitCengche.setVisibility(View.GONE);
//                tvSureGoal.setVisibility(View.VISIBLE);
//                tv_wait_go.setVisibility(View.GONE);
//
//            } else if (strokeStatus.equals("40")) {//表示乘客点击了确认到达	当用户为车主身份时会收到此消息
//                mPresenter.gettravelinfo();
//
//            } else if (strokeStatus.equals("45")) {//表示车主结束了行程	当用户为乘客身份时会收到此消息
//                mPresenter.gettravelinfo();
//            }
//        }
    }

    private void initData() {

        //有行程信息的时候
        ll_canceltravel.setVisibility(View.VISIBLE);
        tvNoteDate.setText(data.getPostedTime());
        tvStartLocation.setText(data.getStartAddr());
        tvEndLocation.setText(data.getEndAddr());
        tvReleaseDate.setText(data.getDeparTime());
        tvRemarks.setText(data.getComments());
        //有车主或者乘客接受的时候
        if (nowTravelInfoBean.getInfo().size() != 0) {
            if (data.getUserFlag() == 2) {
                ll_ischezhu.setVisibility(View.VISIBLE);
//                llCcpeople.setVisibility(View.GONE);
            } else if (data.getUserFlag() == 1) {
                ll_ischezhu.setVisibility(View.GONE);
                llCcpeople.setVisibility(View.VISIBLE);
            }
            infoList = nowTravelInfoBean.getInfo();
            Glide.with(this).load(infoList.get(0).getBackgroundPic()).into(userSmallicon);
            tvNickname.setText(infoList.get(0).getNickname());
            tvUserage.setText(infoList.get(0).getDeparTime());
            tvUsercartype.setText(infoList.get(0).getBrand() + "-" + infoList.get(0).getColor());
        }else{
//            llCcpeople.setVisibility(View.GONE);
//            ll_ischezhu.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.iv_delete, R.id.iv_sendmessage, R.id.tv_wait_cengche, R.id.tv_sure_goal, tv_wait_cengchecc, R.id.tv_travelover,R.id.iv_sendmessagecc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //乘客删除行程
            case R.id.iv_delete:
                cancelTravelInfo();
                break;

            //确认上车
            case R.id.tv_wait_cengche:
                mPresenter.changeInfo("1", nowTravelInfoBean.getInfo().get(0).getId() + "", data.getId() + "", "30");
                break;
            //乘客确认到达
            case R.id.tv_sure_goal:
                mPresenter.changeInfoArrive("1", nowTravelInfoBean.getInfo().get(0).getId() + "", data.getId() + "", "40");
                break;
            //车主接受 确认乘客上车
            case tv_wait_cengchecc:
                mPresenter.changeInfoCarerSureUpCar("2", data.getId() + "", nowTravelInfoBean.getInfo().get(0).getId() + "", "35");
                break;
            //车主接受 确认乘客上车 车主确认到达
            case R.id.tv_travelover:
                mPresenter.changeInfoCarerSureOver("2",data.getId() + "", nowTravelInfoBean.getInfo().get(0).getId() + "", "45");
                break;

            //发送消息
            case R.id.iv_sendmessage:
            case R.id.iv_sendmessagecc:

                Intent intent = new Intent();
                intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
                intent.putExtra(JMessageUtils.TARGET_APP_KEY, targetAppKey);
                intent.putExtra(JMessageUtils.IS_FRIEND, isFriend);
                intent.setClass(this, MyChatActivity.class);
                startActivity(intent);
                break;

        }
    }
    //车主取消行程
    private void cancelTravelInfo() {
        CommonUtils.showInfoDialog(TravelInfoActivity.this, "一天只能取消三次行程,确定要取消行程吗？", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ll_canceltravel.setVisibility(View.GONE);
                ll_empty.setVisibility(View.VISIBLE);
                //当前为乘客的状态
                LogUtils.i(TAG,nowTravelInfoBean.getData().getUserFlag()+"===="+nowTravelInfoBean.getInfo().size());
                if(nowTravelInfoBean.getData().getUserFlag()==1){
                    if(nowTravelInfoBean.getInfo().size()==0){
                        mPresenter.changeInfo("1",  "", data.getId() + "", "55");
                    }else{
                        mPresenter.changeInfo("1",nowTravelInfoBean.getInfo().get(0).getId() +"", data.getId() + "", "55");
                    }
                    //当前为车主的状态
                }else if(nowTravelInfoBean.getData().getUserFlag()==2){
                    if(nowTravelInfoBean.getInfo().size()==0) {
                        mPresenter.changeInfoCarerCancel("2",  data.getId() + "","", "50");
                    }else{
                        mPresenter.changeInfoCarerCancel("2", data.getId() + "",nowTravelInfoBean.getInfo().get(0).getId() + "", "50");
                    }
                }

            }
        }, null);
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void gettravelinfosuccess(final NowTravelInfoBean nowTravelInfoBean) {
        NowTravelInfoBean.DataBean data = nowTravelInfoBean.getData();

        this.data = data;
        this.nowTravelInfoBean = nowTravelInfoBean;
        if(data.getUid()!=0){
            initData();
        }else{
            ll_empty.setVisibility(View.VISIBLE);
        }

        //判断集合数据得到用户名
        if(nowTravelInfoBean.getInfo().size()!=0){
            targetUsername=nowTravelInfoBean.getInfo().get(0).getUserName();
            tvUseragecc.setText(infoList.get(0).getPostedTime());

            targetUsername= new String(Base64.decode(targetUsername.getBytes(), Base64.DEFAULT));

            JMessageClient.getUserInfo(targetUsername, new GetUserInfoCallback() {
                @Override
                public void gotResult(int responseCode, String s, UserInfo userInfo) {
                    if (responseCode == 0) {
                        targetAppKey = userInfo.getAppKey();
                        isFriend = userInfo.isFriend();
                    }
                }
            });
        }

        //当前为乘客行程
        LogUtils.i(TAG,"stauerokest"+data.getStrokeStatus()+"===data.getUserFlag()"+data.getUserFlag());
        if (data.getUserFlag() == 1) {
            tv_wait_go.setText("等待请求同行");
            //车主信息隐藏
            ll_ischezhu.setVisibility(View.GONE);
            llShaorenaccept.setVisibility(View.GONE);
            //没有任何行程
            if (data.getStrokeStatus() == 10) {
                llCcpeople.setVisibility(View.GONE);

            } else if (data.getStrokeStatus() == 20) {
                llCcpeople.setVisibility(View.VISIBLE);
                tvWaitCengche.setVisibility(View.VISIBLE);
                tvSureGoal.setVisibility(View.GONE);
                tv_wait_go.setVisibility(View.GONE);
            } else if (data.getStrokeStatus() == 30) {
                llCcpeople.setVisibility(View.VISIBLE);
                tvWaitCengche.setVisibility(View.GONE);
                tvSureGoal.setVisibility(View.VISIBLE);
                tv_wait_go.setVisibility(View.GONE);

            }
            //当前为车主行程
        } else if (data.getUserFlag() == 2) {
            tv_wait_go.setVisibility(View.GONE);
            llCcpeople.setVisibility(View.GONE);
            //有行程,没有接受
            if (data.getStrokeStatus() == 10) {
                ll_ischezhu.setVisibility(View.VISIBLE);
                infoList1 = nowTravelInfoBean.getInfo();
                travelInfoAdapter = new TravelInfoAdapter(TravelInfoActivity.this, infoList1);
                lvShaoren.setAdapter(travelInfoAdapter);
                llShaorenaccept.setVisibility(View.GONE);
                //捎人点击事件
                travelInfoAdapter.setmOnItemClickListener(new TravelInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {

                    }

                    @Override
                    public void onItemDeleteClick(View view, final int postion) {
                        //
                        ignoreposition = postion;
                        CommonUtils.showInfoDialog(TravelInfoActivity.this, "确认要忽略行程吗？", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.changeInfoIgnoreCancel("2", nowTravelInfoBean.getData().getId() + "", nowTravelInfoBean.getInfo().get(postion).getId() + "", "25");
                            }
                        }, null);
                    }

                    @Override
                    public void onItemAcceptClick(View view, final int position) {
                        CommonUtils.showInfoDialog(TravelInfoActivity.this, "确认要接受行程吗？", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.changeInfoCarerAgree("2", nowTravelInfoBean.getData().getId() + "", nowTravelInfoBean.getInfo().get(position).getId() + "", "20");
                            }
                        }, null);


                    }
                });

                //有行程,乘客同意
            } else if (data.getStrokeStatus() == 20) {
                ll_ischezhu.setVisibility(View.GONE);
                llShaorenaccept.setVisibility(View.VISIBLE);
                llCcpeople.setVisibility(View.GONE);
                Glide.with(this).load(infoList.get(0).getUserPic()).into(userSmalliconcc);
                tvNicknamecc.setText(infoList.get(0).getNickname());
                tvUserage.setText(infoList.get(0).getPostedTime());
            } else if (data.getStrokeStatus() == 30) {
                ll_ischezhu.setVisibility(View.GONE);
                llShaorenaccept.setVisibility(View.VISIBLE);
                Glide.with(this).load(infoList.get(0).getUserPic()).into(userSmalliconcc);
                tvNicknamecc.setText(infoList.get(0).getNickname());
                tvUserage.setText(infoList.get(0).getPostedTime());
                tv_travelover.setVisibility(View.VISIBLE);
                tvWaitCengchecc.setVisibility(View.GONE);

            }
        }

    }





    @Override
    public void gettravelfail(String msg) {

    }

    //确认上车
    @Override
    public void changeInfoSuccess() {
        tvSureGoal.setClickable(true);
        tvSureGoal.setVisibility(View.VISIBLE);
        tvWaitCengche.setVisibility(View.GONE);
    }

    @Override
    public void changeInfoFail() {
        tvSureGoal.setClickable(false);
    }

    //乘客确认到达
    @Override
    public void changeInfoArriveSuccess() {
        //乘客到达了
        ll_empty.setVisibility(View.VISIBLE);
        ll_canceltravel.setVisibility(View.GONE);

    }

    @Override
    public void changeInfoArriveFail() {

    }


    //车主取消行程
    @Override
    public void changeInfoCarerCancelSuccess() {
        llCcpeople.setVisibility(View.GONE);
        ll_canceltravel.setVisibility(View.GONE);

    }

    @Override
    public void changeInfoCarerCancelFail() {

    }


    //车主忽略行程
    @Override
    public void changeInfoCarerIgnoreSuccess() {
//        ignoreposition
        infoList1.remove(ignoreposition);
        travelInfoAdapter.notifyDataSetChanged();

    }

    @Override
    public void changeInfoCarerIgnoreFail() {

    }

    //车主接受成功与否
    @Override
    public void changeInfoCarerAgreeSuccess() {
        ll_ischezhu.setVisibility(View.GONE);
        llShaorenaccept.setVisibility(View.VISIBLE);

        Glide.with(this).load(infoList.get(0).getUserPic()).into(userSmalliconcc);
        tvNicknamecc.setText(infoList.get(0).getNickname());
        tvUserage.setText(infoList.get(0).getDeparTime());


    }

    @Override
    public void changeInfoCarerAgreeFail() {

    }

    //车主接受成功与否 确认行程结束
    @Override
    public void changeInfoCarerSureUpCarSuccess() {
        tv_travelover.setVisibility(View.VISIBLE);
        tvWaitCengchecc.setVisibility(View.GONE);
    }

    @Override
    public void changeInfoCarerSureUpCarFail() {

    }

    //车主确认到达
    @Override
    public void changeInfoCarerSureOverSuccess() {
        ll_empty.setVisibility(View.VISIBLE);
        ll_canceltravel.setVisibility(View.GONE);

    }

    @Override
    public void changeInfoCarerSureOverFail() {

    }



}
