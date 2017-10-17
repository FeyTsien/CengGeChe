package com.ygst.cenggeche.ui.activity.friendlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.FriendListBean;
import com.ygst.cenggeche.ui.widget.ColorGenerator;
import com.ygst.cenggeche.ui.widget.TextDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017/5/8.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyRecycleHolder> {

    private List<FriendListBean.DataBean> mListDataBean;
    private Context mContext;
    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerItemLongListener mOnItemLongListener = null;

    public ContactAdapter(Context context) {
        this.mContext = context;
        mListDataBean = new ArrayList<>();
    }

    public void addAll(List<FriendListBean.DataBean> beans) {
        if (mListDataBean.size() > 0) {
            mListDataBean.clear();
        }
        mListDataBean.addAll(beans);
        notifyDataSetChanged();
    }

    public void add(FriendListBean.DataBean bean, int position) {
        mListDataBean.add(position, bean);
        notifyItemInserted(position);
    }

    public void add(FriendListBean.DataBean bean) {
        mListDataBean.add(bean);
        notifyItemChanged(mListDataBean.size() - 1);
    }

    @Override
    public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        return new MyRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleHolder holder, int position) {
        if (mListDataBean == null || mListDataBean.size() == 0 || mListDataBean.size() <= position)
            return;
        final FriendListBean.DataBean bean = mListDataBean.get(position);
        String friendName = "";
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getFriendNote())) {
                friendName = bean.getFriendNote();
            } else if (!TextUtils.isEmpty(bean.getNickname())) {
                friendName = bean.getNickname();
            } else {
                friendName = bean.getFriendusername();
            }
            holder.tv_name.setText(friendName);
            //头像
            int resourceId = R.mipmap.icon_my_avatar;
            if (bean.getGender() == 0) {
                resourceId = R.mipmap.icon_avatar_girl;
            } else if (bean.getGender() == 1) {
                resourceId = R.mipmap.icon_avatar_boy;
            }
            Glide.with(mContext)
                    .load(bean.getUserPic())
                    .apply(new RequestOptions().placeholder(resourceId))
                    .into(holder.iv_img);
        }
    }

    @Override
    public int getItemCount() {
        return mListDataBean.size();
    }

    public class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final ImageView iv_img;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongListener != null) {
                        mOnItemLongListener.onItemLongClick(v, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);

    }

    public interface OnRecyclerItemLongListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerItemLongListener listener) {
        this.mOnItemLongListener = listener;
    }
}
