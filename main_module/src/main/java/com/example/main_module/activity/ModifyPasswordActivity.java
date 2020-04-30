package com.example.main_module.activity;


import android.os.Bundle;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.main_module.R;

public class ModifyPasswordActivity extends AppMvpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(true);
    }

    @Override
    protected String getTitleName() {
        return "修改登陆密码";
    }

    @Override
    protected String getRightTextName() {
        return "修改";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.upload;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_modify_password;
    }

    @Override
    protected void onFloatBtClick() {

    }
}