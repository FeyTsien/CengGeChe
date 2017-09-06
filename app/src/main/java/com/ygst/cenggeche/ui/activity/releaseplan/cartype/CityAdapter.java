package com.ygst.cenggeche.ui.activity.releaseplan.cartype;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.AllCarBrandBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


/**
 * Created by shixi_tianrui1 on 16-9-27.
 */
public class CityAdapter extends BaseAdapter {

    private String TAG=this.getClass().getSimpleName();
    private Context mContext;
    private List<AllCarBrandBean.BrandBean> mCities;
    private HashMap<AllCarBrandBean.BrandBean, Integer> mLetterPos = new LinkedHashMap<>();

    public CityAdapter(Context context, List<AllCarBrandBean.BrandBean> cities) {
        mContext = context;
        mCities = cities;
        Log.i(TAG,"--==="+cities.size());
        mLetterPos.put(cities.get(0), 1);
        for (int i = 1; i < mCities.size(); i++) {
            AllCarBrandBean.BrandBean prev = mCities.get(i - 1);
            AllCarBrandBean.BrandBean cur = mCities.get(i);
            if (!TextUtils.equals(getFirstLetter(prev.getInitials())
                    , getFirstLetter(cur.getInitials()))) {
                mLetterPos.put(cur, i);
            }
        }
        // L.d(mLetterPos.toString());
    }

    @Override
    public int getCount() {
        Log.i(TAG,"--==="+mCities.size());
        return mCities.size();
    }

    @Override
    public Object getItem(int i) {
        return mCities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Log.i(TAG, "getView:++++++++++11++ " + mCities.get(i).getBrand());
        ViewHolder holder;
        if (convertView == null) {
            Log.i(TAG, "getView:+++++++++22+++ " + mCities.get(i).getBrand());
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.pp_item, viewGroup, false);
            holder.mTvCity = (TextView) convertView.findViewById(R.id.id_tv_city_name);
            holder.mTvLetter = (TextView) convertView.findViewById(R.id.id_tv_letter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.i(TAG, "getView:++++++++333++++ " + mCities.get(i).getBrand());
        }
        AllCarBrandBean.BrandBean curCity = mCities.get(i);
        holder.mTvCity.setText(mCities.get(i).getBrand());

        if (mLetterPos.containsKey(curCity)) {
            holder.mTvLetter.setVisibility(View.VISIBLE);
            String letter = getFirstLetter(curCity.getInitials());
            if (!TextUtils.isEmpty(letter)) {
                holder.mTvLetter.setText(letter.toUpperCase());
            }
        } else {
            holder.mTvLetter.setVisibility(View.GONE);
        }
        Log.i(TAG, "getView:++++++++++++ " + mCities.get(i).getBrand());
        holder.mTvCity.setText(curCity.getBrand());
        return convertView;
    }


    private class ViewHolder {
        private TextView mTvLetter;
        private TextView mTvCity;

    }

    public static String getFirstLetter(String pinyin) {
        if (TextUtils.isEmpty(pinyin)) return null;
        return pinyin;
    }

    public int getPosition(String letter) {
        int position = -1;
        Log.i("CityAdapter", "getPosition: ++++");
        Set<AllCarBrandBean.BrandBean> set = mLetterPos.keySet();
        Iterator<AllCarBrandBean.BrandBean> it = set.iterator();
        AllCarBrandBean.BrandBean city = null;
        while (it.hasNext()) {
            city = it.next();
            Log.i("CityAdapter", "getPosition: +" + city.getInitials().substring(0, 1).toLowerCase() + "====" + letter.toLowerCase());
            if (city.getInitials().substring(0, 1).toLowerCase().equals(letter.toLowerCase())) {
                Log.i("CityAdapter", "getPosition: +" + mLetterPos.get(city));
                if (letter.toLowerCase().equals("a"))
                    return 0;
                return mLetterPos.get(city);
            }
        }
        return -1;
    }
}
