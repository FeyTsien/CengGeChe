package com.ygst.cenggeche.ui.activity.friendinfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
    //上下文对象
    private Context mContext;
    private Integer[] mImageIds;

    public GridViewAdapter(Context context, Integer[] mImageIds) {
        this.mContext = context;
        this.mImageIds = mImageIds;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int item) {
        return item;
    }

    public long getItemId(int id) {
        return id;
    }

    //创建View方法
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(236, 255));//设置ImageView对象布局
//            imageView.setAdjustViewBounds(false);//设置边界对齐
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
//            imageView.setPadding(8, 8, 8, 8);//设置间距
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mImageIds[position]);//为ImageView设置图片资源
        return imageView;
    }
}