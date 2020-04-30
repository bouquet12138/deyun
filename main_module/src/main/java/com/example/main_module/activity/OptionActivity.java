package com.example.main_module.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baselib.util.NetWorkUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.main_module.R;

public class OptionActivity extends AppMvpBaseActivity {

    private EditText mTitle;
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initListener();
    }

    private void initView() {
        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_line_color));
    }

    @Override
    protected void onBackBtPressed() {
        if (!TextUtils.isEmpty(mContent.getText().toString()))
            mHintDialog.show();
        else
            finish();
    }

    private void initListener() {
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setSubmitEnable(!TextUtils.isEmpty(s.toString()));
            }
        });
    }

    @Override
    protected String getTitleName() {
        return "意见建议";
    }

    @Override
    protected String getRightTextName() {
        return "提交";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.upload;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_option;
    }

    @Override
    protected void onFloatBtClick() {
        if (!NetWorkUtils.isNetWorkAvailable())
            showErrorHint("网络错误");
        else {
            Toast.makeText(this, "感谢您的反馈谢谢", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

