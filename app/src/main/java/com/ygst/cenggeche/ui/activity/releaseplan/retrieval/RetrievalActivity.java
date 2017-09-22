package com.ygst.cenggeche.ui.activity.releaseplan.retrieval;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.ReleaseplanActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.cartype.Myadapter;
import com.ygst.cenggeche.ui.activity.releaseplan.surerelease.SureReleaseAdapter;
import com.ygst.cenggeche.ui.activity.suretravel.InputTask;
import com.ygst.cenggeche.ui.activity.suretravel.PoiListAdapter;
import com.ygst.cenggeche.ui.activity.suretravel.SearchAdapter;
import com.ygst.cenggeche.utils.AMapUtil;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.SharedPreferencesUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.list;
import static android.R.string.no;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 *  检索 输入点的搜索
 */

public class RetrievalActivity extends MVPBaseActivity<RetrievalContract.View, RetrievalPresenter> implements RetrievalContract.View,PoiSearch.OnPoiSearchListener, TextWatcher, Inputtips.InputtipsListener {


    private ListView mPoiSearchList;
    private String TAG="RetrievalActivity";
    private EditText autoCompleteTextView;
    private PoiListAdapter mpoiadapter;
    private SearchAdapter mAdapter;
    private List<String> listString;
    //历史记录的保存集合
    ArrayList<String> mHistoryList=new ArrayList<>();
    private List mHislist;
    private ListView mHisTtoryList;
    private Myadapter myadapter;
    private List searchist;
    public static final String SEARCH_HISTORY = "search_history";
    private ArrayList<String> mOriginalValues;//历史记录的集合
    private SharedPreferences sp;
    private LinearLayout historyLayout;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrieval;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPoiSearchList = (ListView) findViewById(R.id.listView);
        mHisTtoryList = (ListView) findViewById(R.id.hislistView);
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        TextView clear = (TextView) findViewById(R.id.tv_clear_history);
        historyLayout = (LinearLayout) findViewById(R.id.history_ll);
        //创建数据shared保存数据
        sp = getSharedPreferences(SEARCH_HISTORY, 0);


        //返回事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //清除数据库保存的历史记录
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showInfoDialog(RetrievalActivity.this, "确定删除历史记录吗", "提示", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sp.edit().clear().commit();
                        myadapter.notifyDataSetChanged();
                        mPoiSearchList.setVisibility(View.GONE);
                    }
                },null);
            }
        });
        //初始化数据 得到历史记录
        initSearchHistory();
        //历史记录创建适配器
        LogUtils.i(TAG,mOriginalValues.size()+"===历史记录");
        myadapter = new Myadapter(this,mOriginalValues);
        mPoiSearchList.setAdapter(myadapter);

        autoCompleteTextView = (EditText) findViewById(R.id.search_edit);
        mAdapter = new SearchAdapter(this);
        autoCompleteTextView.addTextChangedListener(this);

        mHisTtoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poi_Search(listString.get(position));
                mHisTtoryList.setVisibility(View.GONE);
            }
        });

        mPoiSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RetrievalActivity.this, ReleaseplanActivity.class);
                intent.putExtra("result", mOriginalValues.get(position).toString());
                setResult(3, intent);
                finish();
            }
        });

    }



    private void poi_Search(String str){
        PoiSearch.Query mPoiSearchQuery = new PoiSearch.Query(str, "", AppData.getLocation());
        mPoiSearchQuery.requireSubPois(true);   //true 搜索结果包含POI父子关系; false
        mPoiSearchQuery.setPageSize(10);
        mPoiSearchQuery.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(RetrievalActivity.this,mPoiSearchQuery);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        PoiSearch.Query query = poiSearch.getQuery();
        int pageSize = query.getPageSize();
        InputTask.getInstance(this, mAdapter).onSearch(str, "");

    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<PoiItem> poiItems = new ArrayList<PoiItem>();
            poiItems.add(item);
        }

    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null ) {

                List<PoiItem> poiItems = result.getPois();
                mHisTtoryList.setVisibility(View.VISIBLE);

                mpoiadapter =new PoiListAdapter(this, poiItems);
                mPoiSearchList.setAdapter(mpoiadapter);
            }
        } else {
            ToastUtil.show(this.getApplicationContext(), rcode);
        }
        Log.i(TAG,"====="+rcode+result.toString()+"---"+result.getPois().size());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if(s!=null){
            historyLayout.setVisibility(View.GONE);
        }else{
            historyLayout.setVisibility(View.VISIBLE);
        }
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, AppData.getLocation());
            inputquery.setCityLimit(true);
            Inputtips inputTips = new Inputtips(RetrievalActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
        Log.i(TAG,"====="+newText+"---");

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        Log.i(TAG,"===recode=="+rCode+"==");

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            listString = new ArrayList<String>();
            listString.clear();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }


            myadapter = new Myadapter(this,listString);
            mHisTtoryList.setAdapter(myadapter);
            mHisTtoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(RetrievalActivity.this, ReleaseplanActivity.class);

                    //将搜索记录保存到集合中
                    saveSearchHistory(listString.get(position));
                    intent.putExtra("result", listString.get(position));
                    setResult(3, intent);
                    finish();
                }
            });
            myadapter.notifyDataSetChanged();
        } else {
            ToastUtil.show(this.getApplicationContext(), rCode);
        }


    }



    /*
       * 保存搜索记录
       */
    private void saveSearchHistory(String savehistory) {
        if (savehistory.length() < 1) {
            return;
        }
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (savehistory.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, savehistory);
        }

        //保存搜索的历史记录
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, savehistory + ",").commit();
        }
    }

    /**
     * 读取历史搜索记录
     */
    public void initSearchHistory() {
        SharedPreferences sp = getSharedPreferences(
                SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        mOriginalValues = new ArrayList<String>();
        if (hisArrays.length == 1) {
            return;
        }
        for (int i = 0; i < hisArrays.length; i++) {
            mOriginalValues.add(hisArrays[i]);
            LogUtils.i(TAG,mOriginalValues.size()+"---"+mOriginalValues.get(i));
        }

    }

}
