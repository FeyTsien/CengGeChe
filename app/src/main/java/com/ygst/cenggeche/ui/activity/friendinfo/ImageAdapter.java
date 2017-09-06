package com.ygst.cenggeche.ui.activity.friendinfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.ygst.cenggeche.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private Integer[] mThumbIds;

    public ImageAdapter(Context c,Integer[] ints) {
        this.mThumbIds = ints;
        this.mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);

        i.setImageResource(mThumbIds[position]);
        i.setAdjustViewBounds(true);
        i.setLayoutParams(new Gallery.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        i.setBackgroundResource(R.drawable.e);
        return i;
    }

}