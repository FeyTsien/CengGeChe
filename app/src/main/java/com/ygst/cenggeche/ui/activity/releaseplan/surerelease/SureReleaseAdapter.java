package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ygst.cenggeche.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class SureReleaseAdapter extends BaseAdapter {

    private final List<String> mList;
    private final Context context;
    private LayoutInflater li;

    public SureReleaseAdapter(Context context,List<String> data){
        li = LayoutInflater.from(context);
        this.mList=data;
        this.context=context;
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
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = li.inflate(R.layout.sure_release_iten, null);
            vh.title = (TextView) convertView.findViewById(R.id.item_title);
            vh.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.title.setText(mList.get(position));
//        vh.text.setText(mList.get(position).getText());
        return convertView;
    }

    private class ViewHolder{
        public TextView title;
        public TextView text;
    }
}
