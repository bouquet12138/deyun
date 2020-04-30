package com.example.zhixiaoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.zhixiaoapp.R;

@Route(path = ARouterContract.LOGIN_REGISTER)
public class RegisterActivity extends AppMvpBaseActivity implements View.OnClickListener {


    private MyEditText mMyTelPhone;
    private MyEditText mPlace_user;
    private MyEditText mVerCode;
    private Button mGetVrCodeBt;
    private MyEditText mLoginPassword;

    private ShowPasswordView mPasswordBt1;
    private MyEditText mConfirmLoginPassword;
    private ShowPasswordView mPasswordBt2;
    private MyEditText mPayPassword;

    private ShowPasswordView mPasswordBt3;
    private MyEditText mConfirmPayPassword;
    private ShowPasswordView mPasswordBt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(true);//可用
        initView();
        initListener();
    }



    private void initView() {
        mMyTelPhone = findViewById(R.id.myTelPhone);
        mPlace_user = findViewById(R.id.place_user);
        mVerCode = findViewById(R.id.verCode);
        mGetVrCodeBt = findViewById(R.id.getVrCodeBt);
        mLoginPassword = findViewById(R.id.loginPassword);
        mPasswordBt1 = findViewById(R.id.passwordBt1);
        mConfirmLoginPassword = findViewById(R.id.confirmLoginPassword);
        mPasswordBt2 = findViewById(R.id.passwordBt2);
        mPayPassword = findViewById(R.id.payPassword);
        mPasswordBt3 = findViewById(R.id.passwordBt3);
        mConfirmPayPassword = findViewById(R.id.confirmPayPassword);
        mPasswordBt4 = findViewById(R.id.passwordBt4);
    }

    private void initListener() {
        mGetVrCodeBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getVrCodeBt) {
        }
    }


    @Override
    protected String getTitleName() {
        return "注册";
    }

    @Override
    protected String getRightTextName() {
        return "下一步";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.next;
    }

    /**
     * 正常view
     *
     * @return
     */
    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_register;
    }

    @Override
    protected void onFloatBtClick() {
        Intent intent = new Intent(RegisterActivity.this, InformationActivity.class);
        startActivity(intent);
    }
}
