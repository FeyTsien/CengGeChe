package com.ygst.cenggeche.ui.activity.travelinfo;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NowTravelInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.choosepic.ChoosePicActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.android.uikit.chatting.CircleImageView;

import static com.ygst.cenggeche.R.id.tv_remarks;


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
    @BindView(R.id.tv_cengorshao)
    TextView tvCengorshao;
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
    @BindView(tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.user_smallicon)
    CircleImageView userSmallicon;
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
    @BindView(R.id.tv_wait_cengche)
    TextView tvWaitCengche;
    @BindView(R.id.ll_ccpeople)
    LinearLayout llCcpeople;
    @BindView(R.id.ll_canceltravel)
    LinearLayout ll_canceltravel;
    @BindView(R.id. ll_empty)
    LinearLayout  ll_empty;
    @BindView(R.id.ll_ischezhu)
    LinearLayout  ll_ischezhu;
    @BindView(R.id.tv_wait_go)
    TextView tv_wait_go;






    @BindView(R.id.lv_shaoren)
    ListView lvShaoren;
    private NowTravelInfoBean.DataBean data;

    private String TAG=this.getClass().getSimpleName();
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
        mTvTitle.setText("行程信息");
        initData();
        ArrayList<String> str=new ArrayList<>();
        str.add("a");
        str.add("a");
        mPresenter.gettravelinfo();
        TravelInfoAdapter travelInfoAdapter = new TravelInfoAdapter(this, str);
        lvShaoren.setAdapter(travelInfoAdapter);
        travelInfoAdapter.setmOnItemClickListener(new TravelInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }

            @Override
            public void onItemDeleteClick(View view, int postion) {
                //
                ToastUtil.show(TravelInfoActivity.this,"删除");

            }

            @Override
            public void onItemAcceptClick(View view, int position) {
                ToastUtil.show(TravelInfoActivity.this,"删除"+position);

            }
        });

    }

    private void initData() {

    }

    @OnClick({R.id.iv_delete,R.id.iv_sendmessage,R.id.tv_wait_cengche})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                CommonUtils.showInfoDialog(TravelInfoActivity.this, "确定要删除行程吗？", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.changeInfo(data.getUserFlag()+"",data.getId()+"",data.getUid()+"","50");
                        ll_canceltravel.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);

                    }
                }, null);

                break;
            //

            //发送消息
            case R.id.iv_sendmessage:
                ToastUtil.show(TravelInfoActivity.this,"message ssssssss");

                break;
            //确认上车
            case R.id.tv_wait_cengche:
                String status = tvWaitCengche.getText().toString();
                if(status.equals("确认上车")){
                    mPresenter.changeInfo(data.getUserFlag()+"",data.getId()+"",data.getUid()+"","30");
                }else if(status.equals("确认到达")){
                    mPresenter.changeInfo(data.getUserFlag()+"",data.getId()+"",data.getUid()+"","40");
                }


                break;


        }
    }

    @Override
    public void gettravelinfosuccess(NowTravelInfoBean nowTravelInfoBean) {
        NowTravelInfoBean.DataBean data = nowTravelInfoBean.getData();

        this.data=data;
        LogUtils.i(TAG,data.getUserFlag()+"返回的信息：");
        if(data.getUserFlag()!=0){
            ll_canceltravel.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
            tvNoteDate.setText(data.getDeparTime());
            tvStartLocation.setText(data.getStartAddr());
            tvEndLocation.setText(data.getEndAddr());
            tvReleaseDate.setText(data.getPostedTime());
            tvRemarks.setText(data.getComments());

        }else{
             ll_empty.setVisibility(View.VISIBLE);
            ll_canceltravel.setVisibility(View.GONE);
        }

        List<NowTravelInfoBean.InfoBean> infoList = nowTravelInfoBean.getInfo();
        if(infoList.size()==0){
            ll_ischezhu.setVisibility(View.GONE);
            llCcpeople.setVisibility(View.GONE);
        }else{

        }

    }

    @Override
    public void gettravelfail(String msg) {

    }

    @Override
    public void changeInfoSuccess() {
        tvWaitCengche.setText("确认到达");

    }

    @Override
    public void changeInfoFail() {

    }
}
