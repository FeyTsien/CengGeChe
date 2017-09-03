package com.ygst.cenggeche.ui.activity.friendlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.FriendListBean;
import com.ygst.cenggeche.ui.activity.friendinfo.FriendInfoActivity;
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
            if(!TextUtils.isEmpty(bean.getFriendNote())){
                friendName = bean.getFriendNote();
            }else if(!TextUtils.isEmpty(bean.getNickname())){
                friendName = bean.getNickname();
            }else{
                friendName = bean.getFriendusername();
            }
            holder.tv_name.setText(friendName);

            if(!TextUtils.isEmpty(bean.getUserPic())){
                Uri uri = Uri.parse(bean.getUserPic());
                holder.iv_img.setImageURI(uri);
            }else {
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(friendName.charAt(0)), mColorGenerator.getColor(friendName));
                holder.iv_img.setImageDrawable(drawable);
            }
        }

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("friendsUsername",bean.getFriendusername());
                intent.setClass(mContext, FriendInfoActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListDataBean.size();
    }

    public static class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final ImageView iv_img;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
