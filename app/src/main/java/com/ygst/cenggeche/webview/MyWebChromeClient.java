package com.ygst.cenggeche.webview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

import cn.jmessage.android.uikit.chatting.utils.FileHelper;

import static android.app.Activity.RESULT_OK;


/**
 * Created by quantan.liu on 2017/3/29.
 * - 播放网络视频配置
 * - 上传图片(兼容)
 * 点击空白区域的左边,因是公司图片,自己编辑过,所以显示不全,见谅
 */
public class MyWebChromeClient extends WebChromeClient {

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private ValueCallback<String[]> mUploadMessageForAndroid51;
    public static int FILECHOOSER_RESULTCODE = 1;
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    public static int TAKE_PHOTO = 1;
    public static int REQ_CHOOSE = 2;

    private View mXProgressVideo;
    private WebViewActivity mActivity;
    private IWebPageView mIWebPageView;
    private View mXCustomView;
    private CustomViewCallback mXCustomViewCallback;

    public MyWebChromeClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        this.mActivity = (WebViewActivity) mIWebPageView;
    }

    // 播放网络视频时全屏会被调用的方法
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mIWebPageView.hindWebView();
        // 如果一个视图已经存在，那么立刻终止并新建一个
        if (mXCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }

        mActivity.fullViewAddView(view);
        mXCustomView = view;
        mXCustomViewCallback = callback;
        mIWebPageView.showVideoFullView();
    }

    // 视频播放退出全屏会被调用的
    @Override
    public void onHideCustomView() {
        if (mXCustomView == null)// 不是全屏播放状态
            return;
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mXCustomView.setVisibility(View.GONE);
        if (mActivity.getVideoFullView() != null) {
            mActivity.getVideoFullView().removeView(mXCustomView);
        }
        mXCustomView = null;
        mIWebPageView.hindVideoFullView();
        mXCustomViewCallback.onCustomViewHidden();
        mIWebPageView.showWebView();
    }

    // 视频加载时进程loading
    @Override
    public View getVideoLoadingProgressView() {
        if (mXProgressVideo == null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            mXProgressVideo = inflater.inflate(R.layout.video_loading_progress, null);
        }
        return mXProgressVideo;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mIWebPageView.progressChanged(newProgress);
    }

    /**
     * 判断是否是全屏
     */
    public boolean inCustomView() {
        return (mXCustomView != null);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // 设置title
        mActivity.setTitle(title);
        this.title = title;
    }

    private String title = "";

    public String getTitle() {
        return title + " ";
    }

    //扩展浏览器上传文件

    /**
     * For Android  >= 3.0
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooserImpl(uploadMsg);
    }

    /**
     * For Android < 3.0
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooserImpl(uploadMsg);
    }

    /**
     * For Android  >= 4.1
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooserImpl(uploadMsg);
    }

    // For Android >= 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
        openFileChooserImplForAndroid5(uploadMsg);
        return true;
    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        dispatchTakePictureIntent();
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        dispatchTakePictureIntent();
    }

    //拍照
    private void dispatchTakePictureIntent() {
        selectImgDialog();
    }

    protected final void selectImgDialog() {
        if (!checkSDcard())
            return;
        String[] selectPicTypeStr = {"拍照", "相册"};
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    // 相机拍摄
                                    case 0:
                                        takeCamera();
                                        break;
                                    // 手机相册
                                    case 1:
                                        chosePic();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mUploadMessageForAndroid5 != null) {
                            Uri[] uris = new Uri[1];
                            uris[0] = Uri.parse("");
                            mUploadMessageForAndroid5.onReceiveValue(uris);
                            mUploadMessageForAndroid5 = null;
                        } else {
                            mUploadMessage.onReceiveValue(Uri.parse(""));
                            mUploadMessage = null;
                        }
                    }
                }).show();
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
            ToastUtil.show(mActivity, "请插入手机存储卡再使用本功能");
        }
        return flag;
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
        mActivity.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            Uri imageUri = null;
            try {
                imageUri = Uri.fromFile(createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            mActivity.startActivityForResult(takePictureIntent, TAKE_PHOTO);
        }
    }

    String FileName = "forum";

    //创建文件夹包装图片
    private File createImageFile() throws IOException {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + FileName);

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + FileName + "/", System.currentTimeMillis() + ".jpg");
        //保存当前图片路径
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    /**
     * 209.  * 本地相册选择图片  * 210.
     */
    private void chosePic() {
        // 当SDK版本号为2.3以下版本时
//        if (Build.VERSION.SDK_INT < 21) {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        mActivity.startActivityForResult(wrapperIntent, REQ_CHOOSE);
//            FolderListActivity.startFolderListActivity(mActivity, REQ_CHOOSE, null, 1);
//        } else {
//            FolderListActivity.startFolderListActivity(mActivity, REQ_CHOOSE, null, 6);
//        }
    }


    public void mUploadMessage(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            if (mUploadMessageForAndroid5 != null) {
                Uri[] uris = new Uri[1];
                uris[0] = Uri.parse("");
                mUploadMessageForAndroid5.onReceiveValue(uris);
                mUploadMessageForAndroid5 = null;
            } else {
                mUploadMessage.onReceiveValue(Uri.parse(""));
                mUploadMessage = null;
            }
            return;
        }
        if (null == mUploadMessage && null == mUploadMessageForAndroid5) return;
        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
        if (mUploadMessageForAndroid5 != null) {
            onActivityResultAboveL(requestCode, data);
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    //5.0以上版本，由于api不一样，要单独处理
    private void onActivityResultAboveL(int requestCode, Intent data) {

        if (mUploadMessageForAndroid5 == null) {
            return;
        }
        Uri result = null;
        Uri results[] = null;
        if (requestCode == TAKE_PHOTO) {
            File file = new File(mCurrentPhotoPath);
            Uri localUri = Uri.fromFile(file);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            mActivity.sendBroadcast(localIntent);
            result = Uri.fromFile(file);
        } else if (requestCode == REQ_CHOOSE) {
            if (data != null) {
                result = data.getData();
            }
        }
        if (result != null) {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
        }
        mUploadMessageForAndroid5 = null;
        return;
    }

// ============================以下是多选上传的功能==================================================
//    public void mUploadMessage(int requestCode, int resultCode, Intent data) {
//        if(resultCode!=RESULT_OK){
//            if (mUploadMessageForAndroid5 != null) {
//                Uri[] uris = new Uri[1];
//                uris[0] = Uri.parse("");
//                mUploadMessageForAndroid5.onReceiveValue(uris);
//                mUploadMessageForAndroid5 = null;
//            } else {
//                mUploadMessage.onReceiveValue(Uri.parse(""));
//                mUploadMessage = null;
//            }
//            return;
//        }
//        if (null == mUploadMessage && null == mUploadMessageForAndroid5) return;
//        Uri result1 = data == null || resultCode != RESULT_OK ? null : data.getData();
//        if (mUploadMessageForAndroid5 != null) {
//            onActivityResultAboveL(requestCode, data);
//        } else if (mUploadMessage != null) {
//            if (requestCode == TAKE_PHOTO) {
//                mUploadMessage.onReceiveValue(result1);
//            } else if (requestCode == REQ_CHOOSE) {
//                List<ImageFolderBean> list = (List<ImageFolderBean>) data.getSerializableExtra("list");
//                if (list == null) {
//                    return;
//                }
//                for (int i = 0; i < list.size(); i++) {
//                    String[] dataStr = list.get(i).path.split("/");
//                    String fileTruePath = "/sdcard";
//                    for (int j = 4; j < dataStr.length; j++) {
//                        fileTruePath = fileTruePath + "/" + dataStr[j];
//                    }
//                    File file = new File(fileTruePath);
//                    Uri result = Uri.fromFile(file);
//                    mUploadMessage.onReceiveValue(result);
//                }
//            }
//            mUploadMessage = null;
//        }
//    }
//
//    //5.0以上版本，由于api不一样，要单独处理
//    private void onActivityResultAboveL(int requestCode, Intent data) {
//
//        if (mUploadMessageForAndroid5 == null) {
//            return;
//        }
//        Uri result = null;
//        Uri results[] = null;
//        if (requestCode == TAKE_PHOTO) {
//            File file = new File(mCurrentPhotoPath);
//            Uri localUri = Uri.fromFile(file);
//            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
//            mActivity.sendBroadcast(localIntent);
//            result = Uri.fromFile(file);
//            if (result != null) {
//                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
//            }
//        } else if (requestCode == REQ_CHOOSE) {
//            if (data != null) {
//                List<ImageFolderBean> list = (List<ImageFolderBean>) data.getSerializableExtra("list");
//                if (list == null) {
//                    return;
//                }
//                results = new Uri[list.size()];
//                for (int i = 0; i < list.size(); i++) {
//                    String[] dataStr = list.get(i).path.split("/");
//                    String fileTruePath = "/sdcard";
//                    for (int j = 4; j < dataStr.length; j++) {
//                        fileTruePath = fileTruePath + "/" + dataStr[j];
//                    }
//                    File file = new File(fileTruePath);
//                    results[i] = Uri.fromFile(file);
//                }
//                mUploadMessageForAndroid5.onReceiveValue(results);
//            }
//        }
//        mUploadMessageForAndroid5 = null;
//        return;
//    }
}
