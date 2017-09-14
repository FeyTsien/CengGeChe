package com.ygst.cenggeche.ui.activity.suretravel;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.itinerary.ItineraryActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.surerelease.SureReleaseActivity;
import com.ygst.cenggeche.ui.activity.travelinfo.TravelInfoActivity;
import com.ygst.cenggeche.ui.view.PickValueView;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.analytics.pro.x.S;
import static com.ygst.cenggeche.R.id.et_start_action;
import static com.ygst.cenggeche.R.id.pickValue;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SureTravelActivity extends MVPBaseActivity<SureTravelContract.View, SureTravelPresenter> implements SureTravelContract.View,View.OnClickListener,PickValueView.onSelectedChangeListener{

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private EditText mEtStartAction;
    private EditText mEtEndAction;
    private EditText mEtUsercarTime;
    private Button mButReleasePlan;
    private EditText mEtUsercarType;
    private LinearLayout mUserdCarLayout;
    private String mStringEnd,mStringStart,mStringType,mStringTime;
    private boolean isStart,isEnd,isType,isTime,isCengche;
    private String TAG="ReleaseplanActivity";
    private MapView mMapView;
    private AMap aMap;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private TextView mTvFinish;
    private PickValueView pickValueView;
    private LinearLayout mChooseLayout;

    private static  String  CHOOSETIME="今天-0时:0分";
    private Intent intent;
    private LinearLayout mLl_jumpLayout;
    private String comment;
    private String postedTime;
    private String startAddr;
    private String endAddr;

    @OnClick(R.id.iv_back)
    public void goBack(){
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_suretravel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suretravel);

        initView();
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        intent = getIntent();
        String cartype = intent.getStringExtra("CARTYPE");
        if(cartype.equals("1")){
            mUserdCarLayout.setVisibility(View.GONE);
            mLl_jumpLayout.setVisibility(View.GONE);
            mButReleasePlan.setVisibility(View.VISIBLE);
            isCengche=true;
        }else if(cartype.equals("2")){
            mUserdCarLayout.setVisibility(View.VISIBLE);
            mLl_jumpLayout.setVisibility(View.VISIBLE);
            isCengche=false;
        }
        endAddr = intent.getStringExtra("endAddr");
        startAddr = intent.getStringExtra("startAddr");
        postedTime = intent.getStringExtra("postedTime");
        mEtStartAction.setText(startAddr);
        mEtEndAction.setText(endAddr);
        mEtUsercarTime.setText(postedTime);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        mTvTitle.setText(AppData.getLocation());
        mEtStartAction.setFocusable(false);
        mEtEndAction.setFocusable(false);

    }

    //初始化控件
    private void initView() {
        mUserdCarLayout = (LinearLayout) findViewById(R.id.linear_usercar_type);
//        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mMapView = (MapView) findViewById(R.id.map);
        mEtStartAction = (EditText) findViewById(et_start_action);
        mEtEndAction = (EditText) findViewById(R.id.et_end_action);
        mEtUsercarTime = (EditText) findViewById(R.id.et_usercar_time);
        mEtUsercarType= (EditText) findViewById(R.id.et_usercar_type);
        mButReleasePlan = (Button) findViewById(R.id.but_release_plan);
        TextView mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvFinish = (TextView) findViewById(R.id.tv_finish);
        mChooseLayout = (LinearLayout) findViewById(R.id.ll_choosetime);
        TextView mTvjump = (TextView) findViewById(R.id.tv_jump);

        mLl_jumpLayout = (LinearLayout) findViewById(R.id.ll_jump);

        pickValueView = (PickValueView) findViewById(pickValue);
        pickValueView.setOnSelectedChangeListener(this);
        mEtStartAction.setOnClickListener(this);
        mEtEndAction.setOnClickListener(this);
        mEtUsercarTime.setOnClickListener(this);
        mButReleasePlan.setOnClickListener(this);

        mTvCancel.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mTvjump.setOnClickListener(this);
        pickValueView.setOnSelectedChangeListener(this);

        //输入框的监听事件
        mEtStartAction.addTextChangedListener(textWatchStart);
        mEtEndAction.addTextChangedListener(textWatchEnd);
        mEtUsercarTime.addTextChangedListener(textWatchTime);
        mEtUsercarType.addTextChangedListener(textWatchType);
        mEtUsercarType.setFocusable(false);
        mEtStartAction.setFocusable(false);
        mEtEndAction.setFocusable(false);
        mEtUsercarTime.setFocusable(false);
        aMap = mMapView.getMap();
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_BUS);// 卫星地图模式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);
//    MyLocationStyle strokeColor(545454);
        myLocationStyle.strokeColor(12222);
        //设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(725542);
        initLocation();
        startLocation();

        aMap.getUiSettings().setZoomControlsEnabled(false);

        UiSettings uiSettings =  aMap.getUiSettings();

        uiSettings.setLogoBottomMargin(-50);
        UiSettings uiSettings1 = aMap.getUiSettings();
        uiSettings1.setZoomPosition(100);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.et_usercar_time:
                mChooseLayout.setVisibility(View.VISIBLE);
                String left[]=new String[]{"今天","明天"};
                String middle[]=new String[24];
                String right[]=new String[60];
                for (int i = 0; i <24 ; i++) {
                    middle[i] = i+"时";
                }
                for (int i = 0; i <60 ; i++) {
                    right[i] = i+"分";
                }

                pickValueView.setValueData(left,left[0],middle,middle[0],right,right[0]);
                break;
            case R.id.et_start_action:
                startActivityForResult(new Intent(SureTravelActivity.this, SureAddrActivity.class),  1);
                break;
            case R.id.et_end_action:
                startActivityForResult(new Intent(SureTravelActivity.this, SureAddrActivity.class),  2);
                break;


            case R.id.but_release_plan:
                if(judgeIsNull()){
                    mStringEnd=mEtEndAction.getText().toString().trim();
                    mStringStart=mEtStartAction.getText().toString().trim();
                    mStringTime=mEtUsercarTime.getText().toString().trim();
                    mStringType=mEtUsercarType.getText().toString().trim();
                    Intent intents = new Intent(SureTravelActivity.this, SureReleaseActivity.class);
                    intents.putExtra("ENDLOACTION",endAddr);
                    intents.putExtra("STARTLOACTION",startAddr);
                    intents.putExtra("TIME",postedTime);
                    intents.putExtra("comment",comment);
                    startActivity(intents);
                }
                break;

            case R.id.tv_cancel:
                mChooseLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_finish:
                mChooseLayout.setVisibility(View.GONE);
                String[] split = CHOOSETIME.split("-");
                String time = getTime();
                mEtUsercarTime.setText(time+" "+split[1]);
                break;

            case R.id.tv_jump:

                mStringEnd=mEtEndAction.getText().toString().trim();
                mStringStart=mEtStartAction.getText().toString().trim();
                mStringTime=mEtUsercarTime.getText().toString().trim();
                mStringType=mEtUsercarType.getText().toString().trim();
                Intent intents = new Intent(SureTravelActivity.this, SureReleaseActivity.class);
                intents.putExtra("ENDLOACTION",endAddr);
                intents.putExtra("STARTLOACTION",startAddr);
                intents.putExtra("TIME",postedTime);
                intents.putExtra("comment",comment);
                startActivity(intents);
                break;


        }
    }

    public String getTime(){
        String[] split = CHOOSETIME.split("-");
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if(split[0].equals("明天")){
            long time = System.currentTimeMillis();
            time=time+86400000;
            Date dates = new Date(time);
            String times=format.format(dates);
            return times;
        }else{
            Date date=new Date();
            String time=format.format(date);
            return time;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==3){
            String result = data.getExtras().getString("result");
            mEtStartAction.setText(result);
        }else  if(requestCode==2&&resultCode==3){
            String result = data.getExtras().getString("result");

            mEtEndAction.setText(result);
        }

    }

 /*
    输入框是否有数值
    */

    public boolean judgeIsNull(){
        Log.i(TAG,isTime+"--"+isType+isCengche+isEnd);

        //蹭车是 车辆信息隐藏
        if(isCengche){
            showrelease();
            return true;
        }else{
            if(isType){
                showrelease();
                return true;
            }else {
                mButReleasePlan.setVisibility(View.GONE);

            }
        }
        return false;
    }

    public void showrelease(){
        if(isStart&&isEnd&&isTime){
            mButReleasePlan.setVisibility(View.VISIBLE);
        }else{
            mButReleasePlan.setVisibility(View.GONE);
        }
    }

    /*
    输入框的监听
    */

    private TextWatcher textWatchStart = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
            Log.i(TAG,s+"--beforeTextChanged");

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            Log.i(TAG,s+"--char");
            Log.i(TAG,s.toString().length()+"--char");

            if(s.length()>0){
                isStart=true;
            }else{
                isStart=false;
            }
            judgeIsNull();
        }
        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
        }
    };

    private TextWatcher textWatchEnd = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            if(s.length()>0){
                isEnd=true;
            }else{
                isEnd=false;
            }
            judgeIsNull();
        }
        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
        }
    };

    private TextWatcher textWatchTime = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            if(s.length()>0){
                isTime=true;
            }else{
                isTime=false;
            }
            judgeIsNull();
        }
        @Override
        public void afterTextChanged(Editable s) {
            //s:变化后的所有字符
        }
    };

    private TextWatcher textWatchType = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            if(s.length()>0){
                isType=true;
            }else{
                isType=false;
            }
            judgeIsNull();
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
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
                if(location.getErrorCode() == 0){
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

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
                Log.i(TAG,result+"===");

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
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
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
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
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
    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        destroyLocation();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }


    //时间滚轮 得到时间
    @Override
    public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
        CHOOSETIME =leftValue.toString()+"-"+middleValue.toString()+":"+rightValue.toString();
        LogUtils.i(TAG,leftValue.toString()+"-"+middleValue.toString()+":"+rightValue.toString());

    }
}
