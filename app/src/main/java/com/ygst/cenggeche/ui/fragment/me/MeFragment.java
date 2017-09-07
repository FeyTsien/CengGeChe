package com.ygst.cenggeche.ui.fragment.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.MyInfoBean;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.utils.ToastUtil;

import cn.jpush.im.android.api.JMessageClient;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    private View mRootView;
    Button mBtnLoginOut;
    private CircleImageView mCivAvatar;
    private TextView mTvMyName;
    private TextView mTvTotalNum;
    private TextView mTvRubNum;
    private TextView mTvPassiveRubNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_me, container, false);
        init();
        return mRootView;
    }

    private void init() {
        //获取个人信息
        mPresenter.getMyInfo();
        mBtnLoginOut = (Button) mRootView.findViewById(R.id.btn_login_out);
        mCivAvatar = (CircleImageView) mRootView.findViewById(R.id.civ_avatar);
        mTvMyName = (TextView) mRootView.findViewById(R.id.tv_myname);
        mTvTotalNum = (TextView) mRootView.findViewById(R.id.tv_total_num);
        mTvRubNum = (TextView) mRootView.findViewById(R.id.tv_rub_num);
        mTvPassiveRubNum = (TextView) mRootView.findViewById(R.id.tv_passive_rub_num);

        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loginOut();
            }
        });
    }


    @Override
    public void getMyInfoSuccess(MyInfoBean myInfoBean) {
        MyInfoBean.DataBean dataBean = myInfoBean.getData();
        if(!TextUtils.isEmpty(dataBean.getNickname())){
            mTvMyName.setText(myInfoBean.getData().getNickname());
        }else{
            mTvMyName.setText(AppData.getUserName());
        }
        mTvTotalNum.setText(dataBean.getTotalNum());
        mTvRubNum.setText(dataBean.getRubNum());
        mTvPassiveRubNum.setText(dataBean.getPassiveRubNum());
    }

    @Override
    public void getMyInfoError() {

    }

    @Override
    public void loginOutSuccess() {
        JMessageClient.logout();
        MyApplication.clearLogin();
        ToastUtil.show(getActivity(), "退出成功");
    }

    @Override
    public void loginOutError() {

    }
}
