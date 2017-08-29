package cn.jmessage.android.uikit.chatting;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jarek.imageselect.activity.FolderListActivity;
import com.jarek.imageselect.bean.ImageFolderBean;
import com.ygst.cenggeche.ui.activity.addfriend.AddFriendActivity;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import cn.jmessage.android.uikit.chatting.utils.BitmapLoader;
import cn.jmessage.android.uikit.chatting.utils.DialogCreator;
import cn.jmessage.android.uikit.chatting.utils.Event;
import cn.jmessage.android.uikit.chatting.utils.FileHelper;
import cn.jmessage.android.uikit.chatting.utils.IdHelper;
import cn.jmessage.android.uikit.chatting.utils.SharePreferenceManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.eventbus.EventBus;

/*
 * 对话界面
 */
public class ChatActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        ChatView.OnSizeChangedListener, ChatView.OnKeyBoardChangeListener {

    private static final String TAG = "ChatActivity";
    private static final String MEMBERS_COUNT = "membersCount";
    private static final String GROUP_NAME = "groupName";
    private static final String DRAFT = "draft";
    private static final String MsgIDs = "msgIDs";
    private static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    private static final String PICTURE_PATH = "picturePath";
    private static final String TARGET_APP_KEY = "";
    private static final int REQUEST_CODE_TAKE_PHOTO = 4;
    private static final int REQUEST_CODE_SELECT_PICTURE = 6;
    private static final int RESULT_CODE_SELECT_PICTURE = 8;
    private static final int REQUEST_CODE_CHAT_DETAIL = 14;
    private static final int RESULT_CODE_CHAT_DETAIL = 15;
    private static final int RESULT_CODE_FRIEND_INFO = 17;
    private static final int REFRESH_LAST_PAGE = 0x1023;
    private static final int REFRESH_CHAT_TITLE = 0x1024;
    private static final int REFRESH_GROUP_NAME = 0x1025;
    private static final int REFRESH_GROUP_NUM = 0x1026;

    //录音权限
    String[] permission_record_audio = {"android.permission.RECORD_AUDIO"};
    private static final int REQUEST_PERMISSION_RECORD_AUDIO = 1001;
    //读取存储器和相机权限
    String[] permission_read_camera = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    private static final int REQUEST_PERMISSION_READ_CAMERA = 1002;


    private final UIHandler mUIHandler = new UIHandler(this);
    private boolean mIsSingle = true;
    private boolean isInputByKeyBoard = true;
    private boolean mShowSoftInput = false;
    private MsgListAdapter mChatAdapter;
    private ChatView mChatView;
    private Context mContext;
    private Conversation mConv;
    private Dialog mDialog;
    private MyReceiver mReceiver;
    private long mGroupId;
    private String mGroupName;
    private GroupInfo mGroupInfo;
    private String targetUserName;
    private String mTargetAppKey;
    private String mPhotoPath = null;

    Window mWindow;
    InputMethodManager mImm;
    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(IdHelper.getLayout(this, "jmui_activity_chat"));
        mChatView = (ChatView) findViewById(IdHelper.getViewID(this, "jmui_chat_view"));
        mChatView.initModule(mDensity, mDensityDpi);
        this.mWindow = getWindow();
        this.mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mChatView.setListeners(this);
        mChatView.setOnTouchListener(this);
        mChatView.setOnSizeChangedListener(this);
        mChatView.setOnKbdStateListener(this);
        initReceiver();

