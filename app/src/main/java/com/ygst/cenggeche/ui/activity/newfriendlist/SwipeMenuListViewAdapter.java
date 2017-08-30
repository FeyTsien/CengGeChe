package com.ygst.cenggeche.ui.activity.newfriendlist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NewFriendBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class SwipeMenuListViewAdapter extends BaseAdapter {

    List<NewFriendBean> mListNewFriendBean;
    Context mContext;

    public SwipeMenuListViewAdapter(Context context, List<NewFriendBean> list) {
        mContext = context;
        this.mListNewFriendBean = list;
    }

    @Override
    public int getCount() {
        return mListNewFriendBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mListNewFriendBean.get(position);
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
        NewFriendBean newFriend = mListNewFriendBean.get(position);
        return convertView;
    }

    class ViewHolder {

        //头像
        ImageView mIVavatar;
        //性别
        ImageView mIVgender;
        //目标用户名称
        TextView mTVtargetName;
        //申请理由
        TextView mTVlatestMessage;
        //同意
        Button mBtnYes;
        //拒绝
        Button mBtnNo;


        public ViewHolder(View view) {
            mIVavatar = (ImageView) view.findViewById(R.id.iv_avatar);
            mIVgender = (ImageView) view.findViewById(R.id.iv_gender);
            mTVtargetName = (TextView) view.findViewById(R.id.tv_target_name);
            mTVlatestMessage = (TextView) view.findViewById(R.id.tv_latest_message);
            mBtnYes = (Button) view.findViewById(R.id.btn_yes);
            mBtnNo = (Button) view.findViewById(R.id.btn_no);
            view.setTag(this);
        }
    }
}
