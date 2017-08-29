package com.ygst.cenggeche.ui.activity.mychat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.TextViewUtils;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyChatActivity extends MVPBaseActivity<MyChatContract.View, MyChatPresenter> implements MyChatContract.View,IEmotionSelectedListener {

    private String targetUserName;
    private String targetAppKey;

    @BindView(R.id.llContent)
    LinearLayout mLlContent;

    @BindView(R.id.ivAudio)
    ImageView mIvAudio;
    @BindView(R.id.btnAudio)
    Button mBtnAudio;
    @BindView(R.id.etContent)
    EditText mEtContent;
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;
    @BindView(R.id.ivMore)
    ImageView mIvMore;
    @BindView(R.id.btnSend)
    Button mBtnSend;

    @BindView(R.id.flEmotionView)
    FrameLayout mFlEmotionView;
    @BindView(R.id.elEmotion)
    EmotionLayout mElEmotion;
    @BindView(R.id.llMore)
    LinearLayout mLlMore;

    private EmotionKeyboard mEmotionKeyboard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_session;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtContent.clearFocus();
    }

    public void initView() {
        mElEmotion.attachEditText(mEtContent);
        initEmotionKeyboard();
    }

    public void initListener() {
        mElEmotion.setEmotionSelectedListener(this);
        mElEmotion.setEmotionAddVisiable(true);
        mElEmotion.setEmotionSettingVisiable(true);
        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmotionSettingClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });
        mLlContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        closeBottomAndKeyboard();
                        break;
                }
                return false;
            }
        });
        mIvAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnAudio.isShown()) {
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
        //发送文字消息
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  text = TextViewUtils.getText(mEtContent);
                /**
                 * 创建一条单聊文本消息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
                 * 快捷的创建一条消息。其他的情况下推荐使用{@link Conversation#createSendMessage(MessageContent)}
                 * 接口来创建消息
                 *
                 * @param username 聊天对象用户名
                 * @param appKey   聊天对象所属应用的appKey
                 * @param text     文本内容
                 * @return 消息对象
                 */
                Message message = JMessageClient.createSingleTextMessage(targetUserName,targetAppKey,text);
                sendMessage(message);
                Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发送消息
     * @param message
     */
    private void sendMessage(Message message){
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseDesc) {
                if (responseCode == 0) {
                    // 消息发送成功
                } else {
                    // 消息发送失败
                }
            }
        });
        JMessageClient.sendMessage(message);    // 之后再调用发送消息 API
    }

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
                        if (!mElEmotion.isShown()) {
                            if (mLlMore.isShown()) {
                                showEmotionLayout();
                                hideMoreLayout();
                                hideAudioButton();
                                return true;
                            }
                        } else if (mElEmotion.isShown() && !mLlMore.isShown()) {
                            mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
                            return false;
                        }
                        showEmotionLayout();
                        hideMoreLayout();
                        hideAudioButton();
                        break;
                    case R.id.ivMore:
                        if (!mLlMore.isShown()) {
                            if (mElEmotion.isShown()) {
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

    private void showAudioButton() {
        mBtnAudio.setVisibility(View.VISIBLE);
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
        mBtnAudio.setVisibility(View.GONE);
        mEtContent.setVisibility(View.VISIBLE);
        mIvAudio.setImageResource(R.mipmap.ic_cheat_voice);
    }

    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
    }

    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }

    private void closeBottomAndKeyboard() {
        mElEmotion.setVisibility(View.GONE);
        mLlMore.setVisibility(View.GONE);
        if (mEmotionKeyboard != null) {
            mEmotionKeyboard.interceptBackPress();
        }
    }

    @Override
    public void onBackPressed() {
        if (mElEmotion.isShown() || mLlMore.isShown()) {
            mEmotionKeyboard.interceptBackPress();
        } else {
            super.onBackPressed();
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

        /**
         * 创建一条单聊图片信息，此方法是创建message的快捷接口，对于不需要关注会话实例的开发者可以使用此方法
         * 快捷的创建一条消息。其他的情况下推荐使用{@link Conversation#createSendMessage(MessageContent)}
         * 接口来创建消息
         *
         * @param username  聊天对象的用户名
         * @param appKey    聊天对象所属应用的appKey
         * @param imageFile 图片文件
         * @return 消息对象
         * @throws FileNotFoundException
         */

//        try {
//            JMessageClient.createSingleImageMessage(targetUserName, targetAppKey, File imageFile);
//        }catch(FileNotFoundException exception){
//
//        }

    }
    /**
     * 处理发送图片，刷新界面
     */
    private void handleImgRefresh(String path) {
//        Bitmap bitmap = BitmapLoader.getBitmapFromFile(path, 720, 1280);
//        ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
//            @Override
//            public void gotResult(int status, String desc, ImageContent imageContent) {
//                if (status == 0) {
//                    Message msg = mConv.createSendMessage(imageContent);
//                    Intent intent = new Intent();
//                    intent.putExtra(MsgIDs, new int[]{msg.getId()});
//                    mChatAdapter.setSendImg(intent.getIntArrayExtra(MsgIDs));
//                    mChatView.setToBottom();
//                }
//            }
//        });
    }
}
