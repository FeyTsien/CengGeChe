package com.ygst.cenggeche.ui.fragment.nearby;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.NearByBean;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.userinfo.UserInfoActivity;
import com.ygst.cenggeche.ui.loadsir.PostUtil;
import com.ygst.cenggeche.ui.loadsir.callback.EmptyCallback;
import com.ygst.cenggeche.ui.loadsir.callback.LoadingCallback;
import com.ygst.cenggeche.ui.loadsir.callback.NoNetWorkCallback;
import com.ygst.cenggeche.ui.view.refresh.OnRefreshListener;
import com.ygst.cenggeche.ui.view.refresh.PullRefreshLayout;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.NetWorkUtil;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NearbyFragment extends MVPBaseFragment<NearbyContract.View, NearbyPresenter> implements NearbyContract.View {

    private View mRootView;
    private LoadService loadService;
    private LinearLayout LlNearby;
    private String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private PullRefreshLayout mRefreshLayout;
    private MyAdapter mNearAdapter;
    private int PAGE = 1;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private double Lit, LAT, ACC;
    private List<NearByBean.DataBean> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_nearby1, container, false);
        LlNearby = (LinearLayout) mRootView.findViewById(R.id.ll_nearby);
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new NoNetWorkCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(LlNearby, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                getNearby(PAGE);
            }
        });
        initData();
        initViews();
        return mRootView;
    }

    private void initData() {
        mList = new ArrayList<>();
        initLocation();
        startLocation();
        getNearby(PAGE);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


//        //设置Item增加、移除动画
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        mRefreshLayout = (PullRefreshLayout) mRootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                //下拉刷新
                PAGE = 1;
                getNearby(PAGE);
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.onRefreshComplete();
                    }
                }, 5000);
            }

            @Override
            public void onPullUpRefresh() {
                //上拉加载
                if (mList.size() % 10 == 0) {
                    PAGE = (int) (mList.size() / 10) + 1;
                    getNearby(PAGE);
                } else {
                    PAGE = (int) (mList.size() / 10) + 2;
                    getNearby(PAGE);
                }
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.onRefreshComplete();
                    }
                }, 5000);
            }
        });

        mNearAdapter = new MyAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mNearAdapter);
        mNearAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!AppData.isLoginEd() || JMessageClient.getMyInfo() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra(JMessageUtils.TARGET_USERNAME, mList.get(position).getUsername());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取附近的人列表
     */
    private void getNearby(int page) {
        if (!NetWorkUtil.isNetworkAvailable(getContext()) || !NetWorkUtil.checkNetState(getContext())) {
            //当前网络不可用，展示此提示页
            PostUtil.postCallbackDelayed(loadService, NoNetWorkCallback.class);
        } else {
            mPresenter.getNearby(AppData.getLit(),AppData.getLat(), page);
//            mPresenter.getNearby("116.5406130","39.7665020", page);
            //不展示提示页
            PostUtil.postSuccessDelayed(loadService);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void getnearbySuccess(NearByBean nearbybean, int page) {
        mRefreshLayout.onRefreshComplete();
        if (PAGE > 1) {
            if (PAGE == page) {
                PAGE = 1;
                mList.addAll(nearbybean.getData());
            }
        } else {
            mList.clear();
            mList.addAll(nearbybean.getData());
        }

        if (mList.size() > 0) {
            PostUtil.postSuccessDelayed(loadService);
        } else {
            PostUtil.postCallbackDelayed(loadService, EmptyCallback.class);
        }
        mNearAdapter.notifyDataSetChanged();
    }

    @Override
    public void getnearbyFail(String msg) {
        ToastUtil.show(getActivity(), msg);
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(300000);//可选，设置定位间隔。设为5分钟
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");
                    Lit = location.getLongitude();
                    LAT = location.getLatitude();
                    ACC = location.getAccuracy();
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    //绘制marker
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                //定位之后的回调时间

                //解析定位结果，
                String result = sb.toString();
                Log.i(TAG, "result： " + result);
                AppData.savaLit(Lit + "");
                AppData.savaLat(LAT + "");
                getNearby(PAGE);
            } else {

            }
        }
    };


    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
//        String strInterval = etInterval.getText().toString();
//        if (!TextUtils.isEmpty(strInterval)) {
//            try{
//                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
//                locationOption.setInterval(Long.valueOf(strInterval));
//            }catch(Throwable e){
//                e.printStackTrace();
//            }
//        }

//        String strTimeout = etHttpTimeout.getText().toString();
//        if(!TextUtils.isEmpty(strTimeout)){
//            try{
//                // 设置网络请求超时时间
//                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
//            }catch(Throwable e){
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();

    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
