package com.ygst.cenggeche.ui.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.view.explosion.ExplosionField;
import com.ygst.cenggeche.ui.widget.CheckableButton;
import com.ygst.cenggeche.ui.widget.shimmer.Shimmer;
import com.ygst.cenggeche.ui.widget.shimmer.ShimmerTextView;
import com.ygst.cenggeche.utils.UploagImgUrils;

import java.io.File;

import cn.jmessage.android.uikit.chatting.utils.FileHelper;

public class TestActivity2 extends AppCompatActivity {
    ShimmerTextView tv;
    Shimmer shimmer;
    ImageView cv1;
    ImageView mIvTest;
    FlowLayout flowLayout;
    String[] s = {"sssss", "sdsdsds", "ssdsdss", "fcvxcd", "dfdffffffffdgfgd", "dfgfgdfgdf", "gdfgdfgfg", "hjjks", "skjjjjjjjggggggggg", "ss", "sss", "sssss", "sssss"};
    private Integer[] mImageIds = {R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.f,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        mIvTest = (ImageView) findViewById(R.id.iv_test);
        cv1 = (ImageView) findViewById(R.id.civ_1);
        tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        Button btn = (Button) findViewById(R.id.shandong);

        for (int i = 0; i < s.length; i++) {
            TextView view = new TextView(this);
            view.setText(s[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundResource(R.drawable.button_bg);
            flowLayout.addView(view);
        }
        //放到所以控件初始化之后
        ExplosionField explosionField = new ExplosionField(this);
        //为TRUE时，点击一次就消失。FALSE会重生
        explosionField.addListener(findViewById(R.id.test), false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showChoosePhotoDialog();
                if (shimmer != null && shimmer.isAnimating()) {
                    shimmer.cancel();
                } else {
                    shimmer = new Shimmer();
                    shimmer.start(tv);
                }
            }
        });
    }

    private int SELECT_CAMER = 1101;
    private int SELECT_PICTURE = 1102;

    public void showChoosePhotoDialog() {
        CharSequence[] items = {"相册", "相机"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            chosePic();
                        } else {
                            takeCamera();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    String mCurrentPhotoPath = null;

    /**
     * 拍照1
     */
    private void takeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCurrentPhotoPath = FileHelper.createAvatarPath(null);
        File outputImage = new File(mCurrentPhotoPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCurrentPhotoPath)));
        startActivityForResult(intent, SELECT_CAMER);
    }


    /**
     * 209.  * 本地相册选择图片  * 210.
     */
    private void chosePic() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("image/*"); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, SELECT_PICTURE);
    }

    //选择图片或拍完照片之后触发
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = null;
        if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                path = UploagImgUrils.getRealFilePath(this, data.getData());
            }
        } else if (requestCode == SELECT_CAMER) {
            path = mCurrentPhotoPath;
        }
        // 获取手机屏幕的像素
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Resources s = getResources();
        setBackGround(path, dm, s);
    }


    //设置背景图片
    public void setBackGround(String path, DisplayMetrics dm, Resources s) {

        File imageFile = new File(path);
        Drawable drawable = Drawable.createFromPath(path);

        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmp = bd.getBitmap();

        //压缩图片
        bmp = Bitmap.createScaledBitmap(bmp, dm.widthPixels, dm.heightPixels, true);

        mIvTest.setBackground(new BitmapDrawable(s, bmp));

    }

    private void addChildTo(FlowLayout flowLayout) {
        for (int i = 'A'; i < 'Z'; i++) {
            Button btn = new CheckableButton(this);
            btn.setHeight(dp2px(32));
            btn.setTextSize(16);
            btn.setTextColor(getResources().getColorStateList(R.color.colorSub2));
            btn.setBackgroundResource(R.drawable.button_bg);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i - 'A' + 4; j++) {
                sb.append((char) i);
            }
            btn.setText(sb.toString());
            flowLayout.addView(btn);
        }
    }

    public int dp2px(int dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density);
    }
}
