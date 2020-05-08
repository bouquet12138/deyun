package com.example.withdraw_module.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alibaba.android.arouter.utils.TextUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.withdraw_module.R;
import com.example.withdraw_module.contract.WithdrawContract;
import com.example.withdraw_module.presenter.WithdrawPresenter;

public class WithdrawActivity extends AppMvpBaseActivity implements WithdrawContract.IView {

    private MyEditText mAmountNum;//提现金额
    private EditText mPassword;
    private MyEditText mRemark;
    private MyEditText mBandCardText;
    private MyEditText mAlipayText;
    private MyEditText mWechatText;

    private ShowPasswordView mPasswordView;//密码view

    private WithdrawPresenter mPresenter = new WithdrawPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();
        setSubmitEnable(false);//右边的按钮不可用
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定view
    }

    /**
     * 初始化view
     */
    private void initView() {
        mAmountNum = findViewById(R.id.amountNum);//提现金额
        mPassword = findViewById(R.id.password);
        mPasswordView = findViewById(R.id.passwordBt);//密码按钮
        mRemark = findViewById(R.id.remark);
        mBandCardText = findViewById(R.id.bandCardText);
        mAlipayText = findViewById(R.id.alipayText);
        mWechatText = findViewById(R.id.wechatText);

        mPasswordView.setEditText(mPassword);//设置edit
    }

    /**
     * 初始化
     */
    private void initData() {
        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户

        if (userBean == null)//为空就返回
            return;

        mRemark.setText("提现");
        mBandCardText.setText(userBean.getBank_card());//银行卡号
        mAlipayText.setText(userBean.getPhone_num());//支付宝号
        mWechatText.setText(userBean.getPhone_num());//微信号

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        MyEditText.OnTextChangedListener onTextChangedListener = () -> {
            setSubmitEnable(isRight());//提交是否可用
        };
        mAmountNum.setOnTextChangedListener(onTextChangedListener);
        mBandCardText.setOnTextChangedListener(onTextChangedListener);
        mAlipayText.setOnTextChangedListener(onTextChangedListener);
        mWechatText.setOnTextChangedListener(onTextChangedListener);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setSubmitEnable(isRight());//提交是否可用
            }
        });
    }

    /**
     * 是否合格
     *
     * @return
     */
    private boolean isRight() {

        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText().toString();//密码

        String bandCard = mBandCardText.getText();
        String alipay = mAlipayText.getText();
        String wechat = mWechatText.getText();
        if (amount != 0 && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(bandCard)
                && !TextUtils.isEmpty(alipay) && !TextUtils.isEmpty(wechat))
            return true;
        return false;
    }

    @Override
    protected String getTitleName() {
        return "提现";
    }

    @Override
    protected String getRightTextName() {
        return "提现";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.upload;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.withdraw_layout_withdraw;
    }

    @Override
    protected void onFloatBtClick() {
        UserBean userBean = NowUserInfo.getNowUserInfo();

        if (userBean == null)
            return;

        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText().toString();//密码
        String remark = mRemark.getText();
        String bandCard = mBandCardText.getText();
        String alipay = mAlipayText.getText();
        String wechat = mWechatText.getText();

        WithdrawBean withdrawBean = new WithdrawBean(userBean.getUser_id(), amount, password, remark,
                bandCard, alipay, wechat);
        mPresenter.withdrawInfo(withdrawBean);//提现
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    @Override
    public void withdrawSuccess() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
