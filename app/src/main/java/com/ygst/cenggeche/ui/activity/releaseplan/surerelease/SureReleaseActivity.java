package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ygst.cenggeche.R.id.et_remark;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SureReleaseActivity extends MVPBaseActivity<SureReleaseContract.View, SureReleasePresenter> implements SureReleaseContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
//    @BindView(R.id.tv_mine_releasetime)
//    TextView mTv_mine_releasetime;
    @BindView(R.id.tv_release_date)
    TextView mTv_release_date;
    TextView mTv_title_right;
    @BindView(R.id.tv_start_location)
    TextView mTv_start_location;
    @BindView(R.id.tv_end_location)
    TextView mTv_end_location;
    @BindView(R.id.tv_cartype)
    TextView mTv_cartype;
    @BindView(R.id.tv_num)
    TextView mTv_num;//字数限制
    @BindView(R.id.btn_nowrelease)
    Button  mBtn_sure;
    @BindView(et_remark)
    EditText mEt_descriable;
    @BindView(R.id.ll_cartype)
    LinearLayout mLl_Cartype;



    private String meEndlocation;
    private String mStartlocation;
    private String mTime;
    private String mType;
    private int stateuser;
    private String mId;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }


    //为乘客的时候点击发布信息
    @OnClick(R.id.btn_nowrelease)
    public void nowrelease(){
        if(stateuser==1){
            mPresenter.getSureRelease(1+"",mStartlocation,meEndlocation,mTime);
        }else if(stateuser==2){
            mPresenter.getSureRelease(2+"",mStartlocation,meEndlocation,mTime);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sure_release_iten;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mTvTitle.setText("发布行程");
        ArrayList<String> list=new ArrayList<>();
        Intent intent = getIntent();
        stateuser = intent.getIntExtra("STATEUSER",1);

        mTime = intent.getStringExtra("TIME");
        meEndlocation = intent.getStringExtra("ENDLOACTION");
        mStartlocation = intent.getStringExtra("STARTLOACTION");
        mId = intent.getStringExtra("ID");

        mTv_release_date.setText(mTime);
        mTv_end_location.setText(meEndlocation);
        mTv_start_location.setText(mStartlocation);
        //捎人
        mTv_start_location.setText(mStartlocation);

        if(stateuser ==1){
            mLl_Cartype.setVisibility(View.GONE);
        }else{
            mLl_Cartype.setVisibility(View.VISIBLE);
            mType = intent.getStringExtra("TYPE");
            mTv_cartype.setText(mType);
        }
        //输入框的监听
        mEt_descriable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                mTv_num.setText(length+"/50");
                if(length==50){
                    ToastUtil.show(SureReleaseActivity.this,"最多输入50个字，不要贪心哟！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void sureReleaseSuccess() {
//        CommonUtils.startActivity(SureReleaseActivity.this, TravelInfoActivity.class);
    }

    @Override
    public void sureReleaseFail(String msg) {

    }
}
