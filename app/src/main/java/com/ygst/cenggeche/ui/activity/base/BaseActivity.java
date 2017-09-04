package com.ygst.cenggeche.ui.activity.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;

import com.tsien.myapplication.asimplecachedemo.ACache;
import com.umeng.analytics.MobclickAgent;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.http.LifeSubscription;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fey Tesin on 2017/07/05.
 */

public abstract class BaseActivity extends AppCompatActivity implements LifeSubscription {

    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();

    //轻量级的数据缓存工具类
    public ACache mCache;
    public static BaseActivity activity;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mAvatarSize;
    protected int mWidth;
    protected int mHeight;

    //一下变量用于从左边滑动到右边关闭的变量   类似ios自带的关闭效果
    private int endX;
    private int startX;
    private int deltaX;
    private int endY;
    private int startY;
    private int deltaY;

    private View decorView;
    private VelocityTracker mVelocityTracker;
    private boolean isClose = true;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        activity = this;
        //当应用在后台运行超过30秒（默认）再回到前端，将被认为是两个独立的session(启动)，
        // 例如用户回到home，或进入其他程序，经过一段时间后再返回之前的应用。可通过接口：
        //MobclickAgent.setSessionContinueMillis( 60000); //来自定义这个间隔（参数单位为毫秒）。
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        activity = null;
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有系统自带标题头
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置为竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mCache = ACache.get(this);
        mVelocityTracker = mVelocityTracker.obtain();
        decorView = getWindow().getDecorView();
        synchronized (mActivities) {
            mActivities.add(this);
        }
        getDM();
    }

    protected void getDM(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mAvatarSize = (int) (50 * mDensity);
    }

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        setToolBar(toolbar, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, boolean enable) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private CompositeSubscription mCompositeSubscription;

    //用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
    @Override
    public void bindSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
            overridePendingTransition(R.anim.screen_zoom_out, R.anim.screen_zoom_in);
        }
//    （友盟）如果开发者调用Process.kill或者System.exit之类的方法杀死进程，请务必在此之前调用下面方法，用来保存统计数据。
        MobclickAgent.onKillProcess(this);
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //下面的用于侧滑关闭Activity
    public void touchFinish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
    }

    /**
     * 关闭activity时执行这个动画
     *
     * @param deltaX
     */
    public void closeAnimator(int deltaX) {
        if (isClose) {
            ValueAnimator animator = ValueAnimator.ofInt(deltaX, decorView.getWidth());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
//                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
                    decorView.scrollTo(-value, 0);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator arg0) {

                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    if (isClose) {
                        touchFinish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator arg0) {

                }
            });
            animator.setDuration(300);
            animator.start();
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(deltaX, 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (Integer) animation.getAnimatedValue();
//                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
                    decorView.scrollTo(-value, 0);
                }
            });
            animator.setDuration(300);
            animator.start();
        }
    }

    //    需要测滑关闭时在打开这个注释
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                if (startX < getWindow().getDecorView().getWidth() / 32) {
                    return true;
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getRawX();
                endY = (int) ev.getRawY();
                deltaX = endX - startX;
                deltaY = endY - startY;
                if (deltaX > deltaY && startX < getWindow().getDecorView().getWidth() / 32) {
                    decorView.scrollTo(-deltaX, 0);
                    decorView.getBackground().setColorFilter((Integer) evaluateColor((float) deltaX / (float) decorView.getWidth(), Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
                    return true;
                } else {
                    return super.dispatchTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (-25 < xVelocity && xVelocity <= 50 && deltaX > decorView.getWidth() / 3 && startX < getWindow().getDecorView().getWidth() / 32
                        || xVelocity > 50 && startX < getWindow().getDecorView().getWidth() / 32) {
                    isClose = true;
                    closeAnimator(deltaX);
                    return true;
                } else {
                    if (deltaX > 0 && startX < getWindow().getDecorView().getWidth() / 32) {
                        isClose = false;
                        closeAnimator(deltaX);
                        return true;
                    } else {
                        if (startX < getWindow().getDecorView().getWidth() / 32) {
                            decorView.scrollTo(0, 0);
                        }
                        return super.dispatchTouchEvent(ev);
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 颜色变化过度
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }

//    //退出时的动画
//    public void myFinish(){
//        this.finish();
//        overridePendingTransition(R.anim.screen_zoom_out, R.anim.screen_zoom_in);
//    }
}