        Intent intent = getIntent();
        targetUserName = intent.getStringExtra(JMessageUtils.TARGET_USERNAME);
        mTargetAppKey = intent.getStringExtra(JMessageUtils.TARGET_APP_KEY);
        if (!TextUtils.isEmpty(targetUserName)) {
            mIsSingle = true;
            mConv = JMessageClient.getSingleConversation(targetUserName, mTargetAppKey);
            if (mConv != null) {
                UserInfo userInfo = (UserInfo) mConv.getTargetInfo();
                if (TextUtils.isEmpty(userInfo.getNickname())) {
                    mChatView.setChatTitle(userInfo.getUserName());
                } else {
                    mChatView.setChatTitle(userInfo.getNickname());
                }
            } else {
                mConv = Conversation.createSingleConversation(targetUserName, mTargetAppKey);
                UserInfo userInfo = (UserInfo) mConv.getTargetInfo();
                if (TextUtils.isEmpty(userInfo.getNickname())) {
                    mChatView.setChatTitle(userInfo.getUserName());
                } else {
                    mChatView.setChatTitle(userInfo.getNickname());
                }
            }
            mChatAdapter = new MsgListAdapter(mContext, targetUserName, mTargetAppKey, longClickListener);
        } else {
            mIsSingle = false;
            mGroupId = intent.getLongExtra(JMessageUtils.GROUP_ID_KEY, 0);
            Log.d(TAG, "GroupId : " + mGroupId);

            //UIKit 直接用getGroupInfo更新标题,而不用考虑从创建群聊跳转过来
            JMessageClient.getGroupInfo(mGroupId, new GetGroupInfoCallback() {
                @Override
                public void gotResult(int status, String desc, GroupInfo groupInfo) {
                    if (status == 0) {
                        mGroupInfo = groupInfo;
                        mChatView.setChatTitle(groupInfo.getGroupName());
                    }
                }
            });
            mConv = JMessageClient.getGroupConversation(mGroupId);
            if (mConv == null) {
                mConv = Conversation.createGroupConversation(mGroupId);
            }
            mChatAdapter = new MsgListAdapter(mContext, mGroupId, longClickListener);
        }

        String draft = intent.getStringExtra(DRAFT);
        if (draft != null && !TextUtils.isEmpty(draft)) {
            mChatView.setInputText(draft);
        }
        mChatView.setChatListAdapter(mChatAdapter);
        //单条消息点击事件
        mChatView.setListItemClickListener(mOnItemClickListener);

