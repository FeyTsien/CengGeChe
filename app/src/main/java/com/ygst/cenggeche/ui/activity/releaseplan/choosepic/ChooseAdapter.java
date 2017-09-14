package com.ygst.cenggeche.ui.activity.releaseplan.choosepic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ChooseAdapter extends BaseAdapter {

    private ArrayList<String> mList;
    private Context context;
    private String TAG=this.getClass().getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    public ChooseAdapter(Context context, ArrayList<String> list){
        this.context=context;
        this.mList=list;
    }


    @Override
    public int getCount() {
        Log.i(TAG,mList.size()+"===");
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
            convertView = View.inflate(context, R.layout.item_choosepic, null);
            viewHolder = new ViewHolder();
            viewHolder.mIvGender = (ImageView) convertView.findViewById(R.id.iv_chooseuserimg);
            viewHolder.mIvChoose = (ImageView) convertView.findViewById(R.id.isselected);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(mList.get(position)).into(viewHolder.mIvGender);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.mIvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);

                for (int i=0;i>mList.size();i++){
                    if(i==position){
                        finalViewHolder.mIvChoose.setVisibility(View.VISIBLE);

                    }else{
                        finalViewHolder.mIvChoose.setVisibility(View.GONE);

                    }
                }
            }
        });


        return convertView;
    }

    //View复用
    public final class ViewHolder {
        ImageView mIvGender,mIvChoose;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }

}
