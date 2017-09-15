package com.ygst.cenggeche.ui.fragment.cengche;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CengCheFragment extends MVPBaseFragment<CengCheContract.View, CengChePresenter> implements CengCheContract.View {

    @BindView(R.id.btn_cengche)
    Button btnCengche;
    @BindView(R.id.btn_shaoren)
    Button btnShaoren;
    private View mCengCheView;
    private ViewPager mViewPager;
    @BindView(R.id.iv_trip_info)
    ImageView ivTripInfo;
    @BindView(R.id.iv_release_plan)
    ImageView ivReleasePlan;
    @BindView(R.id.iv_take_her)
    ImageView ivTakeHer;
    @BindView(R.id.iv_cengtache)
    ImageView ivCengTaChe;
//    @BindView(R.id.rl_empty)
//    RelativeLayout rlEmpty;
//    @BindView(R.id.ll_cenglayout)
//    LinearLayout ll_cenglayout;

    private String TAG = "CengCheFragment";
    private ArrayList<AllStrokeBean.DataBean> list;
    private MyViewPagerAdapter mAdapter;
    private int PAGE = 1;

    private int currentPage;
    private AllStrokeBean allStrokeBean;
    private int sid;
    private String endAddr;
    private String comment;
    private String postedTime;
    private String startAddr;

    private int PERSONSTATUS = 0;


    //点击事件的处理
    @OnClick({R.id.iv_take_her, R.id.iv_release_plan, R.id.iv_trip_info, R.id.iv_cengtache})
    public void onclick(View v) {
        //登录状态
        if (AppData.isLoginEd()) {
            switch (v.getId()) {
                case R.id.iv_release_plan:
                    CommonUtils.startActivity(getActivity(), ReleaseplanActivity.class);
                    break;
                case R.id.iv_take_her:
                    PERSONSTATUS = 1;
                    mPresenter.checkApplyinfo("1", sid + "");

                    String genders = AppData.getGenders();
                    if(genders!=null){
                        int i = Integer.parseInt(genders);
                        if(i==1){
                            ToastUtil.show(getActivity(),"男性乘客不能蹭车");
                        }else if(i==0){
                            mPresenter.checkApplyinfo("2", sid + "");
                        }

                    }else{

                    }
//                    if(endAddr!=null&&startAddr!=null&&postedTime!=null&&comment!=null) {
//                        intent.putExtra("endAddr", endAddr);
//                        intent.putExtra("startAddr", startAddr);
//                        intent.putExtra("postedTime", postedTime);
//                        intent.putExtra("comment", comment);
//                    } else{
//                        ToastUtil.show(getActivity(),"当前界面无数据");
//                    }
                    break;
                case R.id.iv_trip_info:
                    CommonUtils.startActivity(getActivity(), TravelInfoActivity.class);
                    break;
                case R.id.iv_cengtache:
                    PERSONSTATUS = 2;
                    mPresenter.checkApplyinfo("2", sid + "");

                    break;

            }//跳转到登录界面
        } else {
            CommonUtils.startActivity(getActivity(), LoginActivity.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCengCheView = inflater.inflate(R.layout.fragment_cengche, container, false);
        ButterKnife.bind(this, mCengCheView);
        mPresenter.getAllinfo(1 + "", PAGE);
        initView();

        return mCengCheView;
    }

    //初始化控件
    private void initView() {
        mViewPager = (ViewPager) mCengCheView.findViewById(R.id.tab_viewpager);
        //设置viewpagaer的样式
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin));
        list = new ArrayList<AllStrokeBean.DataBean>();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, position + "==onPageScrolled=");
            }

            @Override
            public void onPageSelected(int position) {
                //currentpage后面要用
                currentPage = position;
                Log.i(TAG, position + "==onPageSelected=");
//                endAddr = list.get(position).getEndAddr();
//                startAddr = list.get(position).getStartAddr();
//                postedTime = list.get(position).getPostedTime();
//                comment = list.get(position).getComments();
                sid = list.get(position).getId();


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置viewpager的点击事件
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
                        if (list.size() == allStrokeBean.getTotal()) {

                        } else {
                            if (list.size() == allStrokeBean.getTotal()) {
                                ToastUtil.show(getActivity(), "没有数据了哟");

                            }

                            if (currentPage == list.size() - 1) {
                                ++PAGE;
                                mPresenter.getAllinfo(1 + "", PAGE);
                            }
                        }

                        break;
                }
                return false;
            }
        });

    }


    //获取成功数据
    @Override
    public void getAllInfoSuccess(AllStrokeBean allStrokeBean) {
        this.allStrokeBean = allStrokeBean;
        if (list.size() == allStrokeBean.getTotal()) {
            ToastUtil.show(getActivity(), "没有数据了哟");
        } else {
            list.addAll(allStrokeBean.getData());
            sid = list.get(0).getId();
            endAddr = list.get(0).getEndAddr();
            postedTime = list.get(0).getPostedTime();
            startAddr = list.get(0).getStartAddr();
            comment = list.get(0).getComments();

        }
        setData();
//        rlEmpty.setVisibility(View.GONE);
//        ll_cenglayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getAllInfoFail() {
        Log.i(TAG, "fails");
//        rlEmpty.setVisibility(View.VISIBLE);
//        ll_cenglayout.setVisibility(View.GONE);
    }

    @Override
    public void checkApplySuccess() {

        Log.i(TAG, PERSONSTATUS + "----PERSONSTATUS");
        Intent intent = new Intent(getActivity(), SureTravelActivity.class);
        if (PERSONSTATUS == 1) {
            intent.putExtra("CARTYPE", 1 + "");
        } else if (PERSONSTATUS == 2) {
            intent.putExtra("CARTYPE", 2 + "");

        }
        if (endAddr != null && startAddr != null && postedTime != null) {
            intent.putExtra("endAddr", endAddr);
            intent.putExtra("startAddr", startAddr);
            intent.putExtra("postedTime", postedTime);
            startActivity(intent);

        } else {
            ToastUtil.show(getActivity(), "当前界面无数据");
        }
//            int i = Integer.parseInt(num);
//            if(i<=3){
//                Intent intent = new Intent(getActivity(), SureTravelActivity.class);
//                //判断页面是否有数据
//                if (list.size() > 0) {
//                    intent.putExtra("CARTYPE", 1 + "");
//                    startActivity(intent);
//                } else {
//                    ToastUtil.show(getActivity(), "没数据别蹭了");
//                }
//            }else{
//
//            }

    }

    @Override
    public void checkApplyFail(String msg) {
        ToastUtil.show(getActivity(), msg);
    }

    public void setData() {
//        if(mAdapter==null){
        mAdapter = new MyViewPagerAdapter(getActivity(), list);
        mViewPager.setAdapter(mAdapter);
//        }else{
//            mAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    //切换蹭车和捎人点击事件
    @OnClick({R.id.btn_cengche, R.id.btn_shaoren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cengche:
                ivCengTaChe.setVisibility(View.VISIBLE);
                ivTakeHer.setVisibility(View.GONE);

                btnCengche.setTextColor(getResources().getColor(R.color.colorTheme));
                btnCengche.setBackgroundResource(R.drawable.button_cengche1);
                btnShaoren.setTextColor(getResources().getColor(R.color.white));
                btnShaoren.setBackgroundResource(R.drawable.button_shaoren2);
                list.clear();

                mPresenter.getAllinfo(1 + "", PAGE);

                setData();
                break;
            case R.id.btn_shaoren:


                ivCengTaChe.setVisibility(View.GONE);
                ivTakeHer.setVisibility(View.VISIBLE);

                btnShaoren.setTextColor(getResources().getColor(R.color.colorTheme));
                btnShaoren.setBackgroundResource(R.drawable.button_shaoren1);
                btnCengche.setTextColor(getResources().getColor(R.color.white));
                btnCengche.setBackgroundResource(R.drawable.button_cengche2);

                list.clear();
                mPresenter.getAllinfo(2 + "", PAGE);
                setData();
//                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
