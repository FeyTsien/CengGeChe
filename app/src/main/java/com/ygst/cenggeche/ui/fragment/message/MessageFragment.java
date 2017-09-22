package com.ygst.cenggeche.ui.fragment.message;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.notice.NoticeHeadBean;
import com.ygst.cenggeche.bean.systemNotify.SystemNotityHeadBean;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.activity.friendlist.FriendListActivity;
import com.ygst.cenggeche.ui.activity.main.MainActivity1;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.activity.notice.NoticeActivity;
import com.ygst.cenggeche.ui.activity.systemnotify.SystemNotifyActivity;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View,View.OnClickListener {
    private String TAG = "MessageFragment";
    public static MessageFragment instance = null;
    private Activity mContext;
    private View mRootView;
    List<Conversation> mListConversation;

    private TextView mTvUnReadApplyCount;
    private TextView mTvShowFriendList;

    private LinearLayout mLlSystemNotify;
    private TextView mTvLatestSystemMsg;
    private TextView mTvSystemUnreadCount;
    private LinearLayout mLlNotice;
    private TextView mTvLatestNoticeMsg;
    private TextView mTvNoticeUnreadCount;


    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
    private SwipeMenuListView mListView;
    // step 1. create a MenuCreator
    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            //添加了一个Open侧滑按钮
            SwipeMenuItem isReadItem = new SwipeMenuItem(getActivity().getApplicationContext());
            // set item background
            isReadItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                    0xCE)));
            // set item width
            isReadItem.setWidth(dp2px(90));
            // set item title
            isReadItem.setTitle("标为已读");
            // set item title fontsize
            isReadItem.setTitleSize(18);
            // set item title font color
            isReadItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(isReadItem);

            // 创建delete侧滑按钮
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "------------onCreate");
        //生命周期2：onCreate()；
        JMessageClient.registerEventReceiver(this);
        instance = this;
        mContext = this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i(TAG, "------------onCreateView");
        //生命周期3：onCreateView()；
        mRootView = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "------------onResume");
        initConversationListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    private void initView() {

        mTvUnReadApplyCount = (TextView)mRootView.findViewById(R.id.tv_unread_count);
        mTvShowFriendList = (TextView)mRootView.findViewById(R.id.tv_show_friend_list);
        mLlSystemNotify = (LinearLayout) mRootView.findViewById(R.id.ll_system_notify);
        mTvLatestSystemMsg = (TextView) mRootView.findViewById(R.id.tv_latest_system_msg);
        mTvSystemUnreadCount = (TextView) mRootView.findViewById(R.id.tv_system_unread_count);
        mLlNotice = (LinearLayout) mRootView.findViewById(R.id.ll_notice);
        mTvLatestNoticeMsg = (TextView) mRootView.findViewById(R.id.tv_latest_notice_msg);
        mTvNoticeUnreadCount = (TextView) mRootView.findViewById(R.id.tv_notice_unread_count);

        mTvShowFriendList.setOnClickListener(this);
        mLlSystemNotify.setOnClickListener(this);
        mLlNotice.setOnClickListener(this);

        mListView = (SwipeMenuListView) mRootView.findViewById(R.id.listView);
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
                LogUtils.i(TAG, "swipe End");
            }
        });
        //点击侧滑出来的菜单按钮
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                Conversation item = mListConversation.get(position);
                switch (index) {
                    case 0:
                        // 标记为已读
                        setIsRead(menu,item);
                        break;
                    case 1:
                        // delete删除某个会话
                        mPresenter.deleteConversation(item, position);
                        break;
                }
            }
        });
        //单点某一个Item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversation = mListConversation.get(position);
                final Intent intent = new Intent();

                if (conversation.getType().equals(ConversationType.single)){
                    String targetId = ((UserInfo) conversation.getTargetInfo()).getUserName();
                    intent.putExtra(JMessageUtils.TARGET_USERNAME, targetId);
                    intent.putExtra(JMessageUtils.TARGET_APP_KEY, conversation.getTargetAppKey());
                    intent.putExtra(JMessageUtils.IS_FRIEND,((UserInfo) conversation.getTargetInfo()).isFriend());
                    intent.setClass(mContext, MyChatActivity.class);
                    startActivity(intent);
                } else{
                    long groupId = ((GroupInfo) conversation.getTargetInfo()).getGroupID();
                    intent.putExtra(JMessageUtils.GROUP_ID_KEY, groupId);
                    intent.setClass(mContext, MyChatActivity.class);
                    startActivity(intent);
                }

            }
        });
        // 长按某一个Item
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
//				Toast.makeText(getApplicationContext(), position + " long click",0).show();
                return false;
            }
        });
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_show_friend_list:
                //进入我的蹭友
                CommonUtils.startActivity(getActivity(), FriendListActivity.class);
                break;
            case R.id.ll_system_notify:
                //进入系统通知
                CommonUtils.startActivity(getActivity(), SystemNotifyActivity.class);
                break;
            case R.id.ll_notice:
                //进入活动公告
                CommonUtils.startActivity(getActivity(), NoticeActivity.class);
                break;
        }
    }

    private void initConversationListView(){
        //获取系统通知
        mPresenter.getsystemInformHead();
        //获取活动公告
        mPresenter.getNoticeHead();

        if(AppData.getUnReadApplyCount()>0){
            //显示未读的验证消息条数
            showUnReadApplyCount();
        }else{
            //隐藏未读的验证消息数
            mTvUnReadApplyCount.setVisibility(View.GONE);
        }
        //获取显示会话列表
        mListConversation = JMessageClient.getConversationList();
        mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(getActivity(),mListConversation);
        if (mListConversation != null) {
            mListView.setAdapter(mSwipeMenuListViewAdapter);
        }
        if(mSwipeMenuListViewAdapter!=null){
            mSwipeMenuListViewAdapter.notifyDataSetChanged();
        }
    }

    private void setIsRead(SwipeMenu menu,Conversation conversation) {
        if(conversation.getUnReadMsgCnt()>0) {
            conversation.setUnReadMessageCnt(0);
        }
        MainActivity1 mainActivity = (MainActivity1) getActivity();
        mainActivity.showAllUnReadMsgCount();
        mSwipeMenuListViewAdapter.notifyDataSetChanged();
    }

    public void showUnReadApplyCount(){
        mTvUnReadApplyCount.setVisibility(View.VISIBLE);
        mTvUnReadApplyCount.setText(""+AppData.getUnReadApplyCount());
    }

    @Override
    public void getDeleteConversationSuccess(ConversationType type, int position) {
        mListConversation.remove(position);
        mSwipeMenuListViewAdapter.notifyDataSetChanged();
        if (type.equals(ConversationType.single)) {
//            ToastUtil.show(getActivity(), "删除单聊会话成功");
        } else {
//            ToastUtil.show(getActivity(), "删除群聊会话成功");
        }
    }

    @Override
    public void getDeleteConversationError() {
        ToastUtil.show(getActivity(), "删除会话失败");
    }

    @Override
    public void getNoticeHeadSuccess(NoticeHeadBean noticeHeadBean) {
        //最新的活动公告
        mTvLatestNoticeMsg.setText(noticeHeadBean.getData());
    }

    @Override
    public void getNoticeHeadError() {

    }

    @Override
    public void getsystemInformHeadSuccess(SystemNotityHeadBean systemNotityHeadBean) {
        //最新的系统通知
        mTvLatestSystemMsg.setText(systemNotityHeadBean.getData());
    }

    @Override
    public void getsystemInformHeadError() {

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    /**
     * 消息事件实体类 MessageEvent
     *(之前是onEvent(),改成了onEventMainThread()主线程模式)
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        initConversationListView();
    }

}
