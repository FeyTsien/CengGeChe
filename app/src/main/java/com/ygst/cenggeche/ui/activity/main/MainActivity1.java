package com.ygst.cenggeche.ui.activity.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.app.MyApplication;
import com.ygst.cenggeche.bean.NewAppVersionBean;
import com.ygst.cenggeche.download.service.DownloadService;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.fragment.cengche.CengCheFragment;
import com.ygst.cenggeche.ui.fragment.me.MeFragment;
import com.ygst.cenggeche.ui.fragment.message.MessageFragment;
import com.ygst.cenggeche.ui.fragment.nearby.NearbyFragment;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

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
import me.leolin.shortcutbadger.ShortcutBadger;

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
    private List<Fragment> mListFragments = new ArrayList<>();;
    //Tab标题集合
    private List<String> mTitles;
    /**
     * 图片数组
     */
    private int[] mImgs = new int[]{R.drawable.selector_tab_cengche, R.drawable.selector_tab_nearby, R.drawable.selector_tab_message,
            R.drawable.selector_tab_me};
    TextView mTextViewAllUnreadCount;
    //读取存储器，相机和定位权限
    String[] permission_read_camera = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.CAMERA",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"};
    private static final int REQUEST_PERMISSION= 1005;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        //开启读取文件，相机，定位权限
        if (ContextCompat.checkSelfPermission(getContext(), permission_read_camera[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission_read_camera, REQUEST_PERMISSION);
        }
        JMessageClient.registerEventReceiver(this);
        initView();
        //每次进入此页获取是否有最新版本
        if(!AppData.isNewApp()){
            mPresenter.getNewAppVersion();
        }else{

        }
    }

    private void initView() {

        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add(titles[i]);
        }

        mListFragments.add(new CengCheFragment());
        mListFragments.add(new NearbyFragment());
        mListFragments.add(new MessageFragment());
        mListFragments.add(new MeFragment());

        adapter = new FragmentAdapter(getSupportFragmentManager(), mListFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(3);//缓存当前界面每一侧的界面数(此方案适用于界面数较少的情况，避免缓存界面太多导致内存吃紧。)
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        mTabLayout.setSelectedTabIndicatorHeight(1);//tablayout底部线的高度
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        for (int i = 0; i < adapter.getCount(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
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
//        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
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

    /**
     * 获取新版APP信息，比对当前版本
     */
    @Override
    public void getNewAppVersionSuccess(NewAppVersionBean newAppVersionBean) {
        //获取当前应用版本信息
        getAppVersionName(this);
        String code = newAppVersionBean.getData().getVersion();
        String name = newAppVersionBean.getData().getVersionName();
        String updateDate = newAppVersionBean.getData().getUpdateDate();
        final String url = newAppVersionBean.getData().getPath();
        if (Double.parseDouble(code) > versioncode) {
            AppData.setIsNewApp(true);
            CommonUtils.showInfoDialog(this, "新版本号： "+name+"\n新版日期： "+updateDate, "发现新版本", "更新", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //执行下载
                    Intent intent = new Intent(MainActivity1.this, DownloadService.class);
                    intent.putExtra("url",url);
                    startService(intent);
                }
            }, null);
        }else{
            AppData.setIsNewApp(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_LOGIN) {
            setPagerOne();
//            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    ToastUtil.show(this, "还没有此功能，请去设置中开启。");
                }
                break;
        }
    }

    /**
     * 显示全部未读消息数
     */
    public void showAllUnReadMsgCount() {

        if (JMessageClient.getAllUnReadMsgCount() > 0) {
            mTextViewAllUnreadCount.setVisibility(View.VISIBLE);
            mTextViewAllUnreadCount.setText("" + JMessageClient.getAllUnReadMsgCount());
            AppData.savaBadgeCount(JMessageClient.getAllUnReadMsgCount());
            int badgeCount = AppData.getBadgeCount();
            ShortcutBadger.applyCount(getContext(), badgeCount);
        } else {
            mTextViewAllUnreadCount.setVisibility(View.GONE);
        }
    }

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
        UserInfo fromUser = msg.getFromUser();

        Intent notificationIntent = new Intent(this.getContext(), MyChatActivity.class);
        notificationIntent.putExtra(JMessageUtils.TARGET_USERNAME, fromUser.getUserName());
        notificationIntent.putExtra(JMessageUtils.TARGET_APP_KEY, fromUser.getAppKey());
        notificationIntent.putExtra(JMessageUtils.IS_FRIEND,fromUser.isFriend());
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getContext().startActivity(notificationIntent);
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
//        UserInfo myInfo = event.getMyInfo();
//        LoginStateChangeEvent.Reason reason = event.getReason();
        //被挤下线，清除登录信息，跳转到登录页
        JMessageClient.logout();
        MyApplication.clearLogin();
        this.setPagerOne();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(LOGOUT_REASON, "LogOut");
        startActivity(intent);
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
    }

    /**
     * 按返回键不退出应用。
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}