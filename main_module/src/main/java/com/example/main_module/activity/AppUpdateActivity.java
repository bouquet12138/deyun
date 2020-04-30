package com.example.main_module.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.main_module.R;

import static com.example.baselib.util.PackageUtils.getAppName;
import static com.example.baselib.util.PackageUtils.getBitmap;
import static com.example.baselib.util.PackageUtils.getVersionName;

public class AppUpdateActivity extends AppMvpBaseActivity implements View.OnClickListener {

    private ImageView mAppIcon;
    private TextView mAppName;
    private TextView mAppVersion;
    private Button mUpdateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//不展示提交按钮
        initView();
        initData();
        initListener();
    }

    @Override
    protected String getTitleName() {
        return "应用信息";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_app_update;
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {

        mAppIcon = findViewById(R.id.appIcon);
        mAppName = findViewById(R.id.appName);
        mAppVersion = findViewById(R.id.appVersion);
        mUpdateBt = findViewById(R.id.updateBt);
    }

    private void initData() {
        mAppName.setText(getAppName(this));
        mAppVersion.setText("版本号：" + getVersionName(this));
     /*   Glide.with(this).load(getBitmap(this)).error(R.drawable.icon_circle)
                .into(mAppIcon);*/
        mAppIcon.setImageBitmap(getBitmap(this));
    }


    private void initListener() {
        mUpdateBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateBt:
                break;
        }
    }
}

