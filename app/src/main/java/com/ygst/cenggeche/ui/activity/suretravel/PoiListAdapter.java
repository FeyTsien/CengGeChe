package com.ygst.cenggeche.ui.activity.suretravel;//package com.ygst.cenggeche.ui.activity.suretravel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.ygst.cenggeche.R;

import java.util.List;

public class PoiListAdapter extends BaseAdapter{
    private Context ctx;
    private List<PoiItem> list;

    public PoiListAdapter(Context context, List<PoiItem> poiList) {
        this.ctx = context;
        this.list = poiList;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.poi_search_listview_item, null);
            holder.poititle = (TextView) convertView
                   .findViewById(R.id.poititle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PoiItem item = list.get(position);
        holder.poititle.setText(item.getTitle());



        return convertView;
    }
    private class ViewHolder {
        TextView poititle;

    }

}
