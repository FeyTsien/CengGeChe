package com.ygst.cenggeche.ui.fragment.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.utils.ToastUtil;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View {

    private View mRootView;
    Button mBtnLoginOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_me, container, false);
        initView();
        return mRootView;
    }

    private void initView() {

        mBtnLoginOut = (Button) mRootView.findViewById(R.id.btn_login_out);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loginOut();
            }
        });
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
