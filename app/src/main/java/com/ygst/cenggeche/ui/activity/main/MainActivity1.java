package com.ygst.cenggeche.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.fragment.cengche.CengCheFragment;
import com.ygst.cenggeche.ui.fragment.me.MeFragment;
import com.ygst.cenggeche.ui.fragment.message.MessageFragment;
import com.ygst.cenggeche.ui.fragment.nearby.NearbyFragment;
import com.ygst.cenggeche.utils.JMessageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MyInfoUpdatedEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 主页面
 * Created by lijuan on 2016/8/23.
 */
public class MainActivity1 extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View {
    public static MainActivity1 instance;
    private int GO_LOGIN = 123;
    private String TAG = "MainActivity";
    private String[] titles = new String[]{"蹭车", "附近", "消息", "我"};
    private TabLayout mTabLayout;
    private MyViewPager mViewPager;
    private FragmentAdapter adapter;
    //ViewPage选项卡页面集合
    private List<Fragment> mListFragments;
    //Tab标题集合
    private List<String> mTitles;
    /**
     * 图片数组
     */
    private int[] mImgs = new int[]{R.drawable.selector_tab_cengche, R.drawable.selector_tab_nearby, R.drawable.selector_tab_message,
            R.drawable.selector_tab_me};

    TextView mTextViewAllUnreadCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        JMessageClient.registerEventReceiver(this);
        initView();
    }

    private void initView() {

        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add(titles[i]);
        }

        Fragment fmCengChe = new CengCheFragment();
        Fragment fmNearby = new NearbyFragment();
        Fragment fmMessage = new MessageFragment();
        Fragment fmMe = new MeFragment();
        mListFragments = new ArrayList<>();
        mListFragments.add(fmCengChe);
        mListFragments.add(fmNearby);
        mListFragments.add(fmMessage);
        mListFragments.add(fmMe);

        adapter = new FragmentAdapter(getSupportFragmentManager(), mListFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来

        mTabLayout.setSelectedTabIndicatorHeight(1);
        for (int i = 0; i < adapter.getCount(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
//            TabLayout.Tab tab = mTabLayout.newTab();
            if (itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView(R.layout.item_tab);
                TextView textView = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
                textView.setText(mTitles.get(i));
                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);
                imageView.setImageResource(mImgs[i]);
                if (i == 2) {
                    mTextViewAllUnreadCount = (TextView) itemTab.getCustomView().findViewById(R.id.tv_all_unread_count);
                }
            }
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
    }


    TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //选中了tab的逻辑
            if (tab.getPosition() == 2 || tab.getPosition() == 3) {
                if (!AppData.isLoginEd() || JMessageClient.getMyInfo() == null) {
                    Intent intent = new Intent(MainActivity1.this, LoginActivity.class);
                    startActivityForResult(intent, GO_LOGIN);
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            //未选中tab的逻辑
//            if (tab.getPosition() != 0 && tab.getPosition() != 1) {
//                if (!AppData.isLoginEd() || JMessageClient.getMyInfo() == null) {
//                    CommonUtils.startActivity(MainActivity1.this, LoginActivity.class);
//                }
//            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            //再次选中tab的逻辑
        }
    };


    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        super.onResume();
        showAllUnReadMsgCount();
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    public void setPagerOne() {
        // 切换到指定第一页
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_LOGIN) {
            setPagerOne();
//            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }

    /**
     * 显示全部未读消息数
     */
    public void showAllUnReadMsgCount() {

        if (JMessageClient.getAllUnReadMsgCount() > 0) {
            mTextViewAllUnreadCount.setVisibility(View.VISIBLE);
            mTextViewAllUnreadCount.setText("" + JMessageClient.getAllUnReadMsgCount());
        } else {
            mTextViewAllUnreadCount.setVisibility(View.GONE);
        }
    }

    public static final String SET_DOWNLOAD_PROGRESS = "set_download_progress";
    public static final String IS_DOWNLOAD_PROGRESS_EXISTS = "is_download_progress_exists";
    public static final String CREATE_GROUP_CUSTOM_KEY = "create_group_custom_key";
    public static final String CUSTOM_MESSAGE_STRING = "custom_message_string";
    public static final String CUSTOM_FROM_NAME = "custom_from_name";
    public static final String DOWNLOAD_INFO = "download_info";
    public static final String INFO_UPDATE = "info_update";
    public static final String DOWNLOAD_ORIGIN_IMAGE = "download_origin_image";
    public static final String DOWNLOAD_THUMBNAIL_IMAGE = "download_thumbnail_image";
    public static final String IS_UPLOAD = "is_upload";
    public static final String LOGOUT_REASON = "logout_reason";

    /**
     * 新增联系人相关通知事件ContactNotifyEvent
     *
     * @param event
     */
    public void onEvent(ContactNotifyEvent event) {
        LogUtils.i(TAG, "onEvent-----新增联系人相关通知事件ContactNotifyEvent");
        switch (event.getType()) {
            case invite_received://收到好友邀请
                break;
            case invite_accepted://对方接收了你的好友邀请
                break;
            case invite_declined://对方拒绝了你的好友邀请
                break;
            case contact_deleted://对方将你从好友中删除
                break;
            default:
                break;
        }
    }

    /**
     * 通知栏点击事件实体类NotificationClickEvent
     *
     * @param event
     */
    public void onEvent(NotificationClickEvent event) {
        LogUtils.i(TAG, "onEvent-----通知栏点击事件实体类NotificationClickEvent");
        Message msg = event.getMessage();

        Intent notificationIntent = new Intent(getApplicationContext(), MyChatActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(JMessageUtils.MESSAGE, msg);
        notificationIntent.putExtras(mBundle);
        startActivity(notificationIntent);
    }

    /**
     * 消息事件实体类 MessageEvent
     * (之前是onEvent(),改成了onEventMainThread()主线程模式)
     *
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        showAllUnReadMsgCount();
    }

    /**
     * 用户下线事件UserLogoutEvent (已过时，请使用LoginStateChangeEvent代替)
     *
     * @param event
     */
    public void onEvent(LoginStateChangeEvent event) {
        LogUtils.i(TAG, "onEvent-----用户下线事件UserLogoutEvent (已过时，请使用LoginStateChangeEvent代替)");
        LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(LOGOUT_REASON, "LogOut");
        startActivity(intent);
//        Intent intent = new Intent(getApplicationContext(), ShowLogoutReasonActivity.class);
//        intent.putExtra(LOGOUT_REASON, "reason = " + reason + "\n" + "logout user name = " + myInfo.getUserName());
//        startActivity(intent);
    }

    /**
     * 离线消息事件实体类 OfflineMessageEvent Since 2.1.0
     *
     * @param event
     */
    public void onEventMainThread(OfflineMessageEvent event) {
        LogUtils.i(TAG, "onEvent----- 离线消息事件实体类 OfflineMessageEvent Since 2.1.0");
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        List<Integer> offlineMsgIdList = new ArrayList<>();
        if (conversation != null && newMessageList != null) {
            for (Message msg : newMessageList) {
                offlineMsgIdList.add(msg.getId());
            }
//            mTv_showOfflineMsg.append(String.format(Locale.SIMPLIFIED_CHINESE, "收到%d条来自%s的离线消息。\n", newMessageList.size(), conversation.getTargetId()));
//            mTv_showOfflineMsg.append("会话类型 = " + conversation.getType() + "\n消息ID = " + offlineMsgIdList + "\n\n");
        } else {
//            mTv_showOfflineMsg.setText("conversation is null or new message list is null");
        }
    }

    /**
     * 会话刷新事件实体类 ConversationRefreshEvent
     *
     * @param event
     */
    public void onEventMainThread(ConversationRefreshEvent event) {
        LogUtils.i(TAG, "onEvent-----会话刷新事件实体类ConversationRefreshEvent");
        Conversation conversation = event.getConversation();
        ConversationRefreshEvent.Reason reason = event.getReason();
        if (conversation != null) {
//            tv_refreshEvent.append(String.format(Locale.SIMPLIFIED_CHINESE, "收到ConversationRefreshEvent事件,待刷新的会话是%s.\n", conversation.getTargetId()));
//            tv_refreshEvent.append("事件发生的原因 : " + reason + "\n");
        } else {
//            tv_refreshEvent.setText("conversation is null");
        }
    }

    /**
     * 当前登录用户信息被更新事件实体类 MyInfoUpdatedEvent
     *
     * @param event
     */
    public void onEvent(MyInfoUpdatedEvent event) {
        LogUtils.i(TAG, "onEvent-----当前登录用户信息被更新事件实体类 MyInfoUpdatedEvent");
//        UserInfo myInfo = event.getMyInfo();
//        Intent intent = new Intent(this, ShowMyInfoUpdateActivity.class);
//        intent.putExtra(INFO_UPDATE, myInfo.getUserName());
//        startActivity(intent);
    }
}