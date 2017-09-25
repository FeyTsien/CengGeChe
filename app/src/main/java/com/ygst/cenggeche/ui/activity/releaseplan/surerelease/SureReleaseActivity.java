package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.choosepic.ChoosePicActivity;
import com.ygst.cenggeche.ui.activity.travelinfo.TravelInfoActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.TakePhotoUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UploagImgUrils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jmessage.android.uikit.chatting.utils.FileHelper;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SureReleaseActivity extends MVPBaseActivity<SureReleaseContract.View, SureReleasePresenter> implements SureReleaseContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_release_date)
    TextView mTv_release_date;
    TextView mTv_title_right;
    @BindView(R.id.tv_start_location)
    TextView mTv_start_location;
    @BindView(R.id.tv_end_location)
    TextView mTv_end_location;
    @BindView(R.id.tv_cartype)
    TextView mTv_cartype;
    @BindView(R.id.tv_num)
    TextView mTv_num;//字数限制
    @BindView(R.id.btn_nowrelease)
    Button mBtn_sure;
    @BindView(R.id.et_remark)
    EditText mEt_descriable;
//    @BindView(R.id.et_usercar_type)
//    EditText mEt_usercar_type;


    @BindView(R.id.ll_cartype)
    LinearLayout mLl_Cartype;
    @BindView(R.id.ll_cengche)
    LinearLayout mLl_cengche;
    @BindView(R.id.iv_chooseimg)
    ImageView iv_chooseimg;
    @BindView(R.id.upload_cancle)
    Button btn_cancel;
    @BindView(R.id.upload_bt_galley)
    Button btn_gralley;
    @BindView(R.id.upload_bt_camera)
    Button btn_camera;
    @BindView(R.id.upload_take)
    Button btn_take;
    @BindView(R.id.ll_takepic)
    RelativeLayout ll_takepic;
    @BindView(R.id.rl_chooseimg)
    RelativeLayout rl_takepic;

    private String meEndlocation;
    private String mStartlocation;
    private String mTime;
    private String mType;
    private int stateuser;
    private String TAG = "SureReleaseActivitya";
    private String mCarColor;
    private String mCarType;
    public final int OPEN_CANMERA = 111;
    public final int OPEN_GALLEY = 222;
    private File dir;
    private String fileName;
    private String filepath;
    private String picpath;
    private String uppath;

    private boolean IsPicForData;//判断是否是从资料中获取图片

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }

    //取消框
    @OnClick(R.id.upload_cancle)
    public void btnCancel() {
        ll_takepic.setVisibility(View.GONE);
    }

    //选择相册
    @OnClick(R.id.upload_bt_galley)
    public void btngalley() {
        cameraTask(1);
        ll_takepic.setVisibility(View.GONE);
    }

    //拍照获取
    @OnClick(R.id.upload_bt_camera)
    public void btnCamera() {
        cameraTask(0);
        ll_takepic.setVisibility(View.GONE);
    }

    //选择相册
    @OnClick(R.id.rl_chooseimg)
    public void ivChoosepic() {
        ll_takepic.setVisibility(View.VISIBLE);
    }

    //资料中获取图片
    @OnClick(R.id.upload_take)
    public void takepic() {
        ll_takepic.setVisibility(View.VISIBLE);
//        CommonUtils.startActivity(SureReleaseActivity.this, ChoosePicActivity.class);
        Intent intent = new Intent(SureReleaseActivity.this, ChoosePicActivity.class);
        startActivityForResult(intent, 333);
        ll_takepic.setVisibility(View.GONE);
        IsPicForData = true;
    }


    //为乘客的时候点击发布信息
    @OnClick(R.id.btn_nowrelease)
    public void nowrelease() {
        String comments = mEt_descriable.getText().toString().trim();
        //从资料中获取图片
        if (IsPicForData) {
            if (filepath == null) {
                ToastUtil.show(SureReleaseActivity.this, "您还没有选择图片");
                return;
            }
            Log.i(TAG, filepath + "====");
            if (stateuser == 1) {
                mPresenter.getSureRelease(1 + "", mStartlocation, meEndlocation, mTime, "", "", comments, filepath);
            } else if (stateuser == 2) {
                mPresenter.getSureRelease(2 + "", mStartlocation, meEndlocation, mTime, mCarType, mCarColor, comments, filepath);
            }
            //拍照或者相册中选取
        } else {
            if (uppath != null) {
                if (stateuser == 1) {
                    mPresenter.getSureRelease(1 + "", mStartlocation, meEndlocation, mTime, "乘客", "乘客", comments, uppath);
                } else if (stateuser == 2) {
                    mPresenter.getSureRelease(2 + "", mStartlocation, meEndlocation, mTime, mCarType, mCarColor, comments, uppath);
                }
            } else {
                ToastUtil.show(SureReleaseActivity.this, "您还没有选择图片");
                return;
            }
        }

//        startActivityForResult(new Intent(ReleaseplanActivity.this, CartypeActivity.class),  3);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.sure_release_iten;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvTitle.setText("发布行程");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent intent = getIntent();
        stateuser = intent.getIntExtra("STATEUSER", 1);

        mTime = intent.getStringExtra("TIME");
        meEndlocation = intent.getStringExtra("ENDLOACTION");
        mStartlocation = intent.getStringExtra("STARTLOACTION");

        mTv_release_date.setText(mTime);
        mTv_end_location.setText(meEndlocation);
        mTv_start_location.setText(mStartlocation);
        //捎人
        mTv_start_location.setText(mStartlocation);
        //当前为乘客状态
        if (stateuser == 1) {
            mLl_Cartype.setVisibility(View.GONE);
        } else {
            mType = intent.getStringExtra("TYPE");
            String[] split = mType.split("-");
            mCarType = split[0] + split[1];
            mCarColor = split[2];
            mLl_Cartype.setVisibility(View.VISIBLE);
            mTv_cartype.setText(mType);
        }
        //输入框的监听
        mEt_descriable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                mTv_num.setText(length + "/50");
                if (length == 50) {
                    ToastUtil.show(SureReleaseActivity.this, "最多输入50个字，不要贪心哟！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case OPEN_CANMERA:
                mPresenter.uploadImg(fileName, SureReleaseActivity.this);
                Log.i(TAG, fileName + "-=OPEN_GALLEY-filepath");

                break;
            case OPEN_GALLEY:
                try {
                    Uri uri = data.getData();
                    String file2 = UploagImgUrils.getRealFilePath(SureReleaseActivity.this, uri);
                    picpath = file2;
                    Log.i(TAG, picpath + "-=OPEN_GALLEY-filepath");
                    mPresenter.uploadImg(picpath, SureReleaseActivity.this);
                } catch (Exception e) {
                }
                break;

            case 333:
                if (resultCode == 444) {
                    filepath = data.getStringExtra("filepath");
                    Glide.with(SureReleaseActivity.this).load(filepath).into(iv_chooseimg);
                }
                break;


        }

    }

    @Override
    public void sureReleaseSuccess() {
        Intent intent = new Intent(SureReleaseActivity.this,TravelInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void sureReleaseFail(String msg) {
        ToastUtil.show(SureReleaseActivity.this, msg);
    }

    //上传图片成功与失败
    @Override
    public void uploadImgSuccess(String picpath) {
        uppath = picpath;
        Glide.with(SureReleaseActivity.this).load(picpath).into(iv_chooseimg);


    }

    @Override
    public void uoloadFail() {
        ToastUtil.show(SureReleaseActivity.this, "上传图片失败，请重新上传");
    }

    public void cameraTask(final int state) {
        switch (state) {
            case 0:
                if (checkSDcard()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileName = FileHelper.createAvatarPath(null);
                    dir = new File(fileName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileName)));
                    startActivityForResult(intent, OPEN_CANMERA);
                }
                break;
            case 1:
                TakePhotoUtils.openGalley(SureReleaseActivity.this, OPEN_GALLEY);
                break;
        }
    }

    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public final boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
            ToastUtil.show(this, "请插入手机存储卡再使用本功能");
        }
        return flag;
    }

}
