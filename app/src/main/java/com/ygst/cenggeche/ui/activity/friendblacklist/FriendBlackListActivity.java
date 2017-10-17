package com.ygst.cenggeche.ui.activity.friendblacklist;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.FriendListBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.recycle.contacts_recycle.SideBar;
import com.ygst.cenggeche.recycle.contacts_recycle.itemAnimator.SlideInOutLeftItemAnimator;
import com.ygst.cenggeche.ui.activity.friendinfo.FriendInfoActivity;
import com.ygst.cenggeche.ui.activity.friendlist.CommonUtil;
import com.ygst.cenggeche.ui.activity.friendlist.ContactAdapter;
import com.ygst.cenggeche.ui.activity.friendlist.CustomItemDecoration;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;

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

    private String targetName;
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
        //添加分割线
        rl_recycle_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new ContactAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(JMessageUtils.TARGET_USERNAME, mListData.get(position).getFriendusername());
                intent.setClass(FriendBlackListActivity.this, FriendInfoActivity.class);
                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new ContactAdapter.OnRecyclerItemLongListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                FriendListBean.DataBean bean = mListData.get(position);
                if (!TextUtils.isEmpty(bean.getFriendNote())) {
                    targetName = bean.getFriendNote();
                } else if (!TextUtils.isEmpty(bean.getNickname())) {
                    targetName = bean.getNickname();
                } else {
                    targetName = bean.getFriendusername();
                }
                CommonUtils.showInfoDialog(FriendBlackListActivity.this, "是否要将“"+targetName+"”移出黑名单？", "提示", "移出", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //移除黑名单
                    mPresenter.removeBlackList(AppData.getUserName(), mListData.get(position).getFriendusername(),position);
                }
            }, null);
            }
        });
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
        mListData = friendListBean.getData();
        setListView(mListData);
    }

    @Override
    public void getBlackListError() {
    }

    @Override
    public void removeBlackListSuccess(int position) {
        mPresenter.getBlackList(AppData.getUserName());
    }

    @Override
    public void removeBlackListError() {

    }
}
