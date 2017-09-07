package com.ygst.cenggeche.ui.fragment.cengche;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.bean.AllStrokeBean;
import com.ygst.cenggeche.ui.activity.SplashActivity;
import com.ygst.cenggeche.ui.activity.login.LoginActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by Administrator on 2017/8/24.
 */

public class MyViewPagerAdapter extends PagerAdapter {

    private LinkedList<View> mViewCache = null;
    private  Context context;
    private ArrayList<AllStrokeBean.DataBean> list;
    private String TAG=this.getClass().getSimpleName();
    public MyViewPagerAdapter(Context context, ArrayList<AllStrokeBean.DataBean> list){
        this.context=context;
        this.list=list;
        mViewCache = new LinkedList<>();
    }



    @Override
    public int getCount() {
        Log.i(TAG,list.size()+"==");
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
       ViewHolder holder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = View.inflate(context, R.layout.item_viewpager, null);
            holder = new ViewHolder();
            holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_title_pic);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_exper_name);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_bottom);

            holder.tvEndLocation = (TextView) convertView.findViewById(R.id.tv_end_location);
            holder.tvStartLocation = (TextView) convertView.findViewById(R.id.tv_start_location);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tvReleaseDate = (TextView) convertView.findViewById(R.id.tv_release_date);

            convertView.setTag(holder);
        } else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }

        //赋值
        holder.tvUserName.setText(list.get(position).getNickname());
        holder.tvStartLocation.setText(list.get(position).getStartAddr());
        holder.tvEndLocation.setText(list.get(position).getEndAddr());
        holder.tvReleaseDate.setText(list.get(position).getPostedTime());
        holder.tvName.setText(list.get(position).getComments());
        Glide.with(context).load(list.get(position).getBackgroundPic()).into( holder.ivPic);
//			/* 动态设置view 横线 让它和上方的文字等宽*/
        holder.tvName.measure(0, 0);
        int measuredWidth = holder.tvName.getMeasuredWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(measuredWidth, 1);
        params.addRule(RelativeLayout.BELOW, R.id.tv_exper_name);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        container.addView(convertView);
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpuserInfo(list.get(position).getUid()+"");
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpuserInfo(list.get(position).getUid()+"");
            }
        });

        //点击事件
        return convertView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViewCache.add((View) object);
    }

    //View复用
    public final class ViewHolder {

        public TextView tvName;
        public TextView tvEndLocation,tvStartLocation,tvUserName,tvReleaseDate;
        public ImageView ivPic;
        public View vLine;
        public RelativeLayout relativeLayout;
    }

    public void jumpuserInfo(String userid){

        if(AppData.isLoginEd()){
            Intent intent=new Intent(context, SplashActivity.class);
            intent.putExtra("USERID",userid);
            context.startActivity(intent);
        }else{
            Intent intent=new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }

    }
}