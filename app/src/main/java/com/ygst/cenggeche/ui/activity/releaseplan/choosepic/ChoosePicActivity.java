package com.ygst.cenggeche.ui.activity.releaseplan.choosepic;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.bean.ChoosePicBean;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.view.NoScrollGridView;
import com.ygst.cenggeche.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChoosePicActivity extends MVPBaseActivity<ChoosePicContract.View, ChoosePicPresenter> implements ChoosePicContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    NoScrollGridView mGridView;
    private ArrayList<String> picList;
    private String TAG=this.getClass().getSimpleName();
    private ChooseAdapter chooseAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choosepic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter.getUserPic();
        tvTitle.setText("选择图片");

        getPermission();
    }



    @Override
    public void getUserPicSuccess(ChoosePicBean choosePicBean) {
        picList =choosePicBean.getPic();

        Log.i(TAG,picList.size()+"===");
        chooseAdapter = new ChooseAdapter(this, picList);
        if(picList.size()==0){
            CommonUtils.showInfoDialog(ChoosePicActivity.this, "你的资料里面没有图片", "提示", "确定", "", null, null);

        }
        mGridView.setAdapter(chooseAdapter);

        chooseAdapter.setmOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.i(TAG,picList.get(postion)+"----");
                Intent intent=new Intent();
                intent.putExtra("filepath",picList.get(postion));
                setResult(444,intent);
                finish();
            }
        });
    }

    @Override
    public void getUserPicFail() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked( ) {
                finish();
    }

    public void getPermission(){
        PackageManager packageManager =getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                packageManager.checkPermission("android.permission.CAMERA", "com.ygst.cenggeche"));
        boolean permission1 = (PackageManager.PERMISSION_GRANTED ==
                packageManager.checkPermission("android.permission.READ_EXTERNAL_STORAGE", "com.ygst.cenggeche"));
        if(permission1&&permission){
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }

    }

}
