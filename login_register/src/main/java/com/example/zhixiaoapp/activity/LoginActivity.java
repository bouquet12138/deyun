package com.example.zhixiaoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.contract.ARouterContract;
import com.example.zhixiaoapp.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

@Route(path = ARouterContract.LOGIN_LOGIN)
public class LoginActivity extends AppCompatActivity {

    private ImageView mHeadImage;
    private EditText mAccountEdit;
    private EditText mPasswordEdit;
    private ImageView mOpenImage;
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
        mConfirmBt.setEnabled(true);//可用
        initListener();
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
    }

    /**
     * 初始化监听
     */
    private void initListener() {
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
            ARouter.getInstance().build(ARouterContract.MAIN_MAIN) //跳转到登陆页面
                    .navigation();
            finish();//销毁自己
        });
    }

}
