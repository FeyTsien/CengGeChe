package com.ygst.cenggeche.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jarek.imageselect.core.SDCardStoragePath;
import com.jarek.imageselect.utils.SDCardUtils;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;


/**
 * @author :   FeyTsien
 * @date :   2017/8/15
 */

public class MyApplication extends Application {

    private static Context mContext = null;

    private static Handler handler;
    private static int mainThreadId;

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        System.out.println("获取context：etApplicationContext()");
        mContext = getApplicationContext();
        handler = new Handler();//创建Handle
        mainThreadId = Process.myTid();//得到主线程id
        AppData.saveAndroidId();
        // autolayout 的适配初始化 包括状态栏和底部操作栏
//        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        //极光配置
        //您可以在开发状态中启用调试模式。当发布时，您应该关闭调试模式.
        JMessageClient.setDebugMode(true);
        //init 初始化SDK
        JMessageClient.init(mContext);
        initImageLoader(this);
        //表情包开发
        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            }
        });
    }

    //清除登陆
    public static void clearLogin() {
        LogUtils.i("clearLogin", "isTokenExpired: +++++++++++++++++++++++++++++++0005");
        AppData.saveUid("");
        AppData.saveUserName("");
        AppData.saveNickname("");
        AppData.savaUserStatus(0);
        AppData.saveGenders("");
        AppData.setIsLoginEd(false);
        AppData.savaUnReadApplyCount(0);
    }


    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度

    /**
     * 屏幕适配的处理（暂时没有使用，以后有时间研究）
     */
    public void resetDensity() {
        Point size = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }

    /**
     * <br>
     * This configuration tuning is custom. You can tune every option, you may
     * tune some of them, or you can create default configuration by
     * ImageLoaderConfiguration.createDefault(this); method. </br>
     * 开源项目Android-Universal-Image-Loader的初始化
     *
     * @param context Context
     */
    private void initImageLoader(Context context) {
        try {
            SDCardUtils.createFolder(SDCardStoragePath.DEFAULT_IMAGE_CACHE_PATH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(new File(SDCardStoragePath.DEFAULT_IMAGE_CACHE_PATH)))
//				.discCache(DiscCacheFactory.create(context, SDCardStoragePath.DEFAULT_IMAGE_CACHE_PATH))
                .memoryCacheSizePercentage(8)
                .memoryCacheExtraOptions(480, 800)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new BaseImageDownloader(context))
                .writeDebugLogs()
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 解决java.lang.NoClassDefFoundError错误，方法数超过65536了
     * 5.0以下系统会出次问题
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
