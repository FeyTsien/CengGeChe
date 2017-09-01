package com.ygst.cenggeche.ui.activity.friendlist;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.FriendBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.recycle.contacts_recycle.SideBar;
import com.ygst.cenggeche.recycle.contacts_recycle.itemAnimator.SlideInOutLeftItemAnimator;
import com.ygst.cenggeche.ui.activity.newfriendlist.NewFriendListActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FriendListActivity extends MVPBaseActivity<FriendListContract.View, FriendListPresenter> implements FriendListContract.View {

    private RecyclerView rl_recycle_view;
    private ContactAdapter mAdapter;
    private CustomItemDecoration decoration;
    private SideBar side_bar;
    FriendBean friendBean;
    List<FriendBean.DataBean> mListData = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_unread_count)
    TextView mTvUnreadCount;
    @BindView(R.id.ll_newfriend)
    RelativeLayout mLlNewFriend;

    @OnClick(R.id.ll_newfriend)
    public void goToNewFriendList() {
        CommonUtils.startActivity(this, NewFriendListActivity.class);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void initViews() {
        mTvTitle.setText("我的蹭友");
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
        if (AppData.getUnReadApplyCount() > 0) {
            mTvUnreadCount.setVisibility(View.VISIBLE);
            mTvUnreadCount.setText("" + AppData.getUnReadApplyCount());
        } else {
            mTvUnreadCount.setVisibility(View.GONE);
        }

        if (friendBean == null) {
            mPresenter.getFriendList(AppData.getUserName());
        } else {
            mListData = friendBean.getData();
            setListView(mListData);
        }
    }

    /**
     * 设置显示所有申请信息
     *
     * @param listDataBean
     */
    private void setListView(List<FriendBean.DataBean> listDataBean) {
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
    public void getFriendListSuccess(FriendBean friendBean) {
        ToastUtil.show(this, "获取好友成功");
        mListData = friendBean.getData();
        setListView(mListData);
    }

    @Override
    public void getFriendListError() {
        ToastUtil.show(this, "获取好友失败");
    }
}
