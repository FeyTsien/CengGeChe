package com.ygst.cenggeche.ui.activity.guidepage;


import android.view.View;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.CommonUtils;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class GuidePageActivity extends MVPBaseActivity<GuidePageContract.View, GuidePagePresenter> implements GuidePageContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guidepage;
    }

    public void guide1(View view){
        CommonUtils.startActivity(this,GradientBackgroundExampleActivity.class);
    }
    public void guide2(View view){
        CommonUtils.startActivity(this,ImageBackgroundExampleActivity.class);
    }
    public void guide3(View view){
        CommonUtils.startActivity(this,SolidBackgroundExampleActivity.class);
    }

    public void guide4(View view){
        CommonUtils.startActivity(this,WelcomeGuideActivity.class);
    }
}
