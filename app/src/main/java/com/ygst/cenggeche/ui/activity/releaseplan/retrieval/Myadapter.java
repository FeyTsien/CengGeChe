package com.ygst.cenggeche.ui.activity.releaseplan.retrieval;

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
 * Created by Administrator on 2017/5/9.
 */

public class Myadapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context context;

    public Myadapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.text_item, parent, false);
            holder.chexiText = (TextView) convertView.findViewById(R.id.chexiText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.chexiText.setText(list.get(position));
//        AutoUtils.autoSize(convertView);
        return convertView;
    }


    private class ViewHolder {
        private TextView chexiText;
    }
}
