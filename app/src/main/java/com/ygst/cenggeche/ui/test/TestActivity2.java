package com.ygst.cenggeche.ui.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.view.explosion.ExplosionField;
import com.ygst.cenggeche.ui.widget.CheckableButton;
import com.ygst.cenggeche.ui.widget.CircleImageView;
import com.ygst.cenggeche.ui.widget.shimmer.Shimmer;
import com.ygst.cenggeche.ui.widget.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity2 extends AppCompatActivity {
    ShimmerTextView tv;
    Shimmer shimmer;
    CircleImageView cv1;
    FlowLayout flowLayout;
    String[] s = {"sssss", "sdsdsds", "ssdsdss", "fcvxcd", "dfdffffffffdgfgd", "dfgfgdfgdf", "gdfgdfgfg", "hjjks", "skjjjjjjjggggggggg", "ss", "sss", "sssss", "sssss"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        cv1 = (CircleImageView) findViewById(R.id.civ_1);
        cv1.setBorderColor(getResources().getColor(R.color.colorTheme));
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
                if (shimmer != null && shimmer.isAnimating()) {
                    shimmer.cancel();
                } else {
                    shimmer = new Shimmer();
                    shimmer.start(tv);
                }
            }
        });
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
