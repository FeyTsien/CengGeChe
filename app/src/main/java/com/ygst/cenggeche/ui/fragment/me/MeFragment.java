package com.ygst.cenggeche.ui.fragment.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.ACache;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.MyInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.activity.setting.SettingActivity;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View,View.OnClickListener{

    //我的资料
    private final String URL_MY_INFO= UrlUtils.URL_H5+"/cenggeche/pages/info/info.html";
    //行程记录
    private final String URL_TRIP_RECORDER= UrlUtils.URL_H5+"/cenggeche/pages/record/record.html";
    //车主认证
    private final String URL_OWNER_AUTH= UrlUtils.URL_H5+"/cenggeche/pages/carrz/carrz.html";
    //车主认证状态
    private int userStatus;

    private String TAG = "MeFragment";
    public ACache mCache;
    private View mRootView;
    private CircleImageView mCivAvatar;
    private ImageView mIvGender;
    private TextView mTvMyName;
    private TextView mTvTotalNum;
    private TextView mTvRubNum;
    private TextView mTvPassiveRubNum;
    private TextView mTvMyInfo;
    private TextView mTvOwnerAuth;
    private TextView mTvTripRecorder;
    private TextView mTvSetting;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG,"MeFragment-----onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i(TAG,"MeFragment-----onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_me, container, false);
        init();
        return mRootView;
    }

    private void init() {
//        mCache = ACache.get(getActivity());
//        MyInfoBean myInfoBean = (MyInfoBean) mCache.getAsObject("myInfoBean");
//        if(myInfoBean!=null){
//            getMyInfoSuccess(myInfoBean);
//        }
        mCivAvatar = (CircleImageView) mRootView.findViewById(R.id.civ_avatar);
        mIvGender = (ImageView) mRootView.findViewById(R.id.iv_gender);
        mTvMyName = (TextView) mRootView.findViewById(R.id.tv_myname);
        mTvTotalNum = (TextView) mRootView.findViewById(R.id.tv_total_num);
        mTvRubNum = (TextView) mRootView.findViewById(R.id.tv_rub_num);
        mTvPassiveRubNum = (TextView) mRootView.findViewById(R.id.tv_passive_rub_num);
        mTvMyInfo = (TextView) mRootView.findViewById(R.id.tv_my_info);
        mTvTripRecorder = (TextView) mRootView.findViewById(R.id.tv_trip_recorder);
        mTvOwnerAuth  = (TextView) mRootView.findViewById(R.id.tv_owner_auth);
        mTvSetting = (TextView) mRootView.findViewById(R.id.tv_setting);

        mTvMyInfo.setOnClickListener(this);
        mTvTripRecorder.setOnClickListener(this);
        mTvOwnerAuth.setOnClickListener(this);
        mTvSetting.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        //获取个人信息
        mPresenter.getMyInfo();
        LogUtils.i(TAG,"MeFragment-----OnResume");
        super.onResume();
    }

    /**
     * 获取信息成功
     * @param myInfoBean
     */
    @Override
    public void getMyInfoSuccess(MyInfoBean myInfoBean) {
//        mCache.put("myInfoBean", (Serializable) myInfoBean);

        MyInfoBean.DataBean dataBean = myInfoBean.getData();
        userStatus =dataBean.getUserStatus();
        //头像
        Glide.with(this).load(dataBean.getUserPic()).placeholder(R.mipmap.icon_my_avatar).into(mCivAvatar);
        //性别
        if(dataBean.getGender() ==0){
            mIvGender.setImageResource(R.mipmap.icon_girl);
        }else if(dataBean.getGender() ==1){
            mIvGender.setImageResource(R.mipmap.icon_boy);
        }
        //名字
        if(!TextUtils.isEmpty(dataBean.getNickname())){
            mTvMyName.setText(myInfoBean.getData().getNickname());
        }else{
            mTvMyName.setText(AppData.getUserName());
        }
        //总发布行程数
        mTvTotalNum.setText(dataBean.getTotalNum()+"次");
        //总蹭车数
        mTvRubNum.setText(dataBean.getRubNum()+"次");
        //总被蹭数
        mTvPassiveRubNum.setText(dataBean.getPassiveRubNum()+"次");
    }

    @Override
    public void getMyInfoError() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_my_info:
                String url1 = URL_MY_INFO+"?deviceId="+AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
                WebViewActivity.loadUrl(getActivity(),url1,"");
                break;
            case R.id.tv_trip_recorder:
                String url2 = URL_TRIP_RECORDER+"?deviceId="+AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
                WebViewActivity.loadUrl(getActivity(),url2,"");
                break;
            case R.id.tv_owner_auth:
                String url3 = "";
                if(userStatus ==1){
                    url3 = URL_OWNER_AUTH+"?deviceId="+AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid()+"&rz=1";
                }else{
                    url3 = URL_OWNER_AUTH+"?deviceId="+AppData.getAndroidId()+"&os="+"android"+"&uid="+AppData.getUid();
                }
                WebViewActivity.loadUrl(getActivity(),url3,"");
                break;
            case R.id.tv_setting:
                CommonUtils.startActivity(getActivity(), SettingActivity.class);
                break;
        }
    }
}
