package com.ygst.cenggeche.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sqk.emojirelease.Emoji;
import com.sqk.emojirelease.EmojiUtil;
import com.sqk.emojirelease.FaceFragment;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.FlowLayout;
import com.ygst.cenggeche.ui.widget.CheckableButton;

import java.io.IOException;

public class TestActivity2 extends AppCompatActivity{
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        addChildTo(((FlowLayout) findViewById(R.id.flow_layout)));
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
