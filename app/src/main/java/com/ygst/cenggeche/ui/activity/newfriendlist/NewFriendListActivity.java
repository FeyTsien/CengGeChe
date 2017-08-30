package com.ygst.cenggeche.ui.activity.newfriendlist;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NewFriendBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewFriendListActivity extends MVPBaseActivity<NewFriendListContract.View, NewFriendListPresenter> implements NewFriendListContract.View {
    private String TAG = "NewFriendListActivity";
    private Activity mContext;
    private View mRootView;
    List<NewFriendBean> mListNewFriendBean;
    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
    private SwipeMenuListView mListView;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

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
//        mListNewFriendBean = ;
        mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(this,mListNewFriendBean);
        if (mListNewFriendBean != null) {
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
}
