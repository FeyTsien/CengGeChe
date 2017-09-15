package com.ygst.cenggeche.ui.activity.friendoperate.report;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.UrlUtils;
import com.ygst.cenggeche.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ReportTypeActivity extends MVPBaseActivity<ReportContract.View, ReportPresenter> implements ReportContract.View {

    //使用指南
    private final String REPORT_URL = UrlUtils.URL_H5 + "/cenggeche/pages/report/report.html";
    private int reportType = 0;
    private String targetUserName;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_type;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("举报类型");
        targetUserName = getIntent().getStringExtra(JMessageUtils.TARGET_USERNAME);
    }

    /**
     * 广告骚扰
     */
    @OnClick(R.id.tv_harass)
    public void harass() {
        reportType = 1;
        goReport();
    }

    /**
     * 色情低俗
     */
    @OnClick(R.id.tv_pornographic)
    public void pornographic() {
        reportType = 2;
        goReport();
    }

    /**
     * 政治敏感
     */
    @OnClick(R.id.tv_politics)
    public void politics() {
        reportType = 3;
        goReport();
    }

    /**
     * 欺诈骗钱
     */
    @OnClick(R.id.tv_fraud)
    public void fraud() {
        reportType = 4;
        goReport();
    }

    /**
     * 违法
     */
    @OnClick(R.id.tv_illegal)
    public void illegal() {
        reportType = 5;
        goReport();
    }

    private void goReport() {
        String url = REPORT_URL + "?deviceId=" + AppData.getAndroidId() + "&os=" + "android"
                + "&uid=" + AppData.getUid()
                + "&reportType=" + reportType
                + "&myusername=" + AppData.getUserName()
                + "&targetUsername=" + targetUserName;
        WebViewActivity.loadUrl(this, url, "");
    }
}
