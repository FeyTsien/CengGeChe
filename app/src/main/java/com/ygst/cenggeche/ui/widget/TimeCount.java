package com.ygst.cenggeche.ui.widget;

import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {
    private Button mButton;

    public void setButton(Button button) {
        this.mButton = button;
    }

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // btnGetcode.setBackgroundColor(Color.parseColor("#B6B6D8"));
        mButton.setClickable(false);
        mButton.setText(millisUntilFinished / 1000 + " s");
    }

    @Override
    public void onFinish() {
        //设置按钮可点击
        mButton.setClickable(true);
        //设置按钮为正常状态
        mButton.setPressed(true);
        mButton.setText("重新获取");
        // btnGetcode.setBackgroundColor(Color.parseColor("#4EB84A"));

    }
}