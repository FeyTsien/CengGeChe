package com.ygst.cenggeche.ui.activity.suretravel;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.utils.AMapUtil;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 *  检索 输入点的搜索
 */

public class SureAddrActivity extends Activity implements PoiSearch.OnPoiSearchListener, TextWatcher, Inputtips.InputtipsListener {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private ListView mPoiSearchList;
    private Button mSearchbtn;
    private String TAG="RetrievalActivity";
    private String city = "北京";
    private AutoCompleteTextView autoCompleteTextView;
    private PoiListAdapter mpoiadapter;
    private SearchAdapter mAdapter;
    private String ONCLICKADD="";
    private List<String> listString;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval);
        ButterKnife.bind(this);
        mTvTitle.setText("出发");
        mPoiSearchList = (ListView) findViewById(R.id.listView);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.tv_keyword);
        mAdapter = new SearchAdapter(this);
        autoCompleteTextView.addTextChangedListener(this);
        mSearchbtn = (Button)findViewById(R.id.bt_address_search);


        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = autoCompleteTextView.getText().toString();
            poi_Search(keyword);

            }
        });
        itemonclick();

    }

    public void itemonclick(){
        mPoiSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = listString.get(position);
                ToastUtil.show(SureAddrActivity.this,"sss=="+s);
                Intent intent = new Intent(SureAddrActivity.this, SureTravelActivity.class);
                String passString = s;
                intent.putExtra("result", passString);
                setResult(3, intent);
                finish();

            }
        });
    }

    private void poi_Search(String str){
        PoiSearch.Query mPoiSearchQuery = new PoiSearch.Query(str, "", city);
        mPoiSearchQuery.requireSubPois(true);   //true 搜索结果包含POI父子关系; false
        mPoiSearchQuery.setPageSize(10);
        mPoiSearchQuery.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(SureAddrActivity.this,mPoiSearchQuery);
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
            mpoiadapter =new PoiListAdapter(this, poiItems);
            mPoiSearchList.setAdapter(mpoiadapter);
        }
        Log.i(TAG,"====="+rCode);

    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null ) {
                List<PoiItem> poiItems = result.getPois();
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
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, city);
            inputquery.setCityLimit(true);
            Inputtips inputTips = new Inputtips(SureAddrActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        Log.i(TAG,"===recode=="+rCode+"==");

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
            autoCompleteTextView.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.show(this.getApplicationContext(), rCode);
        }


    }





}
