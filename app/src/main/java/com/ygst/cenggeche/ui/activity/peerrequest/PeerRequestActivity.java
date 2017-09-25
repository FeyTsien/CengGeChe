package com.ygst.cenggeche.ui.activity.peerrequest;


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

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseActivity;

import com.ygst.cenggeche.ui.activity.peerrequest.chooseimg.ChooseImgActivity;

import com.ygst.cenggeche.ui.activity.travelinfo.TravelInfoActivity;
import com.ygst.cenggeche.utils.CommonUtils;
import com.ygst.cenggeche.utils.TakePhotoUtils;
import com.ygst.cenggeche.utils.ToastUtil;
import com.ygst.cenggeche.utils.UploagImgUrils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PeerRequestActivity extends MVPBaseActivity<PeerRequestContract.View, PeerRequestPresenter> implements PeerRequestContract.View {

    private static  String ISJUMP= "" ;
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
    RelativeLayout ll_takepic;
    @BindView(R.id.rl_chooseimg)
    RelativeLayout rl_takepic;

    private String meEndlocation;
    private String mStartlocation;
    private String mTime;
    private String mType;
    private int stateuser;
    private String TAG="PeerRequestActivity";
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
    private String userid;
    private String id;
    private String mStrokeFlag;
    private String sid;
    private String UserStatus;

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
        Intent intent=new Intent(PeerRequestActivity.this, ChooseImgActivity.class);
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
                ToastUtil.show(PeerRequestActivity.this,"当前没有选择图片");
                return ;
            }
            Log.i(TAG,filepath+"===="+id);
            if(UserStatus.equals("1")){
                LogUtils.i(TAG,stateuser+"--IsPicForDatastateuser");
                mPresenter.getSureRelease(1+"",sid,userid,id,mStartlocation,meEndlocation,mTime,"","",mStrokeFlag,comments,filepath);
            }else if(UserStatus.equals("2")){
                //资料中选取，跳过车辆信息
                if(ISJUMP.equals("ISJUMP")){
                    mPresenter.getSureRelease(2+"",sid,userid,id,mStartlocation,meEndlocation,mTime,"","",mStrokeFlag,comments,filepath);
                }else{
                    mPresenter.getSureRelease(2+"",sid,userid,id,mStartlocation,meEndlocation,mTime,mCarType,mCarColor,mStrokeFlag,comments,filepath);
                }
            }
            //拍照或者相册中选取
        }else{
            if(uppath!=null){
                if(UserStatus.equals("1")){//为乘客 做别人的车
                    LogUtils.i(TAG,stateuser+"--stateuser");
                    mPresenter.getSureRelease(1+"",sid,userid,id,mStartlocation,meEndlocation,mTime,"","",mStrokeFlag,comments,uppath);
                }else if(UserStatus.equals("2")){//车主 带别人
                    LogUtils.i(TAG,stateuser+"--stateuser");
                    //判断是否为跳过
                    if(ISJUMP.equals("ISJUMP")){
                        mPresenter.getSureRelease(2+"",sid,userid,id,mStartlocation,meEndlocation,mTime,"","",mStrokeFlag,comments,uppath);
                    }else{
                        mPresenter.getSureRelease(2+"",sid,userid,id,mStartlocation,meEndlocation,mTime,mCarType,mCarColor,mStrokeFlag,comments,uppath);
                    }
                }
            }else{
                ToastUtil.show(PeerRequestActivity.this,"当前没有选择图片");
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
        mTvTitle.setText("申请同行");
        Intent intent = getIntent();
        stateuser = intent.getIntExtra("STATEUSER",1);

        mTime = intent.getStringExtra("TIME");
        meEndlocation = intent.getStringExtra("ENDLOACTION");
        mStartlocation = intent.getStringExtra("STARTLOACTION");
        mStrokeFlag = intent.getStringExtra("strokeFlag");
        id = intent.getStringExtra("id");
        userid = intent.getStringExtra("uid");
        sid = intent.getStringExtra("sid");
        UserStatus = intent.getStringExtra("cartype");
        ISJUMP = intent.getStringExtra("ISJUMP");

        LogUtils.i(TAG,UserStatus+"h=="+userid+"--sid:"+sid+"---id"+id);

        mTv_release_date.setText(mTime);
        mTv_end_location.setText(meEndlocation);
        mTv_start_location.setText(mStartlocation);
        //捎人
        mTv_start_location.setText(mStartlocation);
        if(stateuser ==1){
            mLl_Cartype.setVisibility(View.GONE);
        }else{
            String type = intent.getStringExtra("TYPE");
            mType = intent.getStringExtra("TYPECAR");
            String[] split = mType.split("-");
            mCarType = split[0]+split[1];
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
                mTv_num.setText(length+"/50");
                if(length==50){
                    ToastUtil.show(PeerRequestActivity.this,"最多输入50个字，不要贪心哟！");
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
                IsPicForData=false;
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
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                iv_chooseimg.setImageBitmap(bitmap);
                mPresenter.uploadImg(picpath,PeerRequestActivity.this);

                Log.i(TAG,filepath+"-=OPEN_CANMERA-filepath");
                break;
            case OPEN_GALLEY:
                IsPicForData=false;

                if (resultCode == RESULT_CANCELED) {
                }
                try {
                    Uri uri = data.getData();
                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    // setDataJump(bitmap);
                    String file2 = UploagImgUrils.getRealFilePath(PeerRequestActivity.this, uri);
                    Log.i(TAG,file2+"===---");
                    picpath=file2;
//                    Bitmap bitmaps = BitmapFactory.decodeFile(file2);
//                    iv_chooseimg.setImageBitmap(bitmaps);
//                    jumActivity(file2);
                    Log.i(TAG,filepath+"-=OPEN_GALLEY-filepath");
                    mPresenter.uploadImg(picpath,PeerRequestActivity.this);
                } catch (Exception e) {
                }
                break;

            case 333:
                IsPicForData=true;
                if(resultCode==444) {
                    filepath = data.getStringExtra("filepath");
                    Log.i(TAG, filepath + "-=333-filepath");
                    Glide.with(PeerRequestActivity.this).load(filepath).into(iv_chooseimg);
                }
                break;


        }

    }

    @Override
    public void sureReleaseSuccess() {
        CommonUtils.startActivity(PeerRequestActivity.this, TravelInfoActivity.class);
        finish();
    }

    @Override
    public void sureReleaseFail(String msg) {
        ToastUtil.show(PeerRequestActivity.this,msg);
    }

    //上传图片成功与失败
    @Override
    public void uploadImgSuccess(String picpath) {
        uppath=picpath;
        Glide.with(PeerRequestActivity.this).load(picpath).into(iv_chooseimg);

    }

    @Override
    public void uoloadFail() {
        ToastUtil.show(PeerRequestActivity.this,"上传图片失败，请重新上传");
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
                TakePhotoUtils.openGalley(PeerRequestActivity.this, OPEN_GALLEY);
                break;
        }
    }



}
