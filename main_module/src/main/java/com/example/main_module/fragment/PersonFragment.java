package com.example.main_module.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.base.BaseFragment;
import com.example.baselib.util.DensityUtil;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.common_lib.java_bean.PayrollBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.util.HeadImageUtil;
import com.example.common_lib.util.NumberFormatUtil;
import com.example.main_module.R;
import com.example.main_module.activity.AccountManagerActivity;
import com.example.main_module.activity.MyTeamActivity;
import com.example.main_module.contract.PersonContract;
import com.example.main_module.presenter.PersonPresenter;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener, PersonContract.IView {

    private ImageView mHead;
    private TextView mPhoneText;
    private TextView mNameText;
    private ImageView mSexImg;
    private TextView mEditText;

    private TextView mTotalIntegral;//推广积分
    private TextView mTotalPayroll;//可提现金额

    private LinearLayout mMyTeam;
    private LinearLayout mCan_get;
    private LinearLayout mWithdraw;
    private LinearLayout mRechargeRecord;

    private LinearLayout mPromoteIncome;
    private LinearLayout mTransfer;
    private LinearLayout mPromotionRecord;
    private LinearLayout mAccountManager;

    private PersonPresenter mPresenter = new PersonPresenter();

    @Override
    protected int getContentViewId() {
        return R.layout.main_fragment_person;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        mPresenter.attachView(this);//绑定一下
        initView();
        initListener();
        initData();
    }

    /**
     * 重新回到栈顶
     */
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mView == null)
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();

        if (userBean == null)
            return;

        if (TextUtils.isEmpty(userBean.getName()))
            mNameText.setText("");
        else
            mNameText.setText(userBean.getName());

        if (TextUtils.isEmpty(userBean.getPhone_num()))
            mPhoneText.setText("");
        else
            mPhoneText.setText(userBean.getPhone_num());

        if ("女".equals(userBean.getSex()))//女
            mSexImg.setImageResource(R.drawable.woman);
        else
            mSexImg.setImageResource(R.drawable.man);
        HeadImageUtil.setUserHead(userBean, getContext(), mHead);//设置头像
        mPresenter.getInfo();//获取积分信息
    }

    /**
     * 初始化view
     */
    private void initView() {

        QMUILinearLayout cardLayout = mView.findViewById(R.id.cardLayout);
        cardLayout.setRadiusAndShadow(DensityUtil.dipToPx(10), DensityUtil.dipToPx(10), 1f);

        mHead = mView.findViewById(R.id.head);
        mPhoneText = mView.findViewById(R.id.phoneText);
        mNameText = mView.findViewById(R.id.nameText);
        mSexImg = mView.findViewById(R.id.sexImg);
        mEditText = mView.findViewById(R.id.editText);
        mTotalIntegral = mView.findViewById(R.id.totalIntegralText);
        mTotalPayroll = mView.findViewById(R.id.totalPayrollText);
        mMyTeam = mView.findViewById(R.id.myTeam);
        mCan_get = mView.findViewById(R.id.can_get);
        mWithdraw = mView.findViewById(R.id.withdraw);
        mRechargeRecord = mView.findViewById(R.id.rechargeRecord);//充值记录

        mPromoteIncome = mView.findViewById(R.id.promoteIncome);
        mTransfer = mView.findViewById(R.id.transfer);
        mPromotionRecord = mView.findViewById(R.id.promotionRecord);
        mAccountManager = mView.findViewById(R.id.accountManager);

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mTotalIntegral.setOnClickListener(this);
        mTotalPayroll.setOnClickListener(this);

        mEditText.setOnClickListener(this);

        mMyTeam.setOnClickListener(this);
        mCan_get.setOnClickListener(this);
        mWithdraw.setOnClickListener(this);

        mRechargeRecord.setOnClickListener(this);
        mPromoteIncome.setOnClickListener(this);


        mTransfer.setOnClickListener(this);
        mPromotionRecord.setOnClickListener(this);
        mAccountManager.setOnClickListener(this);

    }

    private void setTitle(long _num, String _str, TextView textView) {

        String numStr = NumberFormatUtil.formatNum(_num);
        String str = numStr + "\n" + _str;

        SpannableString spannableString = new SpannableString(str);

        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtil.dipToPx(15)),
                numStr.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_text_color)),
                numStr.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.totalIntegralText:
                break;
            case R.id.totalPayrollText:
                ARouter.getInstance().build(ARouterContract.PAYROLL_RECORD) //工资记录
                        .navigation();
                break;
            case R.id.editText:
                ARouter.getInstance().build(ARouterContract.LOGIN_INFO) //跳转到用户信息页面
                        .navigation();
                break;
            case R.id.can_get:
                break;
            case R.id.withdraw:
                ARouter.getInstance().build(ARouterContract.WITHDRAW_WITHDRAW_RECORD) //提现记录
                        .navigation();
                break;
            case R.id.myTeam:
                startActivity(new Intent(getContext(), MyTeamActivity.class));//启动我的团队
                break;

            case R.id.rechargeRecord:
                ARouter.getInstance().build(ARouterContract.INTEGRAL_TRANSFERS_RECORD).withString("integralType", IntegralBean.RECHARGE)
                        .navigation();
                break;
            case R.id.promoteIncome://推广收益
                ARouter.getInstance().build(ARouterContract.PAYROLL_RECORD).withString("type", PayrollBean.PROMOTE_INCOME)//推广收益
                        .navigation();
                break;
            case R.id.transfer:
                ARouter.getInstance().build(ARouterContract.PAYROLL_TRANSFERS).navigation();//工资互转
                break;
            case R.id.promotionRecord:
                ARouter.getInstance().build(ARouterContract.INTEGRAL_TRANSFERS_RECORD).withString("integralType", IntegralBean.BUY_SET_MEAL)
                        .navigation();
                break;
            case R.id.accountManager:
                startActivity(new Intent(getContext(), AccountManagerActivity.class));
                break;

        }
    }

    @Override
    public void setIntegral(int integral) {
        setTitle(integral, "剩余积分", mTotalIntegral);
    }

    @Override
    public void setWithdraw(int withdraw) {
        setTitle(withdraw, "可提现", mTotalPayroll);

    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
