package com.ygst.cenggeche.ui.activity.newfriendlist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.ApplyBean;

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
        activity = new NewFriendListActivity();
        if (convertView == null) {
            convertView = View.inflate(mContext.getApplicationContext(), R.layout.item_list_newfriend, null);
            new ViewHolder(convertView);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final ApplyBean.DataBean newFriend = mListDataBean.get(position);

        if (newFriend != null) {
            holder.mTvTargetName.setText(newFriend.getFusername());
            holder.mTvLatestMessage.setText(newFriend.getApplyInfo());
            if (newFriend.getIsAgree() == 1) {
                holder.mTvIsAgree.setText("已同意");
            } else if (newFriend.getIsAgree() == 2) {
                holder.mTvIsAgree.setText("已拒绝");
            } else {
                holder.mBtnYes.setVisibility(View.VISIBLE);
                holder.mBtnNo.setVisibility(View.VISIBLE);
            }
        }

        holder.mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showNoAgreeDialog(newFriend);
            }
        });
        holder.mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return convertView;
    }

    class ViewHolder {

        //头像
        ImageView mIvAvatar;
        //性别
        ImageView mIvGender;
        //目标用户名称
        TextView mTvTargetName;
        //申请理由
        TextView mTvLatestMessage;
        //同意
        Button mBtnYes;
        //拒绝
        Button mBtnNo;
        //已同意或拒绝的状态
        TextView mTvIsAgree;


        public ViewHolder(View view) {
            mIvAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            mIvGender = (ImageView) view.findViewById(R.id.iv_gender);
            mTvTargetName = (TextView) view.findViewById(R.id.tv_target_name);
            mTvLatestMessage = (TextView) view.findViewById(R.id.tv_reason);
            mBtnYes = (Button) view.findViewById(R.id.btn_yes);
            mBtnNo = (Button) view.findViewById(R.id.btn_no);
            mTvIsAgree = (TextView) view.findViewById(R.id.tv_isagree);
            view.setTag(this);
        }
    }
}
