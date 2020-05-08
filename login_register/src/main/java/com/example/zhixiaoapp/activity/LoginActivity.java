package com.example.zhixiaoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.base.MVPBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.zhixiaoapp.R;
import com.example.zhixiaoapp.contract.LoginContract;
import com.example.zhixiaoapp.presenter.LoginPresenter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

@Route(path = ARouterContract.LOGIN_LOGIN)
public class LoginActivity extends MVPBaseActivity implements LoginContract.IView {

    private LoginPresenter mLoginPresenter = new LoginPresenter();

    private ImageView mHeadImage;
    private MyEditText mAccountEdit;
    private MyEditText mPasswordEdit;
    private ShowPasswordView mOpenImage;
    private CheckBox mRememberPW;
    private CheckBox mAutoLand;
    private TextView mForgetPassword;
    private QMUIRoundButton mConfirmBt;

    private TextView mUserText;
    private TextView mPrivacyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        initView();
        initListener();

        mLoginPresenter.attachView(this);//绑定一下
        mLoginPresenter.initInfo();//初始化一下信息

    }

    private void initView() {
        mHeadImage = findViewById(R.id.headImage);
        mAccountEdit = findViewById(R.id.accountEdit);
        mPasswordEdit = findViewById(R.id.passwordEdit);
        mOpenImage = findViewById(R.id.openImage);
        mRememberPW = findViewById(R.id.rememberPW);
        mAutoLand = findViewById(R.id.autoLand);
        mForgetPassword = findViewById(R.id.forgetPassword);
        mConfirmBt = findViewById(R.id.confirmBt);
        mUserText = findViewById(R.id.userText);
        mPrivacyText = findViewById(R.id.privacyText);

        mOpenImage.setEditText(mPasswordEdit.getEditText());
    }

    /**
     * 初始化监听
     */
    private void initListener() {


        mForgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SelectPassActivity.class));//启动选择密码界面
        });

        mUserText.setOnClickListener(v -> new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("用户协议")
                .setMessage(Html.fromHtml(getResources().getString(R.string.user_agreement)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show());
        mPrivacyText.setOnClickListener(v -> new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("隐私条例")
                .setMessage(Html.fromHtml(getResources().getString(R.string.user_agreement)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show());
        mConfirmBt.setOnClickListener(view -> {
            mLoginPresenter.login();//登陆一下
        });
        mAutoLand.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                mRememberPW.setChecked(true);//记住密码也选中
        });

        MyEditText.OnTextChangedListener onTextChangedListener = () -> {
            String account = mAccountEdit.getText();//账号
            String password = mPasswordEdit.getText();//密码

            if (TextUtils.isEmpty(account) || account.length() != 11 || TextUtils.isEmpty(password))
                mConfirmBt.setEnabled(false);//不可用
            else
                mConfirmBt.setEnabled(true);//可用
        };

        mAccountEdit.setOnTextChangedListener(onTextChangedListener);
        mPasswordEdit.setOnTextChangedListener(onTextChangedListener);

    }

    @Override
    public void setAccount(String account) {
        mAccountEdit.setText(account);
        if (!TextUtils.isEmpty(account))
            mAccountEdit.setCursorPosition(account.length());
    }

    @Override
    public void setPassword(String password) {
        mPasswordEdit.setText(password);
    }

    @Override
    public void setRememberChecked() {
        mRememberPW.setChecked(true);
    }

    @Override
    public void setAutoLandChecked() {
        mAutoLand.setChecked(true);
    }

    @Override
    public boolean isRememberPassword() {
        return mRememberPW.isChecked();
    }

    @Override
    public boolean isAutoLogin() {
        return mAutoLand.isChecked();
    }

    @Override
    public String getAccount() {
        return mAccountEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEdit.getText().toString();
    }

    @Override
    public void startMainActivity() {
        ARouter.getInstance().build(ARouterContract.MAIN_MAIN) // 目标页面
                .navigation();
        finish();//销毁自己
    }

    @Override
    public void showPrivacy() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("隐私条例")
                .setMessage(Html.fromHtml(getResources().getString(R.string.user_agreement)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show();
    }

    /**
     * 销毁后
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();//解除绑定
    }

}
