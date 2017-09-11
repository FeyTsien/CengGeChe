//package com.ygst.cenggeche.ui.activity.newfriendlist;
//
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.TextView;
//
//import com.blankj.utilcode.utils.LogUtils;
//import com.ygst.cenggeche.R;
//import com.ygst.cenggeche.app.AppData;
//import com.ygst.cenggeche.bean.B2.ApplyBean;
//import com.ygst.cenggeche.mvp.MVPBaseActivity;
//import com.ygst.cenggeche.ui.activity.friendinfo.FriendInfoActivity;
//import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;
//import com.ygst.cenggeche.utils.CommonUtils;
//import com.ygst.cenggeche.utils.JMessageUtils;
//import com.ygst.cenggeche.utils.ToastUtil;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import cn.jpush.im.android.api.ContactManager;
//import cn.jpush.im.api.BasicCallback;
//
//
///**
// * MVPPlugin
// * 邮箱 784787081@qq.com
// */
//
//public class NewFriendListActivity extends MVPBaseActivity<NewFriendListContract.View, NewFriendListPresenter> implements NewFriendListContract.View {
//    private String TAG = "NewFriendListActivity";
//    String fromUserName;
//    String fromAppkey;
//    List<ApplyBean> mListApplyBean;
//
//    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
//    private SwipeMenuListView mListView;
//
//    @BindView(R.id.tv_title)
//    TextView mTvTitle;
//
//    /**
//     * 返回
//     */
//    @OnClick(R.id.iv_back)
//    public void goBack() {
//        finish();
//    }
//
//    SwipeMenuCreator creator = new SwipeMenuCreator() {
//        @Override
//        public void create(SwipeMenu menu) {
//            // 创建delete侧滑按钮
//            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//            // set item background
//            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                    0x3F, 0x25)));
//            // set item width
//            deleteItem.setWidth(dp2px(90));
//            // set a icon
//            deleteItem.setIcon(R.drawable.ic_delete);
//            // add to menu
//            menu.addMenuItem(deleteItem);
//        }
//    };
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_new_friend_list;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        mTvTitle.setText("验证消息");
//        AppData.savaUnReadApplyCount(0);
//
//        mListView = (SwipeMenuListView) findViewById(R.id.lv_new_friend);
//        // set creator
//        mListView.setMenuCreator(creator);
//        // 滑动某一个Item
//        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
//            @Override
//            public void onSwipeStart(int position) {
//                // swipe start
//                LogUtils.i(TAG, "swipe start");
//            }
//
//            @Override
//            public void onSwipeEnd(int position) {
//                // swipe end
//                LogUtils.i(TAG, "swipe start");
//            }
//        });
//        //点击侧滑出来的菜单按钮
//        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplyBean dataBeanItem = mListApplyBean.get(position);
//                switch (index) {
//                    case 0:
//                        // delete删除某个会话
//                        mListApplyBean.remove(position);
//                        mCache.put(JMessageUtils.APPLE_BEAN, (Serializable) mListApplyBean);
//                        if (mSwipeMenuListViewAdapter != null) {
//                            mSwipeMenuListViewAdapter.notifyDataSetChanged();
//                        }
//                        break;
//                }
//            }
//        });
//        //单点某一个Item
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ApplyBean dataBeanItem = mListApplyBean.get(position);
//                Intent intent = new Intent(NewFriendListActivity.this, FriendInfoActivity.class);
//                intent.putExtra(JMessageUtils.TARGET_USERNAME, mListApplyBean.get(position).getFromUsername());
//                startActivity(intent);
//            }
//        });
//
//        mListApplyBean = new ArrayList<>();
//        mListApplyBean = (ArrayList<ApplyBean>) mCache.getAsObject(JMessageUtils.APPLE_BEAN);
//        setListView(mListApplyBean);
//
//    }
//
//    /**
//     * 设置显示所有申请信息
//     *
//     * @param listDataBean
//     */
//    private void setListView(List<ApplyBean> listDataBean) {
//        if (listDataBean != null) {
//            mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(this, listDataBean);
//            mListView.setAdapter(mSwipeMenuListViewAdapter);
//            mSwipeMenuListViewAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private int dp2px(int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                getResources().getDisplayMetrics());
//    }
//
//    /**
//     * 拒绝申请提示框
//     */
//    public void showNoAgreeDialog(final int position) {
//        CommonUtils.showInfoDialog(this, "拒绝该用户的好友申请吗？", "提示", "拒绝", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                final ApplyBean applyBean = mListApplyBean.get(position);
//                ContactManager.declineInvitation(applyBean.getFromUsername(), applyBean.getFromAppkey(), "", new BasicCallback() {
//                    @Override
//                    public void gotResult(int responseCode, String s) {
//                        if (responseCode == 0) {
//                            applyBean.setIsAgree(2);
//                            mListApplyBean.set(position, applyBean);
//                            mCache.put(JMessageUtils.APPLE_BEAN, (Serializable) mListApplyBean);
//                            setListView(mListApplyBean);
//                        } else {
//                            ToastUtil.show(NewFriendListActivity.this, "拒绝好友失败");
//                        }
//                    }
//                });
//            }
//        }, null);
//    }
//    /**
//     * 同意申请提示框
//     */
//    public void showYesAgreeDialog(final int position) {
//        CommonUtils.showInfoDialog(this, "同意该用户的好友申请吗？", "提示", "同意", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                fromUserName = mListApplyBean.get(position).getFromUsername();
//                fromAppkey = mListApplyBean.get(position).getFromAppkey();
//                mPresenter.yesAgree(AppData.getUserName(), fromUserName, position);
//            }
//        }, null);
//    }
//
//    @Override
//    public void yesAgreeSuccess(int position) {
//        ToastUtil.show(this, "同意申请");
//
//        ContactManager.acceptInvitation(fromUserName, fromAppkey, new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String responseMessage) {
//                if (0 == responseCode) {
//                    //接收好友请求成功
//                } else {
//                    //接收好友请求失败
//                }
//            }
//        });
//        //接收好友请求成功
//        mListApplyBean.get(position).setIsAgree(1);
//        mListApplyBean.set(position, mListApplyBean.get(position));
//        mCache.put(JMessageUtils.APPLE_BEAN, (Serializable) mListApplyBean);
//        setListView(mListApplyBean);
//
//        Intent mIntent = new Intent(NewFriendListActivity.this, MyChatActivity.class);
//        mIntent.putExtra(JMessageUtils.TARGET_USERNAME, fromUserName);
//        mIntent.putExtra(JMessageUtils.TARGET_APP_KEY, fromAppkey);
//        mIntent.putExtra(JMessageUtils.IS_FRIEND, true);
//        mIntent.putExtra(JMessageUtils.IS_AGREE_KEY, JMessageUtils.YES_AGREE);
//        startActivity(mIntent);
//    }
//
//    @Override
//    public void yesAgreeError() {
//        ToastUtil.show(this, "同意申请失败了，请重试");
//    }
//
//
//}
