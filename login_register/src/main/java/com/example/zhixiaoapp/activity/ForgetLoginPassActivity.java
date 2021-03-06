package com.example.zhixiaoapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.zhixiaoapp.R;
import com.example.zhixiaoapp.contract.ForgetLoginContract;
import com.example.zhixiaoapp.presenter.ForgetLoginPassPresenter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class ForgetLoginPassActivity extends AppMvpBaseActivity implements View.OnClickListener, ForgetLoginContract.IView {


    private MyEditText mMyTelPhone;
    private MyEditText mVerCode;
    private QMUIRoundButton mGetVrCodeBt;
    private MyEditText mLoginPassword;
    private ShowPasswordView mPasswordBt1;
    private MyEditText mConfirmLoginPassword;
    private ShowPasswordView mPasswordBt2;

    private ForgetLoginPassPresenter mPresenter = new ForgetLoginPassPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initListener();
        mPresenter.attachView(this);
    }

    @Override
    protected String getTitleName() {
        return "忘记登陆密码";
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
        return R.layout.login_layout_forget_pass;
    }

    @Override
    protected void onFloatBtClick() {
        mPresenter.submit();//提交一下
    }

    private void initView() {
        mMyTelPhone = findViewById(R.id.myTelPhone);
        mVerCode = findViewById(R.id.verCode);
        mGetVrCodeBt = findViewById(R.id.getVrCodeBt);
        mLoginPassword = findViewById(R.id.loginPassword);
        mPasswordBt1 = findViewById(R.id.passwordBt1);
        mConfirmLoginPassword = findViewById(R.id.confirmLoginPassword);
        mPasswordBt2 = findViewById(R.id.passwordBt2);
        mPasswordBt1.setEditText(mLoginPassword.getEditText());
        mPasswordBt2.setEditText(mConfirmLoginPassword.getEditText());

        mMyTelPhone.setText(NowUserInfo.getNowUserPhone());
        mMyTelPhone.setCursorPosition();//设置光标位置
    }

    private void initListener() {
        mGetVrCodeBt.setOnClickListener(this);
        MyEditText.OnTextChangedListener onTextChange = () -> setSubmitEnable(isRight());
        mMyTelPhone.setOnTextChangedListener(onTextChange);
        mVerCode.setOnTextChangedListener(onTextChange);
        mLoginPassword.setOnTextChangedListener(onTextChange);
        mConfirmLoginPassword.setOnTextChangedListener(onTextChange);
    }

    /**
     * 是否正确
     */
    private boolean isRight() {

        String phoneNum = mMyTelPhone.getText();
        String verCode = mVerCode.getText();//验证码
        String password1 = mLoginPassword.getText();//密码
        String password2 = mConfirmLoginPassword.getText();//密码

        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11
                || TextUtils.isEmpty(verCode) || TextUtils.isEmpty(password1)
                || TextUtils.isEmpty(password2) || password1.length() < 6
                || !password2.equals(password1))
            return false;
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getVrCodeBt) {
            String phoneNum = mMyTelPhone.getText();
            if (phoneNum.length() == 11)
                mPresenter.sendQrCode();//发送验证码
            else
                showErrorHint("请输入正确的手机号");
        }
    }

    @Override
    public String getPhoneNum() {
        return mMyTelPhone.getText();
    }

    @Override
    public String getVerCode() {
        return mVerCode.getText();
    }

    @Override
    public String getLoginPassword() {
        return mLoginPassword.getText();
    }

    @Override
    public void setSendBtEnable(boolean enable) {
        mGetVrCodeBt.setEnabled(enable);
    }

    /**
     * 销毁后
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 设置按钮上的文字
     *
     * @param text
     */
    @Override
    public void setSendBtText(String text) {
        mGetVrCodeBt.setText(text);
    }

    @Override
    public boolean isForgetPay() {
        return false;
    }
}
