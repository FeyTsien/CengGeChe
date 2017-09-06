package com.ygst.cenggeche.ui.fragment.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private  ArrayList<String> mList;
    private  Context context;
    private String TAG="MyAdapter";
    private OnItemClickListener mOnItemClickListener = null;

    public MyAdapter(Context context,ArrayList<String> list){
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
        holder.mTvNickname.setText(mList.get(position));

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
        ImageView mUserSmallicon,mIvGender;
        TextView mTvNickname;
        TextView mTvUserage;
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

        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

