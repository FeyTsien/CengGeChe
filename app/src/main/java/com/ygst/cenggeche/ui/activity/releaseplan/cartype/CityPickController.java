package com.ygst.cenggeche.ui.activity.releaseplan.cartype;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.AllCarBrandBean;
import com.ygst.cenggeche.bean.CxBean;
import com.ygst.cenggeche.manager.GsonManger;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.ui.view.LetterSideBar;
import com.ygst.cenggeche.utils.UrlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * Created by shixi_tianrui1 on 16-9-25.
 */
public class CityPickController implements LetterSideBar.OnTouchLetterListener {

    private View mRootView;
    private TextView mTvMask;
    private LetterSideBar mLsSidebar;
    private ListView mLvCityList, mCxList;
    private Context mContext;
    private CxCallBack cxCallBack;
    private CityAdapter mAdapter;

    private List<AllCarBrandBean.BrandBean> mCities;
    private Myadapter myadapter;
    private List<String> mItemList;

    public CityPickController(final Context context, View root, final List<AllCarBrandBean.BrandBean> cities) {
        this.mRootView = root;
        this.mContext = context;
        initView();
        this.mCities = cities;
        mAdapter = new CityAdapter(mContext, mCities);
        mLvCityList.setAdapter(mAdapter);
        mLvCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("CityPickController", "onNext:++++ " + position);
                final String brand = mCities.get(position).getBrand();
//                final String logo = mCities.get(position).getLogo();
                Map map = new HashMap();
                map.put("brand", brand);
                HttpManager.getHttpManager().postMethod(UrlUtils.BASEURl+ UrlUtils.GETALLCARTYPEBRAND, new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("CityPickController", "onNext:++++ " + s);
                        final CxBean cxb = (CxBean) GsonManger.getGsonManger().gsonFromat(s, new CxBean());
                        if (cxb.getCode().equals("0000")) {
                            myadapter = new Myadapter(context, cxb.getData());
                            mCxList.setAdapter(myadapter);
                            mCxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (cxCallBack != null) {
                                        cxCallBack.cxCallBack(brand, cxb.getData().get(position));
                                    }
                                }
                            });
                        }
                    }
                }, map);

            }
        });

    }

    private void initView() {
        mTvMask = (TextView) mRootView.findViewById(R.id.tv_mask);
        mLsSidebar = (LetterSideBar) mRootView.findViewById(R.id.pp_letter);
        mLvCityList = (ListView) mRootView.findViewById(R.id.pp_lv);
        mCxList = (ListView) mRootView.findViewById(R.id.cx_lv);
        mLsSidebar.setOverLayTextView(mTvMask);
        mLsSidebar.setOnTouchLetterListener(this);
    }

    public interface CxCallBack {
        void cxCallBack(String carname, String cartype);
    }

    public void setCallBack(CxCallBack cxCallBack) {
        this.cxCallBack = cxCallBack;
    }

    /**
     * 处理选择字母时的回调
     *
     * @param letter
     */
    @Override
    public void onLetterSelected(String letter) {
        Log.i("CityPickController", "onLetterSelected:+++++ " + letter);
//        int position = mAdapter.getPosition(letter);
//        Log.i("CityPickController", "onLetterSelected:+++++ " + position);
//        if (position != -1)
//            mLvCityList.setSelection(position);
    }
}
