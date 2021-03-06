package com.example.payroll_module.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.payroll_module.R;
import com.example.payroll_module.contract.PayrollTransfersContract;
import com.example.payroll_module.presenter.PayrollTransfersPresenter;

@Route(path = ARouterContract.PAYROLL_TRANSFERS)
public class PayrollTransfersActivity extends AppMvpBaseActivity implements PayrollTransfersContract.IView {

    private PayrollTransfersPresenter mPresenter = new PayrollTransfersPresenter();

    private MyEditText mAmountNum;
    private MyEditText mPassword;
    private ShowPasswordView mPasswordBt;
    private MyEditText mRemark;
    private MyEditText mTargetUserText;
    private TextView mUserNameText;

    private Button mConfirmBt;//确认按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();
        setSubmitEnable(false);//右边的按钮不可用
        initView();
        initListener();
        mPresenter.attachView(this);//绑定一下
    }

    /**
     * 初始化view
     */
    private void initView() {
        mAmountNum = findViewById(R.id.amountNum);
        mPassword = findViewById(R.id.password);
        mPasswordBt = findViewById(R.id.passwordBt);
        mRemark = findViewById(R.id.remark);
        mTargetUserText = findViewById(R.id.targetUserText);
        mUserNameText = findViewById(R.id.userNameText);

        mConfirmBt = findViewById(R.id.confirmBt);//确认按钮
        mPasswordBt.setEditText(mPassword.getEditText());

        mRemark.setText("转账");

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mConfirmBt.setOnClickListener(view -> {
            onFloatBtClick();//提交
        });

        mAmountNum.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mPassword.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mTargetUserText.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
            String targetUser = mTargetUserText.getText();
            if (!TextUtils.isEmpty(targetUser) && targetUser.length() == 11) {
                mPresenter.getUserInfo(targetUser);//获取用户信息
            } else
                mUserNameText.setText("");//清空
        });
    }

    @Override
    protected String getTitleName() {
        return "工资互转";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.upload;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.payroll_layout_transfers;
    }

    @Override
    protected void onFloatBtClick() {
        String targetUser = mTargetUserText.getText();
        if (targetUser.equals(NowUserInfo.getNowUserPhone())) {
            showErrorHint("不能给自己转账");
            return;
        }
        mPresenter.payrollTransfers();//开始转账
    }

    /**
     * 是否合格
     *
     * @return
     */
    private boolean isRight() {

        Integer amount = 0;//互转金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText();//密码

        String targetUser = mTargetUserText.getText();
        if (amount != 0 && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(targetUser) && targetUser.length() == 11)
            return true;
        return false;
    }

    /**
     * 积分转让成功
     */
    @Override
    public void transferSuccess() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 设置用户信息
     *
     * @param userBean
     */
    @Override
    public void setUserInfo(UserBean userBean) {
        if (userBean != null) {
            mUserNameText.setText("目标用户姓名:" + userBean.getName());
        }
    }

    @Override
    public int getIntegralAmount() {
        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }
        return amount;
    }

    @Override
    public String getPayPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public String getRemark() {
        return mRemark.getText();
    }

    @Override
    public String getTargetUserPhone() {
        return mTargetUserText.getText();
    }
}
