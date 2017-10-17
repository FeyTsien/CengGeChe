package com.ygst.cenggeche.ui.fragment.nearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.NearByBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NearByBean.DataBean> mList;
    private Context context;
    private String TAG = "MyAdapter";
    private OnItemClickListener mOnItemClickListener = null;

    public MyAdapter(Context context, List<NearByBean.DataBean> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.nearby_gridview_item, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NearByBean.DataBean dataBean = mList.get(position);

        //昵称
        holder.mTvNickname.setText(dataBean.getNickname());
        //年龄
        holder.mTvUserage.setText(dataBean.getAge() + "岁");
        String process = "";
        //性别
        int gender = dataBean.getGender();
        if (gender == 1) {
            holder.mIvGender.setImageResource(R.mipmap.icon_boy);
            process = "他发布了行程";
        } else if (gender == 0) {
            holder.mIvGender.setImageResource(R.mipmap.icon_girl);
            process = "她发布了行程";
        }

        //是否有发行程
        if (dataBean.getStrokeStatus().equals("10")) {
            holder.mTvProcessState.setText(process);
            holder.mTvProcessState.setVisibility(View.VISIBLE);
        } else {
            holder.mTvProcessState.setVisibility(View.GONE);
        }

        //头像
        Glide.with(context)
                .load(dataBean.getUserPic())
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.icon_nearby_item_bg))
                .into(holder.mIvBigpic);

        //  设置千米数
        String distance = dataBean.getDistance() + "";

        if (distance != null && !distance.equals("0.00")) {
            double pass = Double.parseDouble(distance);
            if (pass <= 10) {
                holder.mTvDistance.setText("<10米");
            } else if (10 < pass && pass <= 1000) {
                holder.mTvDistance.setText((int) pass + "米");
            } else if (1000 <= pass) {
                double i = pass / 1000 + pass % 1000;
                holder.mTvDistance.setText((int) i + "千米");
            }
        }
        //设置标签
        List<String> tags = dataBean.getTags();

        if (tags.size() == 1) {
            holder.mLabel1.setVisibility(View.VISIBLE);
            holder.mLabel1.setText(dataBean.getTags().get(0));
        } else if (tags.size() == 2) {
            holder.mLabel1.setVisibility(View.VISIBLE);
            holder.mLabel2.setVisibility(View.VISIBLE);
            holder.mLabel1.setText(dataBean.getTags().get(0));
            holder.mLabel2.setText(dataBean.getTags().get(1));
        } else if (tags.size() >= 3) {
            holder.mLabel1.setVisibility(View.VISIBLE);
            holder.mLabel2.setVisibility(View.VISIBLE);
            holder.mLabel3.setVisibility(View.VISIBLE);
            holder.mLabel1.setText(dataBean.getTags().get(0));
            holder.mLabel2.setText(dataBean.getTags().get(1));
            holder.mLabel3.setText(dataBean.getTags().get(2));
        } else {
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvDistance;
        TextView mTvProcessState;
        TextView mLabel1, mLabel2, mLabel3;
        ImageView mIvGender, mIvBigpic;
        TextView mTvNickname;
        TextView mTvUserage;
        LinearLayout mLllayout;

        public MyViewHolder(View view) {
            super(view);
            mTvDistance = (TextView) view.findViewById(R.id.tv_distance);
            mTvProcessState = (TextView) view.findViewById(R.id.tv_process_state);
            mLabel1 = (TextView) view.findViewById(R.id.label1);
            mLabel2 = (TextView) view.findViewById(R.id.label2);
            mLabel3 = (TextView) view.findViewById(R.id.label3);
            mTvNickname = (TextView) view.findViewById(R.id.tv_nickname);
            mTvUserage = (TextView) view.findViewById(R.id.tv_userage);
            mIvGender = (ImageView) view.findViewById(R.id.iv_gender);
            mIvBigpic = (ImageView) view.findViewById(R.id.iv_bigpic);
            mLllayout = (LinearLayout) view.findViewById(R.id.linear_label);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

