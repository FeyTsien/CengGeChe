package com.ygst.cenggeche.ui.fragment.message;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.widget.ColorGenerator;
import com.ygst.cenggeche.ui.widget.TextDrawable;

import java.util.List;

import cn.jmessage.android.uikit.chatting.utils.TimeFormat;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SwipeMenuListViewAdapter extends BaseAdapter {

    List<Conversation> mListConversation;
    Context mContext;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    public SwipeMenuListViewAdapter(Context context, List<Conversation> list) {
        mContext = context;
        this.mListConversation = list;
    }

    @Override
    public int getCount() {
        return mListConversation.size();
    }

    @Override
    public Object getItem(int position) {
        return mListConversation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(),
                    R.layout.item_list_conversation, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Conversation convItem = mListConversation.get(position);
        setItemView(holder, convItem);
//        if (convItem.getType().equals(ConversationType.single)) {
//
//        } else {
//            GroupInfo groupInfo = (GroupInfo) convItem.getTargetInfo();
//        }
        return convertView;
    }

    /**
     * 给单行消息填充数据
     *
     * @param holder
     * @param convItem
     */
    private void setItemView(final ViewHolder holder, final Conversation convItem) {
        Message lastMsg = convItem.getLatestMessage();
        String contentStr = "";
        if (lastMsg != null) {
            TimeFormat timeFormat = new TimeFormat(mContext, lastMsg.getCreateTime());
            //最后会话时间
            holder.mTVlastTime.setText(timeFormat.getTime());
            switch (lastMsg.getContentType()) {
                case image:
                    contentStr = mContext.getString(R.string.type_picture);
                    break;
                case voice:
                    contentStr = mContext.getString(R.string.type_voice);
                    break;
                case location:
                    contentStr = mContext.getString(R.string.type_location);
                    break;
                case file:
                    String extra = lastMsg.getContent().getStringExtra("video");
                    if (!TextUtils.isEmpty(extra)) {
                        contentStr = mContext.getString(R.string.type_smallvideo);
                    } else {
                        contentStr = mContext.getString(R.string.type_file);
                    }
                    break;
                case video:
                    contentStr = mContext.getString(R.string.type_video);
                    break;
                case eventNotification:
                    contentStr = mContext.getString(R.string.group_notification);
                    break;
                case custom:
                    CustomContent customContent = (CustomContent) lastMsg.getContent();
                    Boolean isBlackListHint = customContent.getBooleanValue("blackList");
                    if (isBlackListHint != null && isBlackListHint) {
                        contentStr = mContext.getString(R.string.jmui_server_803008);
                    } else {
                        contentStr = mContext.getString(R.string.type_custom);
                    }
                    break;
                case prompt:
                    contentStr = ((PromptContent) lastMsg.getContent()).getPromptText();
                    break;
                default:
                    contentStr = ((TextContent) lastMsg.getContent()).getText();
            }
        }

        if (convItem.getType().equals(ConversationType.single)) {
            final UserInfo mUserInfo = (UserInfo) convItem.getTargetInfo();
            if (mUserInfo != null) {
                if (mUserInfo.getGender() != null) {
                    //显示性别
                    if (mUserInfo.getGender().equals(UserInfo.Gender.female)) {
                        holder.mIVgender.setImageResource(R.mipmap.icon_girl);
                    } else if (mUserInfo.getGender().equals(UserInfo.Gender.male)) {
                        holder.mIVgender.setImageResource(R.mipmap.icon_boy);
                    } else {
                    }
                }

                //显示头像
                mUserInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int status, String desc, Bitmap bitmap) {
                        String name = "";
                        if (!TextUtils.isEmpty(mUserInfo.getNotename())) {
                            name = mUserInfo.getNotename();
                        } else if (!TextUtils.isEmpty(mUserInfo.getNickname())) {
                            name = mUserInfo.getNickname();
                        } else {
                            name = mUserInfo.getUserName();
                        }
                        //会话的名称
                        holder.mTVtargetName.setText(name);

                        if (status == 0) {
                            holder.mIVavatar.setImageBitmap(bitmap);
                        } else {
                            //显示性别
                            if (mUserInfo.getGender().equals(UserInfo.Gender.female)) {
                                holder.mIVavatar.setImageResource(R.mipmap.icon_avatar_girl);
                            } else if (mUserInfo.getGender().equals(UserInfo.Gender.male)) {
                                holder.mIVavatar.setImageResource(R.mipmap.icon_avatar_boy);
                            } else {
                                holder.mIVavatar.setImageResource(R.mipmap.icon_my_avatar);
                            }
                        }
                    }
                });
            } else {
                holder.mIVavatar.setImageResource(R.mipmap.icon_my_avatar);
            }
        } else {
            GroupInfo mGroupInfo = (GroupInfo) convItem.getTargetInfo();
            //会话的名称
            holder.mTVtargetName.setText(mGroupInfo.getGroupName());
            holder.mIVavatar.setImageResource(R.drawable.group);
        }

        //最新对话内容
        holder.mTVlatestMessage.setText(contentStr);
        //单个会话未读消息数
        if (convItem.getUnReadMsgCnt() > 0) {
            holder.mTVunreadCount.setVisibility(View.VISIBLE);
            holder.mTVunreadCount.setText("" + convItem.getUnReadMsgCnt());
        } else {
            holder.mTVunreadCount.setVisibility(View.GONE);
        }
    }

    class ViewHolder {

        //头像
        ImageView mIVavatar;
        //性别
        ImageView mIVgender;
        //目标用户名称
        TextView mTVtargetName;
        //最新消息
        TextView mTVlatestMessage;
        //最后会话时间
        TextView mTVlastTime;

        //未读消息数
        TextView mTVunreadCount;

        public ViewHolder(View view) {
            mIVavatar = (ImageView) view.findViewById(R.id.iv_avatar);
            mIVgender = (ImageView) view.findViewById(R.id.iv_gender);
            mTVtargetName = (TextView) view.findViewById(R.id.tv_target_name);
            mTVlatestMessage = (TextView) view.findViewById(R.id.tv_latest_message);
            mTVlastTime = (TextView) view.findViewById(R.id.tv_last_time);
            mTVunreadCount = (TextView) view.findViewById(R.id.tv_unread_count);
            view.setTag(this);
        }
    }
}
