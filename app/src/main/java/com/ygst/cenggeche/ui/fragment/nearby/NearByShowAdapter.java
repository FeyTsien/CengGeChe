package com.ygst.cenggeche.ui.fragment.nearby;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/25.
 */

public class NearByShowAdapter extends BaseAdapter {

    private  ArrayList<String> mList;
    private  Context context;

    public NearByShowAdapter(Context context, ArrayList<String> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.nearby_gridview_item, null);
            holder = new ViewHolder();
            holder.mTvDistance = (TextView) convertView.findViewById(R.id.tv_distance);
            holder.mLabel1 = (TextView) convertView.findViewById(R.id.label1);
            holder.mLabel2 = (TextView) convertView.findViewById(R.id.label2);
            holder.mLabel3 = (TextView) convertView.findViewById(R.id.label3);
            holder.mUserSmallicon = (ImageView) convertView.findViewById(R.id.user_smallicon);
            holder.mTvNickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            holder.mTvUserage = (TextView) convertView.findViewById(R.id.tv_userage);
            holder.mIvGender = (ImageView) convertView.findViewById(R.id.iv_gender);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLabel1.setTextSize(CommonUtils.px2dip(20));
        holder.mLabel2.setTextSize(CommonUtils.px2dip(20));
        holder.mLabel3.setTextSize(CommonUtils.px2dip(20));
        return convertView;
    }


    //View复用
    public final class ViewHolder {
        TextView mTvDistance;
        TextView mLabel1,mLabel2,mLabel3;
        ImageView mUserSmallicon,mIvGender;
        TextView mTvNickname;
        TextView mTvUserage;
    }
}
