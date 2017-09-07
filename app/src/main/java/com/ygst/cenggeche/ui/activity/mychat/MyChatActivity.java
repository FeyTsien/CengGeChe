package com.ygst.cenggeche.ui.activity.mychat;


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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.jarek.imageselect.activity.FolderListActivity;
import com.jarek.imageselect.bean.ImageFolderBean;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionSelectedListener;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.addfriend.AddFriendActivity;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.TextViewUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jmessage.android.uikit.chatting.DropDownListView;
import cn.jmessage.android.uikit.chatting.utils.BitmapLoader;
import cn.jmessage.android.uikit.chatting.utils.DialogCreator;
import cn.jmessage.android.uikit.chatting.utils.Event;
import cn.jmessage.android.uikit.chatting.utils.FileHelper;
import cn.jmessage.android.uikit.chatting.utils.IdHelper;
import cn.jpush.im.android.api.JMessageClient;
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
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyChatActivity extends MVPBaseActivity<MyChatContract.View, MyChatPresenter> implements MyChatContract.View, IEmotionSelectedListener, View.OnClickListener, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private String TAG = "MyChatActivity";

    private static final String MEMBERS_COUNT = "membersCount";
    private static final String GROUP_NAME = "groupName";
    private static final String MsgIDs = "msgIDs";
    private static final String TARGET_APP_KEY = "";
    //跳转到加好友页（对方先发申请好友，当前用户再点默认同意）
    private static final int IS_AGREE_KEY = 1105;
    private static final int REQUEST_CODE_TAKE_PHOTO = 4;
    private static final int RESULT_CODE_SELECT_PICTURE = 8;
    private static final int REFRESH_LAST_PAGE = 0x1023;
    private static final int REFRESH_GROUP_NAME = 0x1025;
    private static final int REFRESH_GROUP_NUM = 0x1026;


    //录音权限
    String[] permission_record_audio = {"android.permission.RECORD_AUDIO"};
    private static final int REQUEST_PERMISSION_RECORD_AUDIO = 1001;
    //读取存储器和相机权限
    String[] permission_read_camera = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
    private static final int REQUEST_PERMISSION_READ_CAMERA = 1002;

    private Context mContext;
    private boolean mIsSingle = true;
    private boolean mShowSoftInput = false;
    private String targetUsername;
    private String targetAppKey;
    private boolean isFriend;
    private long mGroupId;
    private String mGroupName;
    private GroupInfo mGroupInfo;
    private Conversation mConversation;
    private UserInfo mUserInfo;

    private MsgListAdapter mChatAdapter;
    private Dialog mDialog;
    private MyReceiver mReceiver;

    private String mPhotoPath;

    private final UIHandler mUIHandler = new UIHandler(this);
    InputMethodManager mImm;

    //标题名称
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    //标题栏左侧返回按钮
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    //标题栏右侧按钮
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    //展示内容栏
    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    //展示聊天内容的listView
    @BindView(R.id.list_view_chat)
    DropDownListView mChatListView;
    //切换成发语音模式
    @BindView(R.id.ivAudio)
    ImageView mIvAudio;
    //按住录音
    @BindView(R.id.record_voice_button)
    RecordVoiceButton mVoiceBtn;
    //消息输入框
    @BindView(R.id.etContent)
    EmojiconEditText mEtContent;
    //点击展示表情包按钮
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;
    //小加号，点击可以展示更多
    @BindView(R.id.ivMore)
    ImageView mIvMore;
    //发送消息按钮
    @BindView(R.id.btnSend)
    Button mBtnSend;
    //更多功能栏（包含着表情包和其他功能）
    @BindView(R.id.flEmotionView)
    FrameLayout mFlEmotionView;
    //Emoji表情包展示栏
    @BindView(R.id.emojicons)
    FrameLayout mFlEmojicons;
    //表情包展示栏
    @BindView(R.id.elEmotion)
    EmotionLayout mElEmotion;
    //其他功能展示栏（相册，相机，定位等）
    @BindView(R.id.llMore)
    LinearLayout mLlMore;
    //相册
    @BindView(R.id.ivAlbum)
    ImageView mIvAlbum;
    //相机
    @BindView(R.id.ivShot)
    ImageView mIvShot;
    //定位
    @BindView(R.id.ivLocation)
    ImageView mIvLocation;

    //表情键盘协调工具
    private EmotionKeyboard mEmotionKeyboard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_session;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        //如果加了这段话，可以使用普通控件默认兼容Emoji（但是Emoji列表里的表情变小了）
