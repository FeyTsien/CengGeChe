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
//import com.ygst.cenggeche.bean.ApplyBean;
//import com.ygst.cenggeche.mvp.MVPBaseActivity;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
//import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;
//import com.ygst.cenggeche.utils.CommonUtils;
//import com.ygst.cenggeche.utils.JMessageUtils;
//import com.ygst.cenggeche.utils.ToastUtil;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//
///**
// * MVPPlugin
// *  邮箱 784787081@qq.com
// */
//
//public class NewFriendListActivityCopy extends MVPBaseActivity<NewFriendListContract.View, NewFriendListPresenter> implements NewFriendListContract.View {
//    private String TAG = "NewFriendListActivity";
//    List<ApplyBean.DataBean> mListDataBean;
//    ApplyBean applyBean;
//
//    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
//    private SwipeMenuListView mListView;
//
//    @BindView(R.id.tv_title)
//    TextView mTvTitle;
//    /**
//     * 返回
//     */
//    @OnClick(R.id.iv_back)
//    public void goBack(){
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
//        applyBean = (ApplyBean) mCache.getAsObject(JMessageUtils.APPLE_BEAN);
//
//        if(applyBean!= null) {
//            mListDataBean = applyBean.getData();
//            setListView(mListDataBean);
//        }
//
////        if(applyBean == null){
////            mPresenter.getApplyList(AppData.getUserName());
////        }else{
////            mListDataBean = applyBean.getData();
////            setListView(mListDataBean);
////        }
//
//        mListView = (SwipeMenuListView)findViewById(R.id.lv_new_friend);
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
//                ApplyBean.DataBean dataBeanItem = mListDataBean.get(position);
//                switch (index) {
//                    case 0:
//                        // delete删除某个会话
////                        mPresenter.deleteApplyDate(AppData.getUserName(), dataBeanItem.getFusername(),position);
//                        break;
//                }
//            }
//        });
//        //单点某一个Item
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ApplyBean.DataBean dataBeanItem = mListDataBean.get(position);
//                final Intent intent = new Intent();
//            }
//        });
//    }
//
//
//    /**
//     * 设置显示所有申请信息
//     * @param listDataBean
//     */
//    private void setListView(List<ApplyBean.DataBean> listDataBean){
//        if (listDataBean != null) {
//            mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(this, listDataBean);
//            mListView.setAdapter(mSwipeMenuListViewAdapter);
//            mSwipeMenuListViewAdapter.notifyDataSetChanged();
//        }
//    }
//    private int dp2px(int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                getResources().getDisplayMetrics());
//    }
//
//    @Override
//    public void getApplyListSuccess(ApplyBean applyBean) {
//        mListDataBean = applyBean.getData();
//        setListView(mListDataBean);
//    }
//
//    @Override
//    public void getApplyListError() {
//        ToastUtil.show(this,"未获取到信息");
//    }
//
//    @Override
//    public void deleteApplyDateSuccess(int position) {
//        mListDataBean.remove(position);
//        if(mSwipeMenuListViewAdapter!=null){
//            mSwipeMenuListViewAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void deleteApplyDateError() {
//        ToastUtil.show(this,"删除申请失败，请重试");
//    }
//
//
//    /**
//     *拒绝申请提示框
//     */
//    public void showNoAgreeDialog(final ApplyBean.DataBean dataBean){
//        CommonUtils.showInfoDialog(this, "拒绝该用户的好友申请吗？", "提示", "拒绝", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                mPresenter.noAgree(AppData.getUserName(),dataBean.getFusername());
//            }
//        }, null);
//    }
//    @Override
//    public void noAgreeSuccess() {
//        ToastUtil.show(this,"拒绝成功");
//        setListView(mListDataBean);
//    }
//
//    @Override
//    public void noAgreeError() {
//        ToastUtil.show(this,"拒绝申请失败，请重试");
//    }
//
//    /**
//     *同意申请提示框
//     */
//    public void showYesAgreeDialog(final ApplyBean.DataBean dataBean){
//        CommonUtils.showInfoDialog(this, "同意该用户的好友申请吗？", "提示", "同意", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                mPresenter.yesAgree(AppData.getUserName(),dataBean.getFusername());
//            }
//        }, null);
//    }
//    @Override
//    public void yesAgreeSuccess() {
//        ToastUtil.show(this,"同意申请");
//        setListView(mListDataBean);
//    }
//
//    @Override
//    public void yesAgreeError() {
//        ToastUtil.show(this,"同意申请失败，请重试");
//    }
//
//
//}
