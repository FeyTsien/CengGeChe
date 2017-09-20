package com.ygst.cenggeche.ui.activity.friendinfo;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/5.
 */

public class BigPicActivity extends BaseActivity {
    public static BigPicActivity instance;
    @BindView(R.id.iv_big_pic)
    PhotoView mIvBigPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_pic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String pic = intent.getStringExtra("pic_uri");
        Glide.with(this).load(pic).placeholder(R.mipmap.icon_my_avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(mIvBigPic);
//        Picasso.with(this).load(pic).placeholder(R.mipmap.icon_my_avatar).into(mIvBigPic);
    }

    public void showImg(String path){
        Glide.with(this).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mIvBigPic);
    }
    @OnClick(R.id.iv_big_pic)
    public void offBigPic() {
        finish();
    }
}
