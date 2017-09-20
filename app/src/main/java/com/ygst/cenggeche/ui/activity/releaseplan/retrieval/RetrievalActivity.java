package com.ygst.cenggeche.ui.activity.releaseplan.retrieval;


import android.content.Intent;
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
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
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
        //返回事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    public static String SceneList2String(List SceneList)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;

    }
    //string 转化为list
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
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

            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.route_inputs, listString);
            myadapter = new Myadapter(this,listString);
            mHisTtoryList.setAdapter(myadapter);
            mHisTtoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(RetrievalActivity.this, ReleaseplanActivity.class);
                    try {
                        String2SceneList(listString.get(position));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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





}
