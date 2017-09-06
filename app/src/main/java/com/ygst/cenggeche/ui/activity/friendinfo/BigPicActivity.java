package com.ygst.cenggeche.ui.activity.friendinfo;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/5.
 */

public class BigPicActivity extends BaseActivity {

    @BindView(R.id.iv_big_pic)
    ImageView mIvBigPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_pic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String pic = getIntent().getStringExtra("pic_uri");
//        Picasso.with(this).load(pic).placeholder(R.mipmap.icon_my_avatar).into(mIvBigPic);
        Glide.with(this).load(pic).placeholder(R.mipmap.icon_my_avatar).into(mIvBigPic);
    }

    @OnClick(R.id.iv_big_pic)
    public void offBigPic() {
        finish();
    }
}
