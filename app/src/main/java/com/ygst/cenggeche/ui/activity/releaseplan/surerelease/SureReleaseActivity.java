package com.ygst.cenggeche.ui.activity.releaseplan.surerelease;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.manager.HttpManager;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.ui.activity.releaseplan.choosepic.ChoosePicActivity;
import com.ygst.cenggeche.ui.activity.travelinfo.TravelInfoActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.TakePhotoUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UploagImgUrils;
import com.ygst.cenggeche.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;

import static com.ygst.cenggeche.R.id.et_remark;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
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
    Button  mBtn_sure;
    @BindView(R.id.et_remark)
    EditText mEt_descriable;
    @BindView(R.id.ll_cartype)
    LinearLayout mLl_Cartype;
    @BindView(R.id.ll_cengche)
    LinearLayout mLl_cengche;
    @BindView(R.id.iv_chooseimg)
    ImageView iv_chooseimg;
    @BindView(R.id.upload_cancle)
    Button  btn_cancel;
    @BindView(R.id.upload_bt_galley)
    Button  btn_gralley;
    @BindView(R.id.upload_bt_camera)
    Button  btn_camera;
    @BindView(R.id.upload_take)
    Button  btn_take;
    @BindView(R.id.ll_takepic)
    RelativeLayout  ll_takepic;
    @BindView(R.id.rl_chooseimg)
    RelativeLayout rl_takepic;

    private String meEndlocation;
    private String mStartlocation;
    private String mTime;
    private String mType;
    private int stateuser;
    private String TAG="SureReleaseActivitya";
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
    public void goBack(){
        finish();
    }

    //取消框
    @OnClick(R.id.upload_cancle)
    public void btnCancel(){
        ll_takepic.setVisibility(View.GONE);
    }
    //选择相册
    @OnClick(R.id.upload_bt_galley)
    public void btngalley(){
        cameraTask(1);
        ll_takepic.setVisibility(View.GONE);
    }

    //拍照获取
    @OnClick(R.id.upload_bt_camera)
    public void btnCamera(){
        cameraTask(0);
        ll_takepic.setVisibility(View.GONE);

    }

    //选择相册
    @OnClick(R.id.rl_chooseimg)
    public void ivChoosepic(){
        ll_takepic.setVisibility(View.VISIBLE);
    }

    //资料中获取图片
    @OnClick(R.id.upload_take)
    public void takepic(){
        ll_takepic.setVisibility(View.VISIBLE);
//        CommonUtils.startActivity(SureReleaseActivity.this, ChoosePicActivity.class);
        Intent intent=new Intent(SureReleaseActivity.this, ChoosePicActivity.class);
        startActivityForResult(intent,333);
        ll_takepic.setVisibility(View.GONE);
        IsPicForData=true;
    }


    //为乘客的时候点击发布信息
    @OnClick(R.id.btn_nowrelease)
    public void nowrelease(){
        String comments = mEt_descriable.getText().toString().trim();
        //从资料中获取图片
        if(IsPicForData){
            if(filepath==null){
                ToastUtil.show(SureReleaseActivity.this,"不传照片想蹭车，想得美");
                return ;
            }
            Log.i(TAG,filepath+"====");
            if(stateuser==1){
                mPresenter.getSureRelease(1+"",mStartlocation,meEndlocation,mTime,"乘客","乘客",comments,filepath);
            }else if(stateuser==2){
                mPresenter.getSureRelease(2+"",mStartlocation,meEndlocation,mTime,mCarType,mCarColor,comments,filepath);
            }
            //拍照或者相册中选取
        }else{
            if(uppath!=null){
                if(stateuser==1){
                    mPresenter.getSureRelease(1+"",mStartlocation,meEndlocation,mTime,"乘客","乘客",comments,uppath);
                }else if(stateuser==2){
                    mPresenter.getSureRelease(2+"",mStartlocation,meEndlocation,mTime,mCarType,mCarColor,comments,uppath);
                }
            }else{
                ToastUtil.show(SureReleaseActivity.this,"不传照片想蹭车，想得美");
                return ;
            }
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.sure_release_iten;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvTitle.setText("发布行程");

        Intent intent = getIntent();
        stateuser = intent.getIntExtra("STATEUSER",1);

        mTime = intent.getStringExtra("TIME");
        meEndlocation = intent.getStringExtra("ENDLOACTION");
        mStartlocation = intent.getStringExtra("STARTLOACTION");

        mTv_release_date.setText(mTime);
        mTv_end_location.setText(meEndlocation);
        mTv_start_location.setText(mStartlocation);
        //捎人
        mTv_start_location.setText(mStartlocation);

        if(stateuser ==1){
            mLl_Cartype.setVisibility(View.GONE);
        }else{
            String type = intent.getStringExtra("TYPE");
            String[] split = type.split("-");
            mCarType = split[0]+split[1];
            mCarColor = split[2];
            mLl_Cartype.setVisibility(View.VISIBLE);
            mType = intent.getStringExtra("TYPE");
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
                mTv_num.setText(length+"/50");
                if(length==50){
                    ToastUtil.show(SureReleaseActivity.this,"最多输入50个字，不要贪心哟！");
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

        switch (requestCode) {
            case OPEN_CANMERA:
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
         /*       Bitmap photo = data.getParcelableExtra("data");
                Uri uri1 = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), photo, null, null));
                String file = UploagImgUrils.getRealFilePath(UploadIdCardActivity.this, uri1);
                jumActivity(file);*/

                File file = new File(dir + fileName);
                Log.i(TAG,file.getAbsolutePath()+"---");
                picpath=file.getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                iv_chooseimg.setImageBitmap(bitmap);
                mPresenter.uploadImg(picpath,SureReleaseActivity.this);

                Log.i(TAG,filepath+"-=OPEN_CANMERA-filepath");
                break;
            case OPEN_GALLEY:
                if (resultCode == RESULT_CANCELED) {
                }
                try {
                    Uri uri = data.getData();
                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // setDataJump(bitmap);
                    String file2 = UploagImgUrils.getRealFilePath(SureReleaseActivity.this, uri);
                    Log.i(TAG,file2+"===---");
                    picpath=file2;
                    Bitmap bitmaps = BitmapFactory.decodeFile(file2);
                    iv_chooseimg.setImageBitmap(bitmaps);
//                    jumActivity(file2);
                    Log.i(TAG,filepath+"-=OPEN_GALLEY-filepath");
                    mPresenter.uploadImg(picpath,SureReleaseActivity.this);
                } catch (Exception e) {
                }
                break;

            case 333:
                if(resultCode==444) {
                    filepath = data.getStringExtra("filepath");
                    Log.i(TAG, filepath + "-=333-filepath");
                    Glide.with(SureReleaseActivity.this).load(filepath).into(iv_chooseimg);
                }
                break;


        }

    }

    @Override
    public void sureReleaseSuccess() {
        CommonUtils.startActivity(SureReleaseActivity.this, TravelInfoActivity.class);
        finish();
    }

    @Override
    public void sureReleaseFail(String msg) {

    }

    //上传图片成功与失败
    @Override
    public void uploadImgSuccess(String picpath) {
        uppath=picpath;

    }

    @Override
    public void uoloadFail() {
        ToastUtil.show(SureReleaseActivity.this,"上传图片失败，请重新上传");
    }

    public void cameraTask(final int state) {
        switch (state) {
            case 0:
                dir = new File(Environment.getExternalStorageDirectory() + "/DataCollect/");
                if (!dir.exists()) dir.mkdirs();
                fileName = String.valueOf(System.currentTimeMillis()) + ".JPEG";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            /*    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dir + fileName)));
                                startActivityForResult(intent, OPEN_CANMERA);*/

                            /*获取当前系统的android版本号*/
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                Log.e("currentapiVersion", "currentapiVersion====>" + currentapiVersion);
                if (currentapiVersion < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dir + fileName)));
                    startActivityForResult(intent, OPEN_CANMERA);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, dir + fileName);
                    Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, OPEN_CANMERA);
                }
                       /*                   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, OPEN_CANMERA);;*/
                break;
            case 1:
                TakePhotoUtils.openGalley(SureReleaseActivity.this, OPEN_GALLEY);
                break;
        }
    }



}
