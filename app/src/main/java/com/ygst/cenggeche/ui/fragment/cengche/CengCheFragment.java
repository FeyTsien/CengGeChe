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
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.bean.CheckPeerBean;
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

import static android.R.attr.id;

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
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.ll_cenglayout)
    RelativeLayout ll_cenglayout;

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
    public static int PERSONSTATUS = 0;//判断是蹭车 1还是捎人2的状态
    private int ChangeStatus=1;//头部切换 蹭车1 捎人2 的状态判断
    private int uid;

    //点击事件的处理
    @OnClick({R.id.iv_take_her, R.id.iv_release_plan, R.id.iv_trip_info, R.id.iv_cengtache})
    public void onclick(View v) {
        //登录状态
        if (AppData.isLoginEd()) {
            switch (v.getId()) {
                case R.id.iv_release_plan:
                    mPresenter.checkApplyinfo();

//                    Intent intent = new Intent(getActivity(), ReleaseplanActivity.class);
//                            intent.putExtra("REQUEST", 1 + "");
//                            startActivity(intent);


                    break;
                case R.id.iv_take_her://蹭他车的点击事件
                    PERSONSTATUS = 1;
                    mPresenter.checkApplyPeerInfo(1 + "", sid + "");

                    break;
                case R.id.iv_trip_info:
                    CommonUtils.startActivity(getActivity(), TravelInfoActivity.class);
                    break;
                case R.id.iv_cengtache://带上她的点击事件
                    PERSONSTATUS = 2;
                    mPresenter.checkApplyPeerInfo(2 + "", sid + "");
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
        mPresenter.getAllinfo(2 + "", PAGE);
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
                endAddr = list.get(position).getEndAddr();
                startAddr = list.get(position).getStartAddr();
                postedTime = list.get(position).getDeparTime();
                comment = list.get(position).getComments();
                sid = list.get(position).getId();
                uid = list.get(position).getUid();


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
                        //根据滑动的距离来切换界面
                        if (list.size() == allStrokeBean.getTotal()) {

                        } else {
//                            if (list.size() == allStrokeBean.getTotal()) {
//                                ToastUtil.show(getActivity(), "没有数据了哟");
//                            }



                            if (currentPage == list.size() - 1) {
                                ++PAGE;
                                if(ChangeStatus==2){
                                    mPresenter.getAllinfo(1 + "", PAGE);
                                    ToastUtil.show(getActivity(), "蹭车滑动");

                                }else if(ChangeStatus==1){
                                    mPresenter.getAllinfo(2 + "", PAGE);

                                    ToastUtil.show(getActivity(), "捎人滑动");
                                }
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
        list.addAll(allStrokeBean.getData());
        Log.i(TAG, list.size() + "===数据多少==");
        if (list.size() == 0) {
            ToastUtil.show(getActivity(), "没有数据了哟");
            ll_cenglayout.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);

        } else {
            sid = list.get(0).getId();
            endAddr = list.get(0).getEndAddr();
            postedTime = list.get(0).getDeparTime();
            startAddr = list.get(0).getStartAddr();
            comment = list.get(0).getComments();
            uid = list.get(0).getUid();

            LogUtils.i(TAG, "endAddr:" + endAddr + "==" + startAddr + "startAddr");
            ll_cenglayout.setVisibility(View.VISIBLE);
            rlEmpty.setVisibility(View.GONE);

        }
        setData();

    }

    //检测是否可以发布行程
    @Override
    public void getAllInfoFail() {
        Log.i(TAG, "fails");
        rlEmpty.setVisibility(View.VISIBLE);
        ll_cenglayout.setVisibility(View.GONE);
    }

    @Override
    public void checkApplySuccess(String msg) {

        if (msg != null) {
            int i = Integer.parseInt(msg);
            if (i >= 3) {
                ToastUtil.show(getActivity(), "今日您已取消三次行程了，请改日发布行程吧");
                return;
            } else {
                Intent intent = new Intent(getActivity(), ReleaseplanActivity.class);
                intent.putExtra("REQUEST", 1 + "");
                startActivity(intent);
            }
        }

    }


    //检测是否可以申请同行
    @Override
    public void checkApplyFail(String msg) {
        ToastUtil.show(getActivity(), msg);
    }



    //检测是否申请同行成功与否
    @Override
    public void checkApplyPeerSuccess(CheckPeerBean checkPeerBean) {
        Intent intent = new Intent(getActivity(), SureTravelActivity.class);
        //蹭车行程
        if (PERSONSTATUS == 1) {
            //判断男女
            String genders = AppData.getGenders();
            if (genders != null) {
                int i = Integer.parseInt(genders);
                if (i == 1) {
                    ToastUtil.show(getActivity(), "男性乘客不能蹭车");
                    return;
                }
            }
            intent.putExtra("CARTYPE", 1 + "");//CARTYPE==1 是蹭车

            //捎人行程
        } else if (PERSONSTATUS == 2) {
            intent.putExtra("CARTYPE", 2 + "");//CARTYPE==2 是捎人
            int userStatus = AppData.getUserStatus();
            if (userStatus != 1) {
                ToastUtil.show(getActivity(),"你还没有车主认证");
            }
        }

        //判断data里面是否有数据，有数据则待会给地图信息里面。并传STtrokeflag为3.为空则传2
        if (checkPeerBean.getData().getUid() != 0) {
            LogUtils.i(TAG, checkPeerBean.getData() + "==checkPeerBean.getData()");
            intent.putExtra("REQUEST", 2 + "");
            intent.putExtra("endAddr", checkPeerBean.getData().getEndAddr());
            intent.putExtra("startAddr", checkPeerBean.getData().getStartAddr());
            intent.putExtra("postedTime", checkPeerBean.getData().getDeparTime());
            intent.putExtra("strokeFlag", "2");
            intent.putExtra("comment", checkPeerBean.getData().getComments());
            intent.putExtra("id", checkPeerBean.getData().getId() + "");
            intent.putExtra("sid", sid + "");
            intent.putExtra("uid", uid + "");
            LogUtils.i(TAG, endAddr + "==data为空时则当前用户没有行程=" + startAddr);

            //data为空时则当前用户没有行程
        } else {
            intent.putExtra("strokeFlag", "3");
            intent.putExtra("REQUEST", 2 + "");
            intent.putExtra("endAddr", endAddr);
            intent.putExtra("startAddr", startAddr);
            intent.putExtra("postedTime", postedTime);
            intent.putExtra("comment", comment);
            intent.putExtra("id", "");
            intent.putExtra("uid", uid + "");
            intent.putExtra("sid", sid + "");
            LogUtils.i(TAG, endAddr + "===" + startAddr);
        }

        LogUtils.i(TAG, id + "===" + uid);
        startActivity(intent);
    }


    @Override
    public void checkApplyPeerFail(String msg) {
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
                ChangeStatus=1;
                ivCengTaChe.setVisibility(View.VISIBLE);
                ivTakeHer.setVisibility(View.GONE);
                btnCengche.setTextColor(getResources().getColor(R.color.colorTheme));
                btnCengche.setBackgroundResource(R.drawable.button_cengche1);
                btnShaoren.setTextColor(getResources().getColor(R.color.white));
                btnShaoren.setBackgroundResource(R.drawable.button_shaoren2);
                list.clear();
                mPresenter.getAllinfo(2 + "", PAGE);
                setData();
                break;
            case R.id.btn_shaoren:
                ChangeStatus=2;
                ivCengTaChe.setVisibility(View.GONE);
                ivTakeHer.setVisibility(View.VISIBLE);
                btnShaoren.setTextColor(getResources().getColor(R.color.colorTheme));
                btnShaoren.setBackgroundResource(R.drawable.button_shaoren1);
                btnCengche.setTextColor(getResources().getColor(R.color.white));
                btnCengche.setBackgroundResource(R.drawable.button_cengche2);
                list.clear();
                mPresenter.getAllinfo(1 + "", PAGE);
                setData();
//                mAdapter.notifyDataSetChanged();
                break;
        }
    }


}
