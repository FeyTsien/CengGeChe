package com.ygst.cenggeche.ui.activity.releaseplan.cartype;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.AllCarBrandBean;
import com.ygst.cenggeche.bean.CarBrandTypeBean;
import com.ygst.cenggeche.bean.CodeBean;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.ReleaseplanActivity;
import com.ygst.cenggeche.ui.view.LetterSideBar;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;



/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CartypeActivity extends MVPBaseActivity<CartypeContract.View, CartypePresenter> implements CartypeContract.View,LetterSideBar.OnTouchLetterListener  {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    EditText mEvSearch;
    private String TAG=this.getClass().getSimpleName();
    private View view;
    private ListView mListView;
    private ListView mTypeListView;
    private List<AllCarBrandBean.BrandBean> brandBeanList;
    private ListView mColorListView;

    private String CARTYPE="";
    private String CARBRAND="";
    private ArrayList<String> mColorList;
    private List<String> mCarTypedata;
    private Button mBtnSearch;
    private LetterSideBar mLsSidebar;
    private CityAdapter mAdapter;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cartype;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvTitle.setText("车辆信息");
        mListView = (ListView)findViewById(R.id.pp_lv);
        mTypeListView = (ListView)findViewById(R.id.cx_lv);
        mColorListView = (ListView)findViewById(R.id.cx_color);
        mEvSearch = (EditText) findViewById(R.id.et_inputcartype);
        mBtnSearch = (Button) findViewById(R.id.btn_searchcartype);
        mLsSidebar = (LetterSideBar)findViewById(R.id.pp_letter);
        mLsSidebar.setOnTouchLetterListener(this);


        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cartype = mEvSearch.getText().toString().trim();
                if(!TextUtils.isEmpty(cartype)){
                    Intent intent = new Intent(CartypeActivity.this, ReleaseplanActivity.class);
                    intent.putExtra("result", cartype);
                    setResult(4, intent);
                    finish();
                }
            }
        });
        Log.i(TAG,"---------");
        getCarBrand();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getCarTypeBrand(brandBeanList.get(position).getBrand());
                CARBRAND=brandBeanList.get(position).getBrand();
            }
        });

        //获取颜色信息
        mTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mColorList = new ArrayList<String>();

                String data[]=new String[]{"黑色","白色","绿色","紫色","黄色","橙色","棕褐色"," 蓝色","红色","银灰色"};
                for (int i=0;i<data.length;i++)
                    mColorList.add(data[i]);
                CARTYPE=mCarTypedata.get(position);
                Myadapter adapter=new Myadapter(CartypeActivity.this,mColorList);
                mColorListView.setAdapter(adapter);
            }
        });

        mColorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String carcolor = mColorList.get(position);
                Intent intent = new Intent(CartypeActivity.this, ReleaseplanActivity.class);
                intent.putExtra("result", CARBRAND+"-"+CARTYPE+"-"+carcolor);
                setResult(5, intent);
                finish();

            }
        });
    }

    @Override
    public void getCarBrandSuccess() {

    }

    @Override
    public void getCarBrandFail() {

    }

    @Override
    public void getCarTypeBrandSuccess() {

    }

    @Override
    public void getCarTypeBrandFail() {

    }

    public void getCarBrand() {
        Map<String, String> map = new HashMap<>();
        map.put("s","1");
        LogUtils.i(TAG,"Url地址："+UrlUtils.GETALLCARBRAND);

        HttpManager.getHttpManager().postMethod(UrlUtils.GETALLCARBRAND, new Observer<String>() {


            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "返回的onError", e);
                Log.i(TAG,e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                CodeBean codeBean = gson.fromJson(s, CodeBean.class);
                AllCarBrandBean allCarBrandBean = gson.fromJson(s, AllCarBrandBean.class);
                if ("0000".equals(codeBean.getCode())) {

                    brandBeanList = allCarBrandBean.getBrand();

                    Collections.sort(brandBeanList, new Comparator<AllCarBrandBean.BrandBean>() {
                        @Override
                        public int compare(AllCarBrandBean.BrandBean lhs, AllCarBrandBean.BrandBean rhs) {
                            if (lhs.getInitials().equals(rhs.getInitials())) {
                                return lhs.getBrand().compareTo(rhs.getBrand());
                            } else {
                                if ("#".equals(lhs.getInitials())) {
                                    return 1;
                                } else if ("#".equals(rhs.getInitials())) {
                                    return -1;
                                }
                                return lhs.getInitials().compareTo(rhs.getInitials());
                            }
                        }
                    });
                    mAdapter = new CityAdapter(CartypeActivity.this, brandBeanList);
                    mListView.setAdapter(mAdapter);
                }
            }
        }, map);
    }

    public void getCarTypeBrand(String brand) {
        Map<String, String> map = new HashMap<>();
        map.put("brand",brand);
        HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.GETALLCARTYPEBRAND, new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                LogUtils.e(TAG, "返回的onError", e);
                Log.i(TAG,e.toString());
            }

            @Override
            public void onNext(String s) {
                LogUtils.i("HttpManager", "ssss:" + s);
                Gson gson = new Gson();
                CarBrandTypeBean carBrandTypeBean = gson.fromJson(s, CarBrandTypeBean.class);
                if ("0000".equals(carBrandTypeBean.getCode())) {
                    mCarTypedata = carBrandTypeBean.getData();
                    Myadapter myadapter=new Myadapter(CartypeActivity.this, mCarTypedata);
                    mTypeListView.setAdapter(myadapter);

                }else{
                    Log.i(TAG,carBrandTypeBean.getMsg());
                }


            }
        }, map);
    }

    @Override
    public void onLetterSelected(String letter) {
        int position = mAdapter.getPosition(letter);
        if (position != -1)
            mListView.setSelection(position);
    }
}
