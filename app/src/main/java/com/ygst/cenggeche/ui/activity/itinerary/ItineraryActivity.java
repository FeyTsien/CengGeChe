package com.ygst.cenggeche.ui.activity.itinerary;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ItineraryActivity extends MVPBaseActivity<ItineraryContract.View, ItineraryPresenter> implements ItineraryContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_itinerary;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }
}
