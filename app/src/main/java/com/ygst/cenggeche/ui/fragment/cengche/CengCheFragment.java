package com.ygst.cenggeche.ui.fragment.cengche;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.ReleaseplanActivity;
import com.ygst.cenggeche.ui.activity.suretravel.SureTravelActivity;
import com.ygst.cenggeche.ui.activity.travelinfo.TravelInfoActivity;
import com.ygst.cenggeche.ui.view.ZoomOutPageTransformer;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ygst.cenggeche.R.id.container;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CengCheFragment extends MVPBaseFragment<CengCheContract.View, CengChePresenter> implements CengCheContract.View{

    private View mCengCheView;
    private ViewPager mViewPager;
    @BindView(R.id.iv_trip_info)
    ImageView ivTripInfo;
    @BindView(R.id.iv_release_plan)
    ImageView ivReleasePlan;
    @BindView(R.id.iv_take_her)
    ImageView ivTakeHer;
    private String TAG="CengCheFragment";
    private ArrayList<AllStrokeBean.DataBean> list;
    private MyViewPagerAdapter mAdapter;
    private int PAGE=1;

    private TextView mTvChangeCity;
    private FloatingActionButton mFabChangeCity;
    private int currentPage;
    @OnClick({R.id.iv_take_her, R.id.iv_release_plan, R.id.iv_trip_info})
    public void onclick(View v){
        //登录状态
        if(AppData.isLoginEd()){
            switch (v.getId()){
                case R.id.iv_release_plan:
                    CommonUtils.startActivity(getActivity(), ReleaseplanActivity.class);
                    break;
                case R.id.iv_take_her:
                    Intent intent=new Intent(getActivity(),SureTravelActivity.class);
                    intent.putExtra("CARTYPE",1+"");
                    startActivity(intent);
                    break;
                case R.id.iv_trip_info:
                    CommonUtils.startActivity(getActivity(), TravelInfoActivity.class);
                    break;
            }
        }else{
            CommonUtils.startActivity(getActivity(), LoginActivity.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCengCheView = inflater.inflate(R.layout.fragment_cengche, container, false);
        ButterKnife.bind(this,mCengCheView);
        mPresenter.getAllinfo(1+"",PAGE);
        initView();

        return mCengCheView;
    }

    //初始化控件
    private void initView() {
        mViewPager = (ViewPager) mCengCheView.findViewById(R.id.tab_viewpager);
        mTvChangeCity = (TextView) mCengCheView.findViewById(R.id.tv_citychange);
        mFabChangeCity = (FloatingActionButton) mCengCheView.findViewById(R.id.floatingActionButton);

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int currentItem = mViewPager.getCurrentItem();
//                Log.i(TAG,currentItem+"onPageSelected+++++"+position);
//
//                currentPage=position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin));
        list = new ArrayList<AllStrokeBean.DataBean>();

//        String  str="城"+'\n'+"市"+'\n'+"列"+'\n'+"表";
//        mTvChangeCity.setText(str);
        mTvChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getActivity(),"sss");
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //currentpage后面要用
                currentPage=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float endX;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();

                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
//获取屏幕的宽度
                        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

                        int width = wm.getDefaultDisplay().getWidth();
                        //根据滑动的距离来切换界面
                        if (currentPage == list.size()-1&& startX - endX >= (width / 5)) {
                            Toast.makeText(getActivity(),"ssss",Toast.LENGTH_SHORT).show();
                            Log.i(TAG,"sasas");

                        }

                        Log.i(TAG,"ssssssssssss");

                        break;
                }
                return false;
            }
        });

//        //触摸事件
//        mViewPager.setOnTouchListener(new View.OnTouchListener() {
//            float startX;
//            float endX;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        endX = event.getX();
//                        //获取屏幕的宽度
//                        WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
//                        int width = wm.getDefaultDisplay().getWidth();
//                        //根据滑动的距离来切换界面
//                        if (currentPage == list.size()-1 && startX - endX >= (width / 5)) {
//                            ToastUtil.show(getActivity(),"ssss");
//                            Log.i(TAG,"刷新了 ");
//
//                        }
//
//
//                        Log.i(TAG,currentPage+"=="+list.size()+"=="+startX +"=="+endX+"=="+width);
//                        break;
//                }
//                return false;
//            }
//        });



    }

    @Override
    public void getAllInfoSuccess(AllStrokeBean allStrokeBean) {
        list.addAll(allStrokeBean.getData());
        setData();
    }

    @Override
    public void getAllInfoFail() {
        Log.i(TAG,"fails");
    }

    public void setData(){
//        if(mAdapter==null) {
            mAdapter = new MyViewPagerAdapter(getActivity(), list);
            mViewPager.setAdapter(mAdapter);
//        }else{
//            mAdapter.notifyDataSetChanged();
//        }

    }
}
