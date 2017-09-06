package com.ygst.cenggeche.ui.activity.suretravel;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ygst.cenggeche.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class InputTask implements Inputtips.InputtipsListener{
    private static InputTask mInstance;
    private SearchAdapter mAdapter;
    private PoiSearch mSearch;
    private Context mContext;
    private String TAG="InputTask";
    private InputTask(Context context, SearchAdapter adapter){
        this.mContext = context;
        this.mAdapter = adapter;
    }

    /**
     * 获取实例
     * @param context 上下文
     * @param adapter 数据适配器
     * @return
     */
    public static InputTask getInstance(Context context, SearchAdapter adapter){
        if(mInstance == null){
            synchronized (InputTask.class) {
                if(mInstance == null){
                    mInstance = new InputTask(context, adapter);
                }
            }
        }
        return mInstance;
    }
    /**
     * 设置数据适配器
     * @param adapter
     */
    public void setAdapter(SearchAdapter adapter){
        this.mAdapter = adapter;
    }


    public void onSearch(String key, String city){
        //POI搜索条件
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        mSearch = new PoiSearch(mContext, query);
        //设置异步监听
        //查询POI异步接口
        mSearch.searchPOIAsyn();
    }
    /**
     * 异步搜索回调
     */
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if(rCode == 0 && poiResult != null){
            ArrayList<AddressBean> data = new ArrayList<AddressBean>();
            ArrayList<PoiItem> items = poiResult.getPois();
            for(PoiItem item : items){
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                data.add(new AddressBean(lon, lat, title, text));
                Log.i(TAG,data.size()+"====");
            }
            mAdapter.setData(data);

            mAdapter.notifyDataSetChanged();
        }
    }




    @Override
    public void onGetInputtips(List<Tip> list, int i) {
       Log.i(TAG,list.size()+"===========");

    }
}
