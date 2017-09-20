package com.ygst.cenggeche.ui.activity.travelinfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NowTravelInfoBean;

import java.util.ArrayList;
import java.util.List;

import cn.jmessage.android.uikit.chatting.CircleImageView;


/**
 * Created by Administrator on 2017/8/25.
 */

public class TravelInfoAdapter extends BaseAdapter {

    private List<NowTravelInfoBean.InfoBean> mList;
    private  Context context;
    private OnItemClickListener mOnItemClickListener;

    public TravelInfoAdapter(Context context,List<NowTravelInfoBean.InfoBean> list){
        this.context=context;
        this.mList=list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.travelinfo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mTvReadyAccept = (TextView) convertView.findViewById(R.id.tv_ready_accept);
            viewHolder.mLlT = (LinearLayout) convertView.findViewById(R.id.ll_t);
            viewHolder.mRlAvatar = (RelativeLayout) convertView.findViewById(R.id.rl_avatar);
            viewHolder.mUserSmallicon = (CircleImageView) convertView.findViewById(R.id.user_smallicon);
            viewHolder.mIvGender = (ImageView) convertView.findViewById(R.id.iv_gender);
            viewHolder.mTvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolder.mTvUserage = (TextView) convertView.findViewById(R.id.tv_userage);
            viewHolder.mBtnIgnore = (Button) convertView.findViewById(R.id.btn_ignore);
            viewHolder.mBtnAccept = (Button) convertView.findViewById(R.id.btn_accept);
            viewHolder.mTvStartLocation = (TextView) convertView.findViewById(R.id.tv_start_location);
            viewHolder.mTvEndLocation = (TextView) convertView.findViewById(R.id.tv_end_location);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //忽略点击事件
        viewHolder.mBtnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemDeleteClick(v,position);

            }
        });
        //接受点击事件
        viewHolder.mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemAcceptClick(v,position);
            }
        });


        viewHolder.mTvNickname.setText(mList.get(position).getNickname());
        Glide.with(context).load(mList.get(position).getUserPic()).into(viewHolder.mUserSmallicon);
        viewHolder.mIvGender.setImageResource(R.mipmap.icon_girl);
        viewHolder.mTvUserage.setText(mList.get(position).getDeparTime());
        viewHolder.mTvStartLocation.setText(mList.get(position).getStartAddr());
        viewHolder.mTvEndLocation.setText(mList.get(position).getEndAddr());
        return convertView;
    }


    //View复用
    public final class ViewHolder {


        TextView mTvReadyAccept;
        LinearLayout mLlT;
        RelativeLayout mRlAvatar;
        ImageView mIvGender;
        ImageView mUserSmallicon;
        TextView mTvNickname;
        TextView mTvUserage;
        Button mBtnIgnore;
        Button mBtnAccept;
        TextView mTvStartLocation;
        TextView mTvEndLocation;




    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
        void onItemDeleteClick(View view, int postion);
        void onItemAcceptClick(View view, int postion);

    }

}
