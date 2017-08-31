package com.ygst.cenggeche.ui.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.friendlist.FriendListActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.fragment.cengche.CengCheFragment;
import com.ygst.cenggeche.ui.fragment.me.MeFragment;
import com.ygst.cenggeche.ui.fragment.message.MessageFragment;
import com.ygst.cenggeche.ui.fragment.nearby.NearbyFragment;
import com.ygst.cenggeche.ui.fragment.shaoren.ShaoRenFragment;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MyInfoUpdatedEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import im.sdk.debug.activity.friend.ShowFriendReasonActivity;
import im.sdk.debug.activity.setting.ShowLogoutReasonActivity;
import im.sdk.debug.activity.showinfo.ShowMyInfoUpdateActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View {

    private String TAG = "MainActivity";
    private CengCheFragment mCengCheFragment;
    private ShaoRenFragment mShaoRenFragment;
    private NearbyFragment mNearbyFragment;
    private MessageFragment mMsgFragment;
    private MeFragment mMeFragment;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.rl_toolbar_cengche)
    RelativeLayout mRLtoolbarCengChe;
    @BindView(R.id.rl_toolbar_nearby)
    RelativeLayout mRLtoolbarNearby;
    @BindView(R.id.rl_toolbar_message)
    RelativeLayout mRLtoolbarMessage;

    //标题栏左边按钮点击事件
