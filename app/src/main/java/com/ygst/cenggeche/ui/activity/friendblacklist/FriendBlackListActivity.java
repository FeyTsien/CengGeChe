package com.ygst.cenggeche.ui.activity.friendblacklist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.FriendListBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.recycle.contacts_recycle.SideBar;
import com.ygst.cenggeche.recycle.contacts_recycle.itemAnimator.SlideInOutLeftItemAnimator;
import com.ygst.cenggeche.ui.activity.friendlist.CommonUtil;
import com.ygst.cenggeche.ui.activity.friendlist.ContactAdapter;
import com.ygst.cenggeche.ui.activity.friendlist.CustomItemDecoration;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FriendBlackListActivity extends MVPBaseActivity<FriendBlackListContract.View, FriendBlackListPresenter> implements FriendBlackListContract.View {
    private RecyclerView rl_recycle_view;
    private ContactAdapter mAdapter;
    private CustomItemDecoration decoration;
    private SideBar side_bar;
    FriendListBean friendListBean;
    List<FriendListBean.DataBean> mListData = new ArrayList<>();
    private LinearLayoutManager layoutManager;

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
        return R.layout.activity_black_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    private void initViews(){
        mTvTitle.setText("黑名单");
        mAdapter = new ContactAdapter(this);
        rl_recycle_view = (RecyclerView) findViewById(R.id.rl_recycle_view);
        //侧边导航栏
        side_bar = (SideBar) findViewById(R.id.side_bar);
        layoutManager = new LinearLayoutManager(this);
        rl_recycle_view.setLayoutManager(layoutManager);
        rl_recycle_view.addItemDecoration(decoration = new CustomItemDecoration(this));
        rl_recycle_view.setItemAnimator(new SlideInOutLeftItemAnimator(rl_recycle_view));
    }

    public void initEvents() {
        side_bar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || mListData.size() <= 0) return;
                for (int i = 0; i < mListData.size(); i++) {
                    if (tag.equals(mListData.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        //右上角未读的验证消息数量
        if (friendListBean == null) {
            mPresenter.getBlackList(AppData.getUserName());
        } else {
            mListData = friendListBean.getData();
            setListView(mListData);
        }
    }

    /**
     * 设置显示所有申请信息
     *
     * @param listDataBean
     */
    private void setListView(List<FriendListBean.DataBean> listDataBean) {
        //对数据源进行排序
        CommonUtil.sortData(listDataBean);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(listDataBean);
//        side_bar.setIndexStr(tagsStr);
        decoration.setDatas(listDataBean, tagsStr);
        mAdapter.addAll(listDataBean);
        rl_recycle_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBlackListSuccess(FriendListBean friendListBean) {

        ToastUtil.show(this, "获取好友成功");
        mListData = friendListBean.getData();
        setListView(mListData);
    }

    @Override
    public void getBlackListError() {
        ToastUtil.show(this, "获取好友失败");
    }
}
