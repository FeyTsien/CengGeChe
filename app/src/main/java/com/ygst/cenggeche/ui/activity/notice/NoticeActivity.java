package com.ygst.cenggeche.ui.activity.notice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.notice.NoticeListBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NoticeActivity extends MVPBaseActivity<NoticeContract.View, NoticePresenter> implements NoticeContract.View {

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
        return R.layout.activity_notice;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mTvTitle.setText("活动公告");
        mPresenter.getNoticeList();
    }

    @Override
    public void getNoticeListSuccess(NoticeListBean noticeListBean) {
    }

    @Override
    public void getNoticeListError() {

    }
}
