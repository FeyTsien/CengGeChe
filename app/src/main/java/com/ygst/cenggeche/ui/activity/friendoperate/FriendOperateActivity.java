package com.ygst.cenggeche.ui.activity.friendoperate;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendOperateActivity extends MVPBaseActivity<FriendOperateContract.View, FriendOperatePresenter> implements FriendOperateContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_operate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvTitle.setText("好友设置");
    }
}
