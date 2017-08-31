package com.ygst.cenggeche.ui.activity.newfriendlist;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.ApplyBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewFriendListActivity extends MVPBaseActivity<NewFriendListContract.View, NewFriendListPresenter> implements NewFriendListContract.View {
    private String TAG = "NewFriendListActivity";
    List<ApplyBean.DataBean> mListDataBean;
    ApplyBean applyBean;

    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
    private SwipeMenuListView mListView;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            // 创建delete侧滑按钮
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(dp2px(90));
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setText("新的朋友");
        AppData.savaUnReadApplyCount(0);
        if(applyBean == null){
            mPresenter.getApplyList(AppData.getUserName());
        }else{
            mListDataBean = applyBean.getData();
            setListView(mListDataBean);
        }

        mListView = (SwipeMenuListView)findViewById(R.id.lv_new_friend);
        // set creator
        mListView.setMenuCreator(creator);
        // 滑动某一个Item
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
                LogUtils.i(TAG, "swipe start");
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                LogUtils.i(TAG, "swipe start");
            }
        });
        //点击侧滑出来的菜单按钮
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplyBean.DataBean dataBeanItem = mListDataBean.get(position);
                switch (index) {
                    case 0:
                        // delete删除某个会话
                        mPresenter.deleteDate(dataBeanItem, position);
                        break;
                }
            }
        });
        //单点某一个Item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplyBean.DataBean dataBeanItem = mListDataBean.get(position);
                final Intent intent = new Intent();
            }
        });
    }


    /**
     * 设置显示所有申请信息
     * @param listDataBean
     */
    private void setListView(List<ApplyBean.DataBean> listDataBean){
        mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(this, listDataBean);
        if (listDataBean != null) {
            mListView.setAdapter(mSwipeMenuListViewAdapter);
        }
        if(mSwipeMenuListViewAdapter!=null){
            mSwipeMenuListViewAdapter.notifyDataSetChanged();
        }
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void getApplyListSuccess(ApplyBean applyBean) {
        ToastUtil.show(this,"成功了");
        setListView(applyBean.getData());
    }

    @Override
    public void getApplyListError() {
        ToastUtil.show(this,"未获取到信息");
    }
}