//    @OnClick(R.id.iv_title_menu_left)
//    public void titleMenuLeft() {
//        Intent intent = new Intent();
//        intent.setClass(this, AddFriendActivity.class);
//        startActivity(intent);
//    }
    //标题栏【消息】右边按钮点击事件
    @OnClick(R.id.tv_show_friend_list)
    public void titleMenuRight() {
        Intent intent = new Intent();
        intent.setClass(this, FriendListActivity.class);
        startActivity(intent);
    }

    //菜单——蹭车
    @BindView(R.id.iv_cengche)
    ImageView mImageViewCengChe;
    @BindView(R.id.tv_cengche)
    TextView mTextViewCengChe;

    //蹭车点击事件
    @OnClick(R.id.rl_cengche)
    public void onClickCengChe() {
        setOnClickMenu(R.id.rl_cengche);
    }

    //菜单——附近
    @BindView(R.id.iv_nearby)
    ImageView mImageViewNearby;
    @BindView(R.id.tv_nearby)
    TextView mTextViewNearby;

    //附近点击事件
    @OnClick(R.id.rl_nearby)
    public void onClickNearby() {
        setOnClickMenu(R.id.rl_nearby);
    }

    //菜单——消息
    @BindView(R.id.iv_message)
    ImageView mImageViewMessage;
    @BindView(R.id.tv_unread_count)
    TextView mTvUnreadCount;
    @BindView(R.id.tv_message)
    TextView mTextViewMessage;
    @BindView(R.id.tv_all_unread_count)
    TextView mTextViewAllUnreadCount;

    //消息点击事件
    @OnClick(R.id.rl_message)
    public void onClickMessage() {
        if (AppData.isLoginEd() && JMessageClient.getMyInfo() != null) {
            setOnClickMenu(R.id.rl_message);
        } else {
            CommonUtils.startActivity(this, LoginActivity.class);
        }
    }

    //菜单——我的
    @BindView(R.id.iv_me)
    ImageView mImageViewMe;
    @BindView(R.id.tv_me)
    TextView mTextViewMe;

    //我的点击事件
    @OnClick(R.id.rl_me)
    public void onClickMe() {
        if (AppData.isLoginEd() && JMessageClient.getMyInfo() != null) {
            setOnClickMenu(R.id.rl_me);
        } else {
            CommonUtils.startActivity(this, LoginActivity.class);
        }
    }

    //顶部标题栏按钮【蹭车】
    @BindView(R.id.btn_cengche)
    Button mBtnCengChe;
    //顶部标题栏按钮【捎人】
    @BindView(R.id.btn_shaoren)
    Button mBtnShaoRen;

    //顶部标题栏【蹭车】点击事件
    @OnClick(R.id.btn_cengche)
    public void onClickToolbarCengChe() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mBtnCengChe.setTextColor(getResources().getColor(R.color.colorTheme));
        mBtnCengChe.setBackgroundResource(R.drawable.button_cengche1);
        mBtnShaoRen.setTextColor(getResources().getColor(R.color.white));
        mBtnShaoRen.setBackgroundResource(R.drawable.button_shaoren2);
        if (mCengCheFragment == null) {
            mCengCheFragment = new CengCheFragment();
        }
        // 使用当前Fragment的布局替代content的控件
        transaction.replace(R.id.content, mCengCheFragment);
        // 事务提交
        transaction.commit();
    }

    //顶部标题栏【捎人】点击事件
    @OnClick(R.id.btn_shaoren)
    public void onClickToolbarShaoRen() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mBtnShaoRen.setTextColor(getResources().getColor(R.color.colorTheme));
        mBtnShaoRen.setBackgroundResource(R.drawable.button_shaoren1);
        mBtnCengChe.setTextColor(getResources().getColor(R.color.white));
        mBtnCengChe.setBackgroundResource(R.drawable.button_cengche2);
        if (mShaoRenFragment == null) {
            mShaoRenFragment = new ShaoRenFragment();
        }
        // 使用当前Fragment的布局替代content的控件
        transaction.replace(R.id.content, mShaoRenFragment);
        // 事务提交
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(mToolbar, "", false);
        JMessageClient.registerEventReceiver(this);
        init();
    }

    private void init() {
        //默认为“蹭车”页面
        setOnClickMenu(R.id.rl_cengche);
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        super.onResume();
        showAllUnReadMsgCount();
    }

    /**
     * 显示全部未读消息数
     */
    public void showAllUnReadMsgCount() {

        //右上角未读的验证消息数量
        if (AppData.getUnReadApplyCount() > 0) {
            mTvUnreadCount.setVisibility(View.VISIBLE);
            mTvUnreadCount.setText("" + AppData.getUnReadApplyCount());
        } else {
            mTvUnreadCount.setVisibility(View.GONE);
        }

        if (JMessageClient.getAllUnReadMsgCount() > 0) {
            mTextViewAllUnreadCount.setVisibility(View.VISIBLE);
            mTextViewAllUnreadCount.setText("" + JMessageClient.getAllUnReadMsgCount());
        } else {
            mTextViewAllUnreadCount.setVisibility(View.GONE);
        }
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

    private long[] mMessageHits = new long[2];
    private long[] mCengCheHits = new long[2];
    private long[] mNeardyHits = new long[2];

    /**
     * 加载顶部标题栏，底部菜单按钮和对应的Fragment
     *
     * @param id --菜单按钮Id
     */
    private void setOnClickMenu(int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //======蹭车
        if (id == R.id.rl_cengche) {
            doubleClick(R.id.rl_cengche, mCengCheHits);
            mImageViewCengChe.setImageResource(R.mipmap.icon_cengche);
            mTextViewCengChe.setTextColor(getResources().getColor(R.color.colorTheme));
            mRLtoolbarCengChe.setVisibility(View.VISIBLE);
            if (mCengCheFragment == null) {
                mCengCheFragment = new CengCheFragment();
            }
            // 使用当前Fragment的布局替代content的控件
            transaction.replace(R.id.content, mCengCheFragment);
        } else {
            mRLtoolbarCengChe.setVisibility(View.GONE);
            mImageViewCengChe.setImageResource(R.mipmap.icon_cengche_un);
            mTextViewCengChe.setTextColor(getResources().getColor(R.color.gray));
        }
        //=====附近
        if (id == R.id.rl_nearby) {
            doubleClick(R.id.rl_nearby, mNeardyHits);
            mImageViewNearby.setImageResource(R.mipmap.icon_nearby);
            mTextViewNearby.setTextColor(getResources().getColor(R.color.colorTheme));
            mRLtoolbarNearby.setVisibility(View.VISIBLE);
            if (mNearbyFragment == null) {
                mNearbyFragment = new NearbyFragment();
            }
            transaction.replace(R.id.content, mNearbyFragment);
        } else {
            mRLtoolbarNearby.setVisibility(View.GONE);
            mImageViewNearby.setImageResource(R.mipmap.icon_nearby_un);
            mTextViewNearby.setTextColor(getResources().getColor(R.color.gray));
        }
        //=====消息
        if (id == R.id.rl_message) {
            doubleClick(R.id.rl_message, mMessageHits);
            mImageViewMessage.setImageResource(R.mipmap.icon_message);
            mTextViewMessage.setTextColor(getResources().getColor(R.color.colorTheme));
            mRLtoolbarMessage.setVisibility(View.VISIBLE);
            if (mMsgFragment == null) {
                mMsgFragment = new MessageFragment();
            }
            transaction.replace(R.id.content, mMsgFragment);
        } else {
            mRLtoolbarMessage.setVisibility(View.GONE);
            mImageViewMessage.setImageResource(R.mipmap.icon_message_un);
            mTextViewMessage.setTextColor(getResources().getColor(R.color.gray));
        }
        //=====我的
        if (id == R.id.rl_me) {
            mImageViewMe.setImageResource(R.mipmap.icon_me);
            mTextViewMe.setTextColor(getResources().getColor(R.color.colorTheme));
            mToolbar.setVisibility(View.GONE);
            if (mMeFragment == null) {
                mMeFragment = new MeFragment();
            }
            transaction.replace(R.id.content, mMeFragment);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mImageViewMe.setImageResource(R.mipmap.icon_me_un);
            mTextViewMe.setTextColor(getResources().getColor(R.color.gray));
        }
        // 事务提交
        transaction.commit();
    }


    /**
     * 判断是否双击(这里有个问题，就是连续点击两个不同 按钮也会触发，建议分开添加此方法)
     *
     * @param itemId 点击的按钮的Id
     * @param mHits  每次点击的时间的数组
     */
    private void doubleClick(int itemId, long[] mHits) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取手机开机时间
        if (mHits[mHits.length - 1] - mHits[0] < 500) { //间隔时间设置为500毫秒
            /**双击的业务逻辑*/
            switch (itemId) {
                case R.id.rl_cengche:
                    ToastUtil.show(this, "蹭车双击");
                    break;
                case R.id.rl_nearby:
                    ToastUtil.show(this, "附近双击");
                    break;
                case R.id.rl_message:
                    ToastUtil.show(this, "消息双击");
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 8) {
            ToastUtil.show(this, "onActivityResult888");
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
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();


        Intent intent = new Intent(this, ShowFriendReasonActivity.class);

        switch (event.getType()) {
            case invite_received://收到好友邀请
                AppData.savaUnReadApplyCount(+1);
                mTvUnreadCount.setVisibility(View.VISIBLE);
                mTvUnreadCount.setText("" + AppData.getUnReadApplyCount());
//                intent.putExtra("invite_received", "fromUsername = " + fromUsername + "\nfromUserAppKey" + appkey + "\nreason = " + reason);
//                intent.putExtra("username", fromUsername);
//                intent.putExtra("appkey", appkey);
//                intent.setFlags(1);
//                startActivity(intent);
                break;
            case invite_accepted://对方接收了你的好友邀请
                intent.putExtra("invite_accepted", "对方接受了你的好友邀请");
                intent.setFlags(2);
                startActivity(intent);
                break;
            case invite_declined://对方拒绝了你的好友邀请
                intent.putExtra("invite_declined", "对方拒绝了你的好友邀请\n拒绝原因:" + event.getReason());
                intent.setFlags(3);
                startActivity(intent);
                break;
            case contact_deleted://对方将你从好友中删除
                intent.putExtra("contact_deleted", "对方将你从好友中删除");
                intent.setFlags(4);
                startActivity(intent);
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

        final Intent notificationIntent = new Intent(getApplicationContext(), MyChatActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(JMessageUtils.MESSAGE, msg);
        notificationIntent.putExtras(mBundle);
        startActivity(notificationIntent);

//        final Intent notificationIntent = new Intent(getApplicationContext(), ShowMessageActivity.class);
//        MessageContent content = msg.getContent();
//        switch (msg.getContentType()) {
//            case text:
//                TextContent textContent = (TextContent) content;
//                notificationIntent.setFlags(1);
//                notificationIntent.putExtra(CreateSigTextMessageActivity.TEXT_MESSAGE, "消息类型 = " + msg.getContentType() +
//                        "\n消息内容 = " + textContent.getText() + "\n附加字段 = " + textContent.getStringExtras() +
//                        "\n群消息isAtMe = " + msg.isAtMe() + "\n群消息isAtAll = " + msg.isAtAll());
//                startActivity(notificationIntent);
//                break;
//            case image:
//                ImageContent imageContent = (ImageContent) content;
//                imageContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
//                    @Override
//                    public void onComplete(int i, String s, File file) {
//                        if (i == 0) {
//                            notificationIntent.setFlags(2);
//                            notificationIntent.putExtra("image_path", file.getAbsolutePath());
//                            startActivity(notificationIntent);
//                        }
//                    }
//                });
//                final List<String> list = new ArrayList<String>();
//                msg.setOnContentDownloadProgressCallback(new ProgressUpdateCallback() {
//                    @Override
//                    public void onProgressUpdate(double v) {
//                        String progressStr = (int) (v * 100) + "%";
//                        list.add(progressStr);
//                        notificationIntent.putStringArrayListExtra(SET_DOWNLOAD_PROGRESS, (ArrayList<String>) list);
//                    }
//                });
//
//                boolean callbackExists = msg.isContentDownloadProgressCallbackExists();
//                notificationIntent.putExtra(IS_DOWNLOAD_PROGRESS_EXISTS, callbackExists + "");
//                break;
//            case voice:
//                VoiceContent voiceContent = (VoiceContent) content;
//                voiceContent.downloadVoiceFile(msg, new DownloadCompletionCallback() {
//                    @Override
//                    public void onComplete(int i, String s, File file) {
//                        if (i == 0) {
//                            String path = file.getPath();
//                            notificationIntent.setFlags(3);
//                            notificationIntent.putExtra("voice", path);
//                            startActivity(notificationIntent);
//                        }
//                    }
//                });
//                break;
//            case file:
//                UserInfo fromUser = msg.getFromUser();
//                String userName = fromUser.getUserName();
//                String appKey = fromUser.getAppKey();
//                ConversationType targetType = msg.getTargetType();
//
//                int id = msg.getId();
//
//                notificationIntent.putExtra("user", userName);
//                notificationIntent.putExtra("appkey", appKey);
//                notificationIntent.putExtra("msgid", id);
//                notificationIntent.putExtra("isGroup", targetType + "");
//                notificationIntent.setFlags(10);
//
//                startActivity(notificationIntent);
//                break;
//
//            case location:
//                LocationContent locationContent = (LocationContent) content;
//                String address = locationContent.getAddress();
//                Number latitude = locationContent.getLatitude();
//                Number scale = locationContent.getScale();
//                Number longitude = locationContent.getLongitude();
//
//                String la = String.valueOf(latitude);
//                String sc = String.valueOf(scale);
//                String lo = String.valueOf(longitude);
//
//                notificationIntent.setFlags(4);
//                notificationIntent.putExtra("address", address);
//                notificationIntent.putExtra("latitude", la);
//                notificationIntent.putExtra("scale", sc);
//                notificationIntent.putExtra("longitude", lo);
//
//                startActivity(notificationIntent);
//                break;
//
//            default:
//                break;
//        }
    }

//    /**
//     * 消息事件实体类 MessageEvent
//     *(之前是onEvent(),改成了onEventMainThread()主线程模式)
//     * @param event
//     */
//    public void onEventMainThread(MessageEvent event) {
//        showAllUnReadMsgCount();
//        Message msg = event.getMessage();
//        switch (msg.getContentType()) {
//            case custom:
//                final ConversationType targetType = event.getMessage().getTargetType();
//                final Intent intent = new Intent(getApplicationContext(), ShowCustomMessageActivity.class);
//                CustomContent customContent = (CustomContent) msg.getContent();
//                Map allStringValues = customContent.getAllStringValues();
//                if (targetType.equals(ConversationType.group)) {
//                    intent.putExtra(CREATE_GROUP_CUSTOM_KEY, allStringValues.toString());
//                    intent.setFlags(1);
//
//                } else if (targetType.equals(ConversationType.single)) {
//                    intent.putExtra(CUSTOM_MESSAGE_STRING, allStringValues.toString());
//                    UserInfo fromUser = msg.getFromUser();
//                    intent.putExtra(CUSTOM_FROM_NAME, fromUser.getUserName());
//                    intent.setFlags(2);
//                }
//                startActivity(intent);
//                break;
//            //其实sdk是会自动下载语音的.本方法是当sdk自动下载失败时可以手动调用进行下载而设计的.同理于缩略图下载
//            case voice:
//                final Intent intentVoice = new Intent(getApplicationContext(), ShowDownloadVoiceInfoActivity.class);
//                final VoiceContent voiceContent = (VoiceContent) msg.getContent();
//                final int duration = voiceContent.getDuration();
//                final String format = voiceContent.getFormat();
//                /**=================     下载语音文件    =================*/
//                voiceContent.downloadVoiceFile(msg, new DownloadCompletionCallback() {
//                    @Override
//                    public void onComplete(int i, String s, File file) {
//                        if (i == 0) {
//                            Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
//                            intentVoice.putExtra(DOWNLOAD_INFO, "path = " + file.getPath() + "\n" + "duration = " + duration + "\n" + "format = " + format + "\n");
//                            startActivity(intentVoice);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "downloadVoiceFile" + ", responseCode = " + i + " ; desc = " + s);
//                        }
//                    }
//                });
//                break;
//            case eventNotification:
//                String eventText = ((EventNotificationContent) msg.getContent()).getEventText();
//                Intent intentNotification = new Intent(getApplicationContext(), ShowGroupNotificationActivity.class);
//                intentNotification.putExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION, eventText);
//
//                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
//                intentNotification.putExtra(CreateGroupTextMsgActivity.GROUP_NOTIFICATION_LIST, userNames.toString());
//                startActivity(intentNotification);
//                break;
//            case image:
//                final Intent intentImage = new Intent(getApplicationContext(), ShowDownloadPathActivity.class);
//                final ImageContent imageContent = (ImageContent) msg.getContent();
//                //其实sdk是会自动下载缩略图的.本方法是当sdk自动下载失败时可以手动调用进行下载而设计的.同理于语音下载
//                /**=================     下载图片信息中的缩略图    =================*/
//                imageContent.downloadThumbnailImage(msg, new DownloadCompletionCallback() {
//                    @Override
//                    public void onComplete(int i, String s, File file) {
//                        if (i == 0) {
//                            Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
//                            intentImage.putExtra(DOWNLOAD_THUMBNAIL_IMAGE, file.getPath());
//                        } else {
//                            Toast.makeText(getApplicationContext(), "下载缩略图失败", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "downloadThumbnailImage" + ", responseCode = " + i + " ; desc = " + s);
//                        }
//                    }
//                });
//
//                /**=================     下载图片消息中的原图    =================*/
//                imageContent.downloadOriginImage(msg, new DownloadCompletionCallback() {
//                    @Override
//                    public void onComplete(int i, String s, File file) {
//                        if (i == 0) {
//                            Toast.makeText(getApplicationContext(), "下载原图成功", Toast.LENGTH_SHORT).show();
//                            intentImage.putExtra(IS_UPLOAD, imageContent.isFileUploaded() + "");
//                            intentImage.putExtra(DOWNLOAD_ORIGIN_IMAGE, file.getPath());
//                            startActivity(intentImage);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "下载原图失败", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "downloadOriginImage" + ", responseCode = " + i + " ; desc = " + s);
//                        }
//                    }
//                });
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 用户下线事件UserLogoutEvent (已过时，请使用LoginStateChangeEvent代替)
     *
     * @param event
     */
    public void onEvent(LoginStateChangeEvent event) {
        LogUtils.i(TAG, "onEvent-----用户下线事件UserLogoutEvent (已过时，请使用LoginStateChangeEvent代替)");
        LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        Intent intent = new Intent(getApplicationContext(), ShowLogoutReasonActivity.class);
        intent.putExtra(LOGOUT_REASON, "reason = " + reason + "\n" + "logout user name = " + myInfo.getUserName());
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
        UserInfo myInfo = event.getMyInfo();
        Intent intent = new Intent(this, ShowMyInfoUpdateActivity.class);
        intent.putExtra(INFO_UPDATE, myInfo.getUserName());
        startActivity(intent);
    }

}
