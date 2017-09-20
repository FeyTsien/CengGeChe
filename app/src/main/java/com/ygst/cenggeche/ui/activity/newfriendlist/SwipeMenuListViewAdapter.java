package com.ygst.cenggeche.ui.activity.newfriendlist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.ApplyBean;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.ui.widget.MyTextDrawable;
import com.ygst.cenggeche.ui.widget.TextDrawable;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SwipeMenuListViewAdapter extends BaseAdapter {

    NewFriendListActivity activity;
    List<ApplyBean.DataBean> mListDataBean;
    Context mContext;

    public SwipeMenuListViewAdapter(Context context, List<ApplyBean.DataBean> list) {
        mContext = context;
        this.mListDataBean = list;
        this.activity = (NewFriendListActivity) context;
    }

    @Override
    public int getCount() {
        return mListDataBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDataBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_list_newfriend, null);
            new ViewHolder(convertView);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final ApplyBean.DataBean newFriend = mListDataBean.get(position);
        String applyName = "";
        if (newFriend != null) {
            if(!TextUtils.isEmpty(newFriend.getNickname())){
                applyName =newFriend.getNickname();
            }else{
                applyName =newFriend.getFusername();
            }
            holder.mTvTargetName.setText(applyName);


            //头像
            TextDrawable drawable = MyTextDrawable.getTextDrawable(applyName);
            Glide.with(mContext)
                    .load(newFriend.getUserPic())
                    .placeholder(drawable)
                    .into(holder.mIvAvatar);
//
//            if(!TextUtils.isEmpty(newFriend.getFromAvatar())){
//                Uri uri = Uri.parse(newFriend.getFromAvatar());
//                holder.mIvAvatar.setImageURI(uri);
//            }else {
//                //自定义的文字图片
//                TextDrawable drawable = MyTextDrawable.getTextDrawable(applyName);
//                holder.mIvAvatar.setImageDrawable(drawable);
//            }

            holder.mTvLatestMessage.setText(newFriend.getApplyInfo());
            if (newFriend.getIsAgree() == 1) {
                holder.mTvIsAgree.setText("已拒绝");
                holder.mBtnYes.setVisibility(View.GONE);
//                holder.mBtnNo.setVisibility(View.GONE);
            } else if (newFriend.getIsAgree() == 2) {
                holder.mTvIsAgree.setText("已同意");
                holder.mBtnYes.setVisibility(View.GONE);
//                holder.mBtnNo.setVisibility(View.GONE);
            } else {
                holder.mTvIsAgree.setVisibility(View.GONE);
                holder.mBtnYes.setVisibility(View.VISIBLE);
//                holder.mBtnNo.setVisibility(View.VISIBLE);
            }
        }

        holder.mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showYesAgreeDialog(newFriend);
            }
        });
        holder.mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showNoAgreeDialog(newFriend);
            }
        });


        return convertView;
    }

    class ViewHolder {

        //头像
        CircleImageView mIvAvatar;
        //性别
        ImageView mIvGender;
        //目标用户名称
        TextView mTvTargetName;
        //申请理由
        TextView mTvLatestMessage;
        //同意
        TextView mBtnYes;
        //拒绝
        Button mBtnNo;
        //已同意或拒绝的状态
        TextView mTvIsAgree;


        public ViewHolder(View view) {
            mIvAvatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
            mIvGender = (ImageView) view.findViewById(R.id.iv_gender);
            mTvTargetName = (TextView) view.findViewById(R.id.tv_target_name);
            mTvLatestMessage = (TextView) view.findViewById(R.id.tv_reason);
            mBtnYes = (TextView) view.findViewById(R.id.btn_yes);
            mBtnNo = (Button) view.findViewById(R.id.btn_no);
            mTvIsAgree = (TextView) view.findViewById(R.id.tv_isagree);
            view.setTag(this);
        }
    }
}