//        Emojiconize.activity(this).go();
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        JMessageClient.registerEventReceiver(this);
        mContext = this;

        initReceiver();
        initView();
        initListener();
        getNotificationMessage();
    }

    /**
     * 点击通知跳转到聊天页显示消息
     */
    private void getNotificationMessage() {
        Message msg = (Message) getIntent().getSerializableExtra(JMessageUtils.MESSAGE);
        if (msg != null) {
            refreshMessages(msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtContent.clearFocus();
        if (!RecordVoiceButton.mIsPressed) {
            mVoiceBtn.dismissDialog();
        }
        if (!mIsSingle) {
            if (mGroupId != 0) {
                JMessageClient.enterGroupConversation(mGroupId);
            }
        } else if (null != targetUsername) {
            String appKey = getIntent().getStringExtra(TARGET_APP_KEY);
            JMessageClient.enterSingleConversation(targetUsername, appKey);
        }
        mChatAdapter.initMediaPlayer();
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        mChatAdapter.releaseMediaPlayer();
        mUIHandler.removeCallbacksAndMessages(null);
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        RecordVoiceButton.mIsPressed = false;
        JMessageClient.exitConversation();
        Log.i(TAG, "[Life cycle] - onPause");
        super.onPause();
    }

    public void initView() {
//        mElEmotion.attachEditText(mEtContent);
        initEmotionKeyboard();

        //接受到传过来的Username和appkey
        Intent intent = getIntent();
        targetUsername = intent.getStringExtra(JMessageUtils.TARGET_USERNAME);
        targetAppKey = intent.getStringExtra(JMessageUtils.TARGET_APP_KEY);
        isFriend = intent.getBooleanExtra(JMessageUtils.IS_FRIEND,false);
        if (!TextUtils.isEmpty(targetUsername)) {
            //==========单聊
            mIsSingle = true;
            mConversation = JMessageClient.getSingleConversation(targetUsername, targetAppKey);
            if (mConversation == null) {
                mConversation = Conversation.createSingleConversation(targetUsername, targetAppKey);
            }
            mUserInfo = (UserInfo) mConversation.getTargetInfo();
            if (!isFriend) {
                //不是好友则显示添加好友按钮
                mIvRight.setVisibility(View.VISIBLE);
            } else {
                //是好友则隐藏添加好友按钮
                mIvRight.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mUserInfo.getNickname())) {
                mTvTitle.setText(mUserInfo.getNickname());
            } else {
                mTvTitle.setText(mUserInfo.getUserName());
            }
            mChatAdapter = new MsgListAdapter(this, targetUsername, targetAppKey, longClickListener);
        } else {
            //==========群聊
            mIsSingle = false;
            mGroupId = intent.getLongExtra(JMessageUtils.GROUP_ID_KEY, 0);
            mConversation = JMessageClient.getGroupConversation(mGroupId);
            if (mConversation != null) {
                mTvTitle.setText(mConversation.getTitle());
            } else {
                mConversation = Conversation.createGroupConversation(mGroupId);
                mTvTitle.setText(mConversation.getTitle());
            }
            mChatAdapter = new MsgListAdapter(mContext, mGroupId, longClickListener);
        }

        mChatListView.setAdapter(mChatAdapter);
//        //单条消息点击事件
//        mChatListView.setOnItemClickListener(mOnItemClickListener);
        mChatAdapter.initMediaPlayer();
        //下拉刷新
        mChatListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                mUIHandler.sendEmptyMessageDelayed(REFRESH_LAST_PAGE, 1000);
            }
        });

        //向对方发送是否同意好友申请
        sendIsAgreeMsg(intent);
        //滑到底部
        setToBottom();
    }


    /**
     * 向对方发送是否同意好友申请
     */
    private void sendIsAgreeMsg(Intent intent) {
        String isAgree = intent.getStringExtra(JMessageUtils.IS_AGREE_KEY);
        LogUtils.i(TAG, "isAgree:" + isAgree);
        if (!TextUtils.isEmpty(isAgree)) {
            if (isAgree.equals(JMessageUtils.YES_AGREE)) {
                String msgContent = "我已接受你的好友申请";
                TextContent content = new TextContent(msgContent);
                final Message msg = mConversation.createSendMessage(content);
//            final Message msg2 = mConversation.createSendCustomMessage(map);
                mChatAdapter.addMsgToList(msg);
                JMessageClient.sendMessage(msg);
            } else if (isAgree.equals(JMessageUtils.NO_AGREE)) {
                String msgContent = "我已拒绝你的好友申请";
                TextContent content = new TextContent(msgContent);
                final Message msg = mConversation.createSendMessage(content);
                mChatAdapter.addMsgToList(msg);
                JMessageClient.sendMessage(msg);
            }
        }
    }

    /**
     * 用于显示Emoji表情的展示栏的Fragment
     *
     * @param useSystemDefault
     */
    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    /**
     * Emoji表情包的重写方法
     */
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEtContent, emojicon);
    }

    /**
     * Emoji表情包的重写方法
     */
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEtContent);
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


    /**
     * 初始化表情键盘协调工具
     */
    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
        mEmotionKeyboard.bindToEmotionButton(mIvEmo, mIvMore);
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                switch (view.getId()) {
                    case R.id.ivEmo:
                        setToBottom();
//                        if (!mElEmotion.isShown()) {
                        if (!mFlEmojicons.isShown()) {
                            if (mLlMore.isShown()) {
                                showEmotionLayout();
                                hideMoreLayout();
                                hideAudioButton();
                                return true;
                            }
                        } else if (mFlEmojicons.isShown() && !mLlMore.isShown()) {
                            mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
                            return false;
                        }
                        showEmotionLayout();
                        hideMoreLayout();
                        hideAudioButton();
                        break;
                    case R.id.ivMore:
                        setToBottom();
                        if (!mLlMore.isShown()) {
//                            if (mElEmotion.isShown()) {
                            if (mFlEmojicons.isShown()) {
                                showMoreLayout();
                                hideEmotionLayout();
                                hideAudioButton();
                                return true;
                            }
                        }
                        showMoreLayout();
                        hideEmotionLayout();
                        hideAudioButton();
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 监听初始化
     */
    public void initListener() {
        setListeners();
        setOnTouchListener();
//        mElEmotion.setEmotionSelectedListener(this);
//        mElEmotion.setEmotionAddVisiable(true);
//        mElEmotion.setEmotionSettingVisiable(true);
//        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
//            @Override
//            public void onEmotionAddClick(View view) {
//                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onEmotionSettingClick(View view) {
//                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
//            }
//        });

        mChatListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.i(TAG, "mChatListView触摸了内容区");
                        closeBottomAndKeyboard();
//                        if (mElEmotion.isShown() || mLlMore.isShown()) {
//                            mEmotionKeyboard.interceptBackPress();
//                        }
                        break;
                }
                return false;
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvMore.setVisibility(View.GONE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    mIvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 触摸监听
     */
    private void setOnTouchListener() {

    }

    /**
     * 单点击监听
     */
    private void setListeners() {
        mIvBack.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mIvAudio.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        mIvAlbum.setOnClickListener(this);
        mIvShot.setOnClickListener(this);
        mIvLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back: //返回按钮
                onBackPressed();
                break;
            case R.id.iv_right://标题栏右边按钮
                Intent intent = new Intent();
                intent.putExtra(JMessageUtils.TARGET_USERNAME, targetUsername);
                intent.putExtra(JMessageUtils.TARGET_APP_KEY, targetAppKey);
                intent.setClass(this, AddFriendActivity.class);
                startActivityForResult(intent, IS_AGREE_KEY);
                break;
            case R.id.btnSend://发送文字消息
                String msgContent = TextViewUtils.getText(mEtContent);
                if (msgContent.equals("")) {
                    return;
                }
                TextContent content = new TextContent(msgContent);
                final Message msg = mConversation.createSendMessage(content);
                mChatAdapter.addMsgToList(msg);
                JMessageClient.sendMessage(msg);
                mEtContent.setText("");
                setToBottom();
                break;
            case R.id.ivAudio://切换语言
                // 开启权限
                if (ContextCompat.checkSelfPermission(this, permission_record_audio[0]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permission_record_audio, REQUEST_PERMISSION_RECORD_AUDIO);
                } else {
                    if (mVoiceBtn.isShown()) {
                        hideAudioButton();
                        mEtContent.requestFocus();
                        if (mEmotionKeyboard != null) {
                            mEmotionKeyboard.showSoftInput();
                        }
                    } else {
                        showAudioButton();
                        hideEmotionLayout();
                        hideMoreLayout();
                    }
                }
                break;
            case R.id.ivAlbum://去选择图片
                //开启读取文件，相机权限
                if (ContextCompat.checkSelfPermission(this, permission_read_camera[0]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permission_read_camera, REQUEST_PERMISSION_READ_CAMERA);
                } else {
                    onMultiClick();
                }
                break;
            case R.id.ivShot: //去拍摄照片
                //开启读取文件，相机权限
                if (ContextCompat.checkSelfPermission(this, permission_read_camera[0]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permission_read_camera, REQUEST_PERMISSION_READ_CAMERA);
                } else {
                    takePhoto();
                }
                break;
            case R.id.ivLocation:
                ToastUtil.show(this, "功能开发中...");
                break;
        }
    }

    public String getChatInput() {
        return mEtContent.getText().toString();
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
                case REQUEST_CODE_TAKE_PHOTO:
                    try {
                        handleImgRefresh(mPhotoPath);
                    } catch (NullPointerException e) {
                        Log.i(TAG, "onActivityResult unexpected result");
                    }
                    break;
                case IS_AGREE_KEY:
                    //是好友则隐藏添加好友按钮
                    mIvRight.setVisibility(View.GONE);
                    String msgContent = "我已接受你的好友申请";
                    TextContent content = new TextContent(msgContent);
                    final Message msg = mConversation.createSendMessage(content);
//            final Message msg2 = mConversation.createSendCustomMessage(map);
                    mChatAdapter.addMsgToList(msg);
                    JMessageClient.sendMessage(msg);
                    //滑到底部
                    setToBottom();
                    break;

            }
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
                    Message msg = mConversation.createSendMessage(imageContent);
                    Intent intent = new Intent();
                    intent.putExtra(MsgIDs, new int[]{msg.getId()});
                    mChatAdapter.setSendImg(intent.getIntArrayExtra(MsgIDs));
                }
            }
        });
    }


    private void showAudioButton() {
        mVoiceBtn.setVisibility(View.VISIBLE);
        mVoiceBtn.initConv(mConversation, mChatAdapter, this);
        mEtContent.setVisibility(View.GONE);
        mIvAudio.setImageResource(R.mipmap.ic_cheat_keyboard);

        if (mFlEmotionView.isShown()) {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.interceptBackPress();
            }
        } else {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.hideSoftInput();
            }
        }
    }

    private void hideAudioButton() {
        mVoiceBtn.setVisibility(View.GONE);
        mEtContent.setVisibility(View.VISIBLE);
        mIvAudio.setImageResource(R.mipmap.ic_cheat_voice);
    }

    private void showEmotionLayout() {
        setEmojiconFragment(false);
        //Emoji表情包栏
        mFlEmojicons.setVisibility(View.VISIBLE);
//        //自定义表情包栏
//        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        //Emoji表情包栏
        mFlEmojicons.setVisibility(View.GONE);
//        //自定义表情包栏
//        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
    }

    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }

    private void closeBottomAndKeyboard() {
        //Emoji表情包栏
        mFlEmojicons.setVisibility(View.GONE);
//        //自定义表情包栏
//        mElEmotion.setVisibility(View.GONE);
        mLlMore.setVisibility(View.GONE);
        dismissSoftInput();
        if (mEmotionKeyboard != null) {
            LogUtils.i(TAG, "mEmotionKeyboard");
            mEmotionKeyboard.interceptBackPress();
        }
    }

    @Override
    public void onBackPressed() {
        if (RecordVoiceButton.mIsPressed) {
            mVoiceBtn.dismissDialog();
            mVoiceBtn.releaseRecorder();
            RecordVoiceButton.mIsPressed = false;
        }

//        if (mElEmotion.isShown() || mLlMore.isShown()) {
        if (mFlEmojicons.isShown() || mLlMore.isShown()) {
            mEmotionKeyboard.interceptBackPress();
        } else {
            mConversation.resetUnreadCount();
            dismissSoftInput();
            JMessageClient.exitConversation();
            //发送保存为草稿事件到会话列表界面,作为UIKit使用可以去掉
            if (mIsSingle) {
                EventBus.getDefault().post(new Event.DraftEvent(targetUsername, targetAppKey, getChatInput()));
            } else {
                EventBus.getDefault().post(new Event.DraftEvent(mGroupId, getChatInput()));
            }
            super.onBackPressed();
        }
    }


    /**
     * 收起虚拟键盘
     */
    private void dismissSoftInput() {
        if (mShowSoftInput) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(mEtContent.getWindowToken(), 0);
                mShowSoftInput = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEmojiSelected(String key) {
        Log.e("CSDN_LQR", "onEmojiSelected : " + key);
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
        //返回点击的表情路径
        Toast.makeText(getApplicationContext(), stickerBitmapPath, Toast.LENGTH_SHORT).show();
        Log.e("CSDN_LQR", "stickerBitmapPath : " + stickerBitmapPath);
        handleImgRefresh(stickerBitmapPath);
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
                                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
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
                            mConversation.deleteMessage(msg.getId());
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
                            mConversation.deleteMessage(msg.getId());
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

//    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        }
//    };


    /**
     * 滑到底部
     */
    public void setToBottom() {
        mChatListView.clearFocus();
        mChatListView.post(new Runnable() {
            @Override
            public void run() {
                mChatListView.setSelection(mChatListView.getAdapter().getCount() - 1);
            }
        });
    }

    private static class UIHandler extends Handler {
        private final WeakReference<MyChatActivity> mActivity;

        public UIHandler(MyChatActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            MyChatActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_LAST_PAGE:
                        activity.mChatAdapter.dropDownToRefresh();
                        activity.mChatListView.onDropDownComplete();
                        if (activity.mChatAdapter.isHasLastPage()) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                activity.mChatListView.setSelectionFromTop(activity.mChatAdapter.getOffset(),
                                        activity.mChatListView.getHeaderHeight());
                            } else {
                                activity.mChatListView.setSelection(activity.mChatAdapter
                                        .getOffset());
                            }
                            activity.mChatAdapter.refreshStartPosition();
                        } else {
                            activity.mChatListView.setSelection(0);
                        }
                        activity.mChatListView.setOffset(activity.mChatAdapter.getOffset());
                        break;
                }
            }
        }
    }

    /**
     * 接收消息类事件
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        final Message msg = event.getMessage();
        refreshMessages(msg);

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
                        break;
                    case group_member_removed:
                        //删除群成员事件
                        break;
                    case group_member_exit:
                        refreshGroupNum();
                        break;
                }
            }
        }
    }

    /**
     * 刷新单聊消息
     */
    private void refreshMessages(final Message msg) {
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
                    if (mIsSingle && targetId.equals(targetUsername) && appKey.equals(targetAppKey)) {
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

}
