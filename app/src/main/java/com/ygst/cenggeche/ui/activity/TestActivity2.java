package com.ygst.cenggeche.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.CheckableButton;

import java.util.ArrayList;
import java.util.List;

public class TestActivity2 extends AppCompatActivity{
    TextView textView;
    EditText editText;

    List<String> listTest = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        for(int i=0 ;i<20;i++){
            listTest.add("SSSSss"+i+i);
        }
        addChildTo2(((FlowLayout) findViewById(R.id.flow_layout)));
    }
    private void addChildTo2(FlowLayout flowLayout) {

        TextView tv = new TextView(this);
            tv.setText("SSSSss");
            tv.setBackgroundResource(R.drawable.button_bg);
            flowLayout.addView(tv);
        TextView tv2 = new TextView(this);
        tv.setText("eeeeeeee");
        tv.setBackgroundResource(R.drawable.button_bg);
        flowLayout.addView(tv2);
        TextView tv3 = new TextView(this);
        tv.setText("dddddddddddddddd");
        tv.setBackgroundResource(R.drawable.button_bg);
        flowLayout.addView(tv3);
        TextView tv4 = new TextView(this);
        tv.setText("dddddddggdd");
        tv.setBackgroundResource(R.drawable.button_bg);
        flowLayout.addView(tv4);
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
