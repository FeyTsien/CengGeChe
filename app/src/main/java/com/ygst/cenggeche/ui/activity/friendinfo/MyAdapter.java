package com.ygst.cenggeche.ui.activity.friendinfo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.ikninephotoview.IKNinePhotoViewAdapter;
import com.ygst.cenggeche.ui.view.ikninephotoview.IKNinePhotoViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Idtk on 2017/3/9.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */

public class MyAdapter extends IKNinePhotoViewAdapter<MyAdapter.MyHolder> {

    private Context mContext;
    private int count;
    private List<String> mListPic;

    public MyAdapter(Context context, List<String> listPic) {
        super();
        mContext = context;
        mListPic = listPic;
        count = listPic.size();
    }

    @Override
    public MyHolder createView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        MyHolder viewHolder = new MyHolder(view);
        return viewHolder;
    }

    @Override
    public void displayView(final MyHolder holder, final int position) {
        Glide.with(mContext)
                .load(mListPic.get(position))
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.icon_my_avatar))
                .into(holder.mImageView);

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", position + "");
                Intent intent = new Intent(mContext, BigPicActivity.class);
                intent.putExtra("pic_uri", mListPic.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class MyHolder extends IKNinePhotoViewHolder {

        @BindView(R.id.nine_pic)
        ImageView mImageView;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