        mChatAdapter.initMediaPlayer();
        //监听下拉刷新
        mChatView.getListView().setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                mUIHandler.sendEmptyMessageDelayed(REFRESH_LAST_PAGE, 1000);
            }
        });
        // 滑动到底部
        mChatView.setToBottom();
    }

    // 监听耳机插入
    private void initReceiver() {
        mReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, filter);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent data) {
            if (data != null) {
                //插入了耳机
                if (data.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                    mChatAdapter.setAudioPlayByEarPhone(data.getIntExtra("state", 0));
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == IdHelper.getViewID(mContext, "jmui_return_btn")) {
            mConv.resetUnreadCount();
            dismissSoftInput();
            JMessageClient.exitConversation();
            //发送保存为草稿事件到会话列表
            if (mIsSingle) {
                EventBus.getDefault().post(new Event.DraftEvent(targetUserName, mTargetAppKey,
                        mChatView.getChatInput()));
            } else {
                EventBus.getDefault().post(new Event.DraftEvent(mGroupId, mChatView.getChatInput()));
            }
            finish();
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_right_btn")) {
            Intent intent = new Intent();
            intent.putExtra(JMessageUtils.TARGET_USERNAME,targetUserName);
            intent.putExtra(JMessageUtils.TARGET_APP_KEY,mTargetAppKey);
            intent.setClass(this, AddFriendActivity.class);
            startActivity(intent);
//            if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
//                mChatView.dismissMoreMenu();
//            }
//            dismissSoftInput();
            //TODO
//            startChatDetailActivity(mTargetId, mTargetAppKey, mGroupId);
            // 切换输入
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_switch_voice_ib")) {
            mChatView.dismissMoreMenu();
            isInputByKeyBoard = !isInputByKeyBoard;
            //当前为语音输入，点击后切换为文字输入，弹出软键盘
            if (isInputByKeyBoard) {
                mChatView.isKeyBoard();
                showSoftInputAndDismissMenu();
            } else {
                //开启权限
                if (ContextCompat.checkSelfPermission(this, permission_record_audio[0]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permission_record_audio, REQUEST_PERMISSION_RECORD_AUDIO);
                } else {
                    //否则切换到语音输入
                    mChatView.notKeyBoard(mConv, mChatAdapter, mChatView);
                    if (mShowSoftInput) {
                        if (mImm != null) {
                            mImm.hideSoftInputFromWindow(mChatView.getInputView().getWindowToken(), 0); //强制隐藏键盘
                            mShowSoftInput = false;
                        }
                    } else if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
                        mChatView.dismissMoreMenu();
                    }
                    Log.i(TAG, "setConversation success");
                }
            }
            // 发送文本消息
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_send_msg_btn")) {
            String msgContent = mChatView.getChatInput();
            mChatView.clearInput();
            mChatView.setToBottom();
            if (msgContent.equals("")) {
                return;
            }
            TextContent content = new TextContent(msgContent);
            final Message msg = mConv.createSendMessage(content);
            mChatAdapter.addMsgToList(msg);
            JMessageClient.sendMessage(msg);
            // 点击添加按钮，弹出更多选项菜单
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_add_file_btn")) {
            //如果在语音输入时点击了添加按钮，则显示菜单并切换到输入框
            if (!isInputByKeyBoard) {
                mChatView.isKeyBoard();
                isInputByKeyBoard = true;
                mChatView.showMoreMenu();
            } else {
                //如果弹出软键盘 则隐藏软键盘
                if (mChatView.getMoreMenu().getVisibility() != View.VISIBLE) {
                    dismissSoftInputAndShowMenu();
                    mChatView.focusToInput(false);
                    //如果弹出了更多选项菜单，则隐藏菜单并显示软键盘
                } else {
                    showSoftInputAndDismissMenu();
                }
            }
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_pick_from_camera_btn")) {
            //开启读取文件，相机权限
            if (ContextCompat.checkSelfPermission(this, permission_read_camera[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permission_read_camera, REQUEST_PERMISSION_READ_CAMERA);
            } else {
                takePhoto();
                if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
                    mChatView.dismissMoreMenu();
                }
            }
        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_pick_from_local_btn")) {
            //开启读取文件，相机权限
            if (ContextCompat.checkSelfPermission(this, permission_read_camera[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permission_read_camera, REQUEST_PERMISSION_READ_CAMERA);
            } else {
                if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
                    mChatView.dismissMoreMenu();
                }
                onMultiClick();
//                Intent intent = new Intent();
//                if (mIsSingle) {
//                    intent.putExtra(JChatUtils.TARGET_ID_KEY, mTargetId);
//                    intent.putExtra(TARGET_APP_KEY, mTargetAppKey);
//                } else {
//                    intent.putExtra(JChatUtils.GROUP_ID_KEY, mGroupId);
//                }
//                if (FileHelper.isSdCardExist()) {
//                    intent.setClass(this, AlbumListActivity.class);
//                    startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
//                } else {
//                    Toast.makeText(this, IdHelper.getString(mContext, "sdcard_not_exist_toast"), Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }

    /**
     * 多选图片（进入图片选择器）
     */
    public void onMultiClick() {
        FolderListActivity.startFolderListActivity(this, RESULT_CODE_SELECT_PICTURE, null, 9);
    }

    private void takePhoto() {
        if (FileHelper.isSdCardExist()) {
            mPhotoPath = FileHelper.createAvatarPath(null);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPhotoPath)));
            try {
                startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                Toast.makeText(mContext, IdHelper.getString(mContext, "camera_not_prepared"),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, IdHelper.getString(mContext, "sdcard_not_exist_toast"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理发送图片，刷新界面
     */
    private void handleImgRefresh(String path) {
        Bitmap bitmap = BitmapLoader.getBitmapFromFile(path, 720, 1280);
        ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
            @Override
            public void gotResult(int status, String desc, ImageContent imageContent) {
                if (status == 0) {
                    Message msg = mConv.createSendMessage(imageContent);
                    Intent intent = new Intent();
                    intent.putExtra(MsgIDs, new int[]{msg.getId()});
                    mChatAdapter.setSendImg(intent.getIntArrayExtra(MsgIDs));
                    mChatView.setToBottom();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed!");
        if (RecordVoiceButton.mIsPressed) {
            mChatView.dismissRecordDialog();
            mChatView.releaseRecorder();
            RecordVoiceButton.mIsPressed = false;
        }
        if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
            mChatView.dismissMoreMenu();
        } else {
            if (mConv != null) {
                mConv.resetUnreadCount();
            }
        }
        //发送保存为草稿事件到会话列表界面,作为UIKit使用可以去掉
        if (mIsSingle) {
            EventBus.getDefault().post(new Event.DraftEvent(targetUserName, mTargetAppKey, mChatView.getChatInput()));
        } else {
            EventBus.getDefault().post(new Event.DraftEvent(mGroupId, mChatView.getChatInput()));
        }

        super.onBackPressed();
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        mChatAdapter.releaseMediaPlayer();
        mChatView.releaseRecorder();
        mUIHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        RecordVoiceButton.mIsPressed = false;
        JMessageClient.exitConversation();
        Log.i(TAG, "[Life cycle] - onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        mChatAdapter.stopMediaPlayer();
        if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
            mChatView.dismissMoreMenu();
        }
        if (mConv != null) {
            mConv.resetUnreadCount();
        }
        Log.i(TAG, "[Life cycle] - onStop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (!RecordVoiceButton.mIsPressed) {
            mChatView.dismissRecordDialog();
        }
        String targetId = getIntent().getStringExtra(JMessageUtils.TARGET_USERNAME);
        if (!mIsSingle) {
            long groupId = getIntent().getLongExtra(JMessageUtils.GROUP_ID_KEY, 0);
            if (groupId != 0) {
                JMessageClient.enterGroupConversation(groupId);
            }
        } else if (null != targetId) {
            String appKey = getIntent().getStringExtra(TARGET_APP_KEY);
            JMessageClient.enterSingleConversation(targetId, appKey);
        }
        mChatAdapter.initMediaPlayer();
        Log.i(TAG, "[Life cycle] - onResume");
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_RECORD_AUDIO:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了
                    ToastUtil.show(this, "拥有录音功能啦，快去发语音吧。");
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    ToastUtil.show(this, "还没有录音功能，请去设置中开启。");
                }
                break;
            case REQUEST_PERMISSION_READ_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以去放肆了
                    ToastUtil.show(this, "拥有发照片功能啦，快去发照片吧。");
                } else {
                    // 权限被用户拒绝了，洗洗睡吧。
                    ToastUtil.show(this, "还没有查看照片功能，请去设置中开启。");
                }
                break;
        }
    }

    /**
     * 用于处理拍照发送图片返回结果以及从其他界面回来后刷新聊天标题
     * 或者聊天消息
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CODE_SELECT_PICTURE:
                    try {
                        List<ImageFolderBean> list = (List<ImageFolderBean>) data.getSerializableExtra("list");
                        if (list == null) {
                            return;
                        }
                        for (ImageFolderBean string : list) {
                            handleImgRefresh(string.path);
                        }
                    } catch (NullPointerException e) {
                        Log.i(TAG, "onActivityResult unexpected result");
                    }
                    break;

            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            try {
                handleImgRefresh(mPhotoPath);
            } catch (NullPointerException e) {
                Log.i(TAG, "onActivityResult unexpected result");
            }
        } else if (resultCode == RESULT_CODE_SELECT_PICTURE) {


//                //得到图片路径
//                ArrayList<String> pathList = data.getStringArrayListExtra(PICTURE_PATH);
//                for (String path : pathList) {
//                    LogUtils.i("path","图path: "+path);
//                    Bitmap bitmap = BitmapLoader.getBitmapFromFile(path, 720, 1280);
//                    ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
//                        @Override
//                        public void gotResult(int status, String desc, ImageContent imageContent) {
//                            if (status == 0) {
//                                Message msg = conv.createSendMessage(imageContent);
//                                Intent intent = new Intent();
//                                intent.putExtra(MsgIDs, new int[]{msg.getId()});
//                                handleImgRefresh(intent);
//                            }
//                        }
//                    });
//                }
            //如果作为UIKit使用,去掉以下几段代码if (resultCode == RESULT_CODE_CHAT_DETAIL) {
            if (!mIsSingle) {
                GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                UserInfo userInfo = groupInfo.getGroupMemberInfo(JMessageClient.getMyInfo().getUserName());
                //如果自己在群聊中，同时显示群人数
                if (userInfo != null) {
                    if (TextUtils.isEmpty(data.getStringExtra(NAME))) {
                        mChatView.setChatTitle(IdHelper.getString(mContext, "group"),
                                data.getIntExtra(MEMBERS_COUNT, 0));
                    } else {
                        mChatView.setChatTitle(data.getStringExtra(NAME),
                                data.getIntExtra(MEMBERS_COUNT, 0));
                    }
                } else {
                    if (TextUtils.isEmpty(data.getStringExtra(NAME))) {
                        mChatView.setChatTitle(IdHelper.getString(mContext, "group"));
                        mChatView.dismissGroupNum();
                    } else {
                        mChatView.setChatTitle(data.getStringExtra(NAME));
                        mChatView.dismissGroupNum();
                    }
                }

            } else mChatView.setChatTitle(data.getStringExtra(NAME));
            if (data.getBooleanExtra("deleteMsg", false)) {
                mChatAdapter.clearMsgList();
            }
        } else if (resultCode == RESULT_CODE_FRIEND_INFO) {
            if (mIsSingle) {
                String nickname = data.getStringExtra(NICKNAME);
                if (!TextUtils.isEmpty(nickname)) {
                    mChatView.setChatTitle(nickname);
                }
            }
        }
    }

    private void dismissSoftInput() {
        if (mShowSoftInput) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(mChatView.getInputView().getWindowToken(), 0);
                mShowSoftInput = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showSoftInputAndDismissMenu() {
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 隐藏软键盘
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mChatView.invisibleMoreMenu();
        mChatView.getInputView().requestFocus();
        if (mImm != null) {
            mImm.showSoftInput(mChatView.getInputView(),
                    InputMethodManager.SHOW_FORCED);//强制显示键盘
        }
        mShowSoftInput = true;
        mChatView.setMoreMenuHeight();
    }

    public void dismissSoftInputAndShowMenu() {
        //隐藏软键盘
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 隐藏软键盘
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mChatView.showMoreMenu();
        if (mImm != null) {
            mImm.hideSoftInputFromWindow(mChatView.getInputView().getWindowToken(), 0); //强制隐藏键盘
        }
        mChatView.setMoreMenuHeight();
        mShowSoftInput = false;
    }

    private static class UIHandler extends Handler {
        private final WeakReference<ChatActivity> mActivity;

        public UIHandler(ChatActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ChatActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_LAST_PAGE:
                        activity.mChatAdapter.dropDownToRefresh();
                        activity.mChatView.getListView().onDropDownComplete();
                        if (activity.mChatAdapter.isHasLastPage()) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                activity.mChatView.getListView()
                                        .setSelectionFromTop(activity.mChatAdapter.getOffset(),
                                                activity.mChatView.getListView().getHeaderHeight());
                            } else {
                                activity.mChatView.getListView().setSelection(activity.mChatAdapter
                                        .getOffset());
                            }
                            activity.mChatAdapter.refreshStartPosition();
                        } else {
                            activity.mChatView.getListView().setSelection(0);
                        }
                        activity.mChatView.getListView()
                                .setOffset(activity.mChatAdapter.getOffset());
                        break;
                    case REFRESH_GROUP_NAME:
                        if (activity.mConv != null) {
                            int num = msg.getData().getInt(MEMBERS_COUNT);
                            String groupName = msg.getData().getString(GROUP_NAME);
                            activity.mChatView.setChatTitle(groupName, num);
                        }
                        break;
                    case REFRESH_GROUP_NUM:
                        int num = msg.getData().getInt(MEMBERS_COUNT);
                        activity.mChatView.setChatTitle(IdHelper.getString(activity, "group"), num);
                        break;
                    case REFRESH_CHAT_TITLE:
                        if (activity.mGroupInfo != null) {
                            //检查自己是否在群组中
                            UserInfo info = activity.mGroupInfo.getGroupMemberInfo(JMessageClient
                                    .getMyInfo().getUserName());
                            if (!TextUtils.isEmpty(activity.mGroupInfo.getGroupName())) {
                                activity.mGroupName = activity.mGroupInfo.getGroupName();
                                if (info != null) {
                                    activity.mChatView.setChatTitle(activity.mGroupName,
                                            activity.mGroupInfo.getGroupMembers().size());
                                    activity.mChatView.showRightBtn();
                                } else {
                                    activity.mChatView.setChatTitle(activity.mGroupName);
                                    activity.mChatView.dismissRightBtn();
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onKeyBoardStateChange(int state) {
        switch (state) {
            case ChatView.KEYBOARD_STATE_INIT:
                if (mImm != null) {
                    mImm.isActive();
                }
                if (mChatView.getMoreMenu().getVisibility() == View.INVISIBLE
                        || (!mShowSoftInput && mChatView.getMoreMenu().getVisibility() == View.GONE)) {

                    mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mChatView.getMoreMenu().setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 接收消息类事件
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        final Message msg = event.getMessage();
        //若为群聊相关事件，如添加、删除群成员
        Log.i(TAG, event.getMessage().toString());
        if (msg.getContentType() == ContentType.eventNotification) {
            GroupInfo groupInfo = (GroupInfo) msg.getTargetInfo();
            long groupId = groupInfo.getGroupID();
            UserInfo myInfo = JMessageClient.getMyInfo();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) msg
                    .getContent()).getEventNotificationType();
            if (groupId == mGroupId) {
                switch (type) {
                    case group_member_added:
                        //添加群成员事件
                        List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                        //群主把当前用户添加到群聊，则显示聊天详情按钮
                        refreshGroupNum();
                        if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mChatView.showRightBtn();
                                }
                            });
                        }

                        break;
                    case group_member_removed:
                        //删除群成员事件
                        userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                        //群主删除了当前用户，则隐藏聊天详情按钮
                        if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mChatView.dismissRightBtn();
                                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                                    if (TextUtils.isEmpty(groupInfo.getGroupName())) {
                                        mChatView.setChatTitle(IdHelper.getString(mContext, "group"));
                                    } else {
                                        mChatView.setChatTitle(groupInfo.getGroupName());
                                    }
                                    mChatView.dismissGroupNum();
                                }
                            });
                        } else {
                            refreshGroupNum();
                        }

                        break;
                    case group_member_exit:
                        refreshGroupNum();
                        break;
                }
            }
        }
        //刷新消息
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //收到消息的类型为单聊
                if (msg.getTargetType() == ConversationType.single) {
                    UserInfo userInfo = (UserInfo) msg.getTargetInfo();
                    String targetId = userInfo.getUserName();
                    String appKey = userInfo.getAppKey();
                    //判断消息是否在当前会话中
                    if (mIsSingle && targetId.equals(targetUserName) && appKey.equals(mTargetAppKey)) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        //收到的消息和Adapter中最后一条消息比较，如果最后一条为空或者不相同，则加入到MsgList
                        if (lastMsg == null || msg.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(msg);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    long groupId = ((GroupInfo) msg.getTargetInfo()).getGroupID();
                    if (groupId == mGroupId) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        if (lastMsg == null || msg.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(msg);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void refreshGroupNum() {
        Conversation conv = JMessageClient.getGroupConversation(mGroupId);
        GroupInfo groupInfo = (GroupInfo) conv.getTargetInfo();
        if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NAME;
            Bundle bundle = new Bundle();
            bundle.putString(GROUP_NAME, groupInfo.getGroupName());
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        } else {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NUM;
            Bundle bundle = new Bundle();
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        }
    }

    private MsgListAdapter.ContentLongClickListener longClickListener = new MsgListAdapter.ContentLongClickListener() {
        @Override
        public void onContentLongClick(final int position, View view) {
            Log.i(TAG, "long click position" + position);
            final Message msg = mChatAdapter.getMessage(position);
            UserInfo userInfo = msg.getFromUser();
            if (msg.getContentType() != ContentType.image) {
                // 长按文本弹出菜单
                String name = userInfo.getNickname();
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (v.getId() == IdHelper.getViewID(mContext, "jmui_copy_msg_btn")) {
                            if (msg.getContentType() == ContentType.text) {
                                final String content = ((TextContent) msg.getContent()).getText();
                                if (Build.VERSION.SDK_INT > 11) {
                                    ClipboardManager clipboard = (ClipboardManager) mContext
                                            .getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("Simple text", content);
                                    clipboard.setPrimaryClip(clip);
                                } else {
                                    ClipboardManager clipboard = (ClipboardManager) mContext
                                            .getSystemService(Context.CLIPBOARD_SERVICE);
                                    clipboard.getText();// 设置Clipboard 的内容
                                    if (clipboard.hasText()) {
                                        clipboard.getText();
                                    }
                                }

                                Toast.makeText(mContext, IdHelper.getString(mContext, "jmui_copy_toast"),
                                        Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_forward_msg_btn")) {
                            mDialog.dismiss();
                        } else {
                            mConv.deleteMessage(msg.getId());
                            mChatAdapter.removeMessage(position);
                            mDialog.dismiss();
                        }
                    }
                };
                boolean hide = msg.getContentType() == ContentType.voice;
                mDialog = DialogCreator.createLongPressMessageDialog(mContext, name, hide, listener);
                mDialog.show();
                mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            } else {
                String name = msg.getFromUser().getNickname();
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (v.getId() == IdHelper.getViewID(mContext, "jmui_delete_msg_btn")) {
                            mConv.deleteMessage(msg.getId());
                            mChatAdapter.removeMessage(position);
                            mDialog.dismiss();
                        } else if (v.getId() == IdHelper.getViewID(mContext, "jmui_forward_msg_btn")) {
                            mDialog.dismiss();
                        }
                    }
                };
                mDialog = DialogCreator.createLongPressMessageDialog(mContext, name, true, listener);
                mDialog.show();
                mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
    };

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (oldh - h > 300) {
            mShowSoftInput = true;
            if (SharePreferenceManager.getCachedWritableFlag()) {
                SharePreferenceManager.setCachedKeyboardHeight(oldh - h);
                SharePreferenceManager.setCachedWritableFlag(false);
            }

            mChatView.setMoreMenuHeight();
        } else {
            mShowSoftInput = false;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (view.getId() == IdHelper.getViewID(mContext, "jmui_chat_input_et")) {
                    if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE && !mShowSoftInput) {
                        showSoftInputAndDismissMenu();
                        return false;
                    } else {
                        return false;
                    }
                }
                if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
                    mChatView.dismissMoreMenu();
                } else if (mShowSoftInput) {
                    View v = getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        mShowSoftInput = false;
                    }
                }
                break;
        }
        return false;
    }
}
