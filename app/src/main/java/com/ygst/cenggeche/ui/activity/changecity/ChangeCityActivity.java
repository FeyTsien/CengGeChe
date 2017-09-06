package com.ygst.cenggeche.ui.activity.changecity;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChangeCityActivity extends MVPBaseActivity<ChangeCityContract.View, ChangeCityPresenter> implements ChangeCityContract.View {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_changecity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    mPresenter.getcityname();
    }

    @Override
    public void getcitysuccess() {

    }

    @Override
    public void getcityfail() {

    }
}
