package com.ygst.cenggeche.ui.activity.nearbyuserinfo;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;

import butterknife.ButterKnife;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NearByUserInfoActivity extends MVPBaseActivity<NearByUserInfoContract.View, NearByUserInfoPresenter> implements NearByUserInfoContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearbyuserinfo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }
}
