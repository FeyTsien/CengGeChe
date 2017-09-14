package com.ygst.cenggeche.ui.activity.setnotename;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.mvp.MVPBaseActivity;
import com.ygst.cenggeche.utils.JMessageUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetNoteNameActivity extends MVPBaseActivity<SetNoteNameContract.View, SetNoteNamePresenter> implements SetNoteNameContract.View {

    private String targetUsername;
    private String noteName;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_note_name)
    EditText mEtNoteName;

    /**
     * 返回
     */
    @OnClick(R.id.iv_back)
    public void goBack() {
        finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_notename;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mTvTitle.setText("修改备注");
        targetUsername = getIntent().getStringExtra(JMessageUtils.TARGET_USERNAME);
        noteName = getIntent().getStringExtra("NoteName");
        mEtNoteName.setText(noteName);
    }
    @OnClick(R.id.bt_submit)
    public void setNoteName(){
        noteName = mEtNoteName.getText().toString();
        Map<String,String> map = new HashMap();
        map.put("myusername", AppData.getUserName());
        map.put("targetUsername",targetUsername);
        map.put("note",noteName);
        mPresenter.updateNoteName(map);
    }

    @Override
    public void updateNoteNameSuccess() {
        Intent intent = new Intent();
        intent.putExtra("NoteName", noteName);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void updateNoteNameError() {

    }
}
