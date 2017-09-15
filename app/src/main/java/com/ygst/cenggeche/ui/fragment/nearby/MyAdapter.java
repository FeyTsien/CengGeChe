package com.ygst.cenggeche.ui.fragment.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NearByBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private  ArrayList<NearByBean.DataBean> mList;
    private  Context context;
    private String TAG="MyAdapter";
    private OnItemClickListener mOnItemClickListener = null;

    public MyAdapter(Context context,ArrayList<NearByBean.DataBean> list){
        this.context=context;
        this.mList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.nearby_gridview_item, parent, false);
        holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvNickname.setText(mList.get(position).getNickname());
        int gender = mList.get(position).getGender();
        if(gender==1){
            holder.mIvGender.setImageResource(R.mipmap.icon_boy);
        }else if(gender==0){
            holder.mIvGender.setImageResource(R.mipmap.icon_girl);
        }
        Glide.with(context).load(mList.get(position).getUserPic()).placeholder(R.mipmap.default_user_icon).into(holder.mUserSmallicon);
        Glide.with(context).load(mList.get(position).getUserPic()).placeholder(R.mipmap.default_user_icon).into(holder.mIvBigpic);

        //  设置千米数
        String distance = mList.get(position).getDistance();

        if(distance!=null&&!distance.equals("0.00")){
            double pass = Double.parseDouble(distance);
            if(pass<=10){
                holder.mTvDistance.setText("<10米");
            }else if(10<pass&&pass<=1000){
                holder.mTvDistance.setText(pass+"米");
            }else if(1000<=pass){
                double i = pass / 1000 + pass % 1000;
                holder.mTvDistance.setText(i+"米");
            }
        }

        //设置标签
        List<String> tags = mList.get(position).getTags();
        int size = tags.size();
        if(size==0){
            holder.mLllayout.setVisibility(View.GONE);
        }else if(size==1){
            holder.mLabel1.setText(mList.get(position).getTags().get(0));
            holder.mLabel2.setVisibility(View.GONE);
            holder.mLabel3.setVisibility(View.GONE);
        }else if(size==2){
            holder.mLabel1.setText(mList.get(position).getTags().get(0));
            holder.mLabel2.setText(mList.get(position).getTags().get(1));
            holder.mLabel3.setVisibility(View.GONE);
        }else if(size==3){
            holder.mLabel1.setText(mList.get(position).getTags().get(0));
            holder.mLabel2.setText(mList.get(position).getTags().get(1));
            holder.mLabel3.setText(mList.get(position).getTags().get(2));
        }
        holder.mTvUserage.setText(mList.get(position).getAge()+"岁");



        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG,mList.size()+"====");
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvDistance;
        TextView mLabel1,mLabel2,mLabel3;
        ImageView mUserSmallicon,mIvGender,mIvBigpic;
        TextView mTvNickname;
        TextView mTvUserage;
        LinearLayout mLllayout;
        public MyViewHolder(View view) {
            super(view);
            mTvDistance = (TextView) view.findViewById(R.id.tv_distance);
            mLabel1 = (TextView) view.findViewById(R.id.label1);
            mLabel2 = (TextView) view.findViewById(R.id.label2);
            mLabel3 = (TextView) view.findViewById(R.id.label3);
            mUserSmallicon = (ImageView) view.findViewById(R.id.user_smallicon);
            mTvNickname = (TextView) view.findViewById(R.id.tv_nickname);
            mTvUserage = (TextView) view.findViewById(R.id.tv_userage);
            mIvGender = (ImageView) view.findViewById(R.id.iv_gender);
            mIvBigpic = (ImageView) view.findViewById(R.id.iv_bigpic);
            mLllayout = (LinearLayout) view.findViewById(R.id.linear_label);

        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

