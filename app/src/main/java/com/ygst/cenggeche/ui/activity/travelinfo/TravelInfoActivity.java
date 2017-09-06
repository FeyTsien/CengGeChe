package com.ygst.cenggeche.ui.activity.travelinfo;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.android.uikit.chatting.CircleImageView;


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
    @BindView(R.id.tv_remarks)
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
    @BindView(R.id.lv_shaoren)
    ListView lvShaoren;

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


//

    }

    @OnClick({R.id.iv_delete,R.id.iv_sendmessage,R.id.tv_wait_cengche})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                //
                ToastUtil.show(TravelInfoActivity.this,"ssssssss");
                break;
            //

            //发送消息
            case R.id.iv_sendmessage:
                ToastUtil.show(TravelInfoActivity.this,"message ssssssss");

                break;
            //确认上车
            case R.id.tv_wait_cengche:
                ToastUtil.show(TravelInfoActivity.this,"up");

                break;


        }
    }

    @Override
    public void gettravelinfosuccess() {

    }

    @Override
    public void gettravelfail(String msg) {

    }
}
