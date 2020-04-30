package com.example.main_module.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.util.DensityUtil;
import com.example.common_lib.contract.ARouterContract;
import com.example.main_module.R;
import com.example.main_module.activity.AccountManagerActivity;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment implements View.OnClickListener {


    protected View mView;

    private ImageView mHead;
    private TextView mPhoneText;
    private TextView mNameText;
    private ImageView mSexImg;
    private TextView mEditText;

    private TextView mPromoteMoney;//推广积分
    private TextView mGetMoneyText;//提现金额

    private LinearLayout mMyTeam;
    private LinearLayout mCan_get;
    private LinearLayout mWithdraw;
    private LinearLayout mTeamReturns;
    private LinearLayout mTransferRecord;
    private LinearLayout mPromotionRecord;
    private LinearLayout mAccountManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_fragment_person, container, false);
        initView();
        initListener();
        return mView;
    }

    private void initView() {

        QMUILinearLayout cardLayout = mView.findViewById(R.id.cardLayout);
        cardLayout.setRadiusAndShadow(DensityUtil.dipToPx(10), DensityUtil.dipToPx(10), 1f);

        mHead = mView.findViewById(R.id.head);
        mPhoneText = mView.findViewById(R.id.myTelPhone);
        mNameText = mView.findViewById(R.id.nameText);
        mSexImg = mView.findViewById(R.id.sexImg);
        mEditText = mView.findViewById(R.id.editText);
        mPromoteMoney = mView.findViewById(R.id.promoteMoneyText);
        mGetMoneyText = mView.findViewById(R.id.getMoneyText);
        mMyTeam = mView.findViewById(R.id.myTeam);
        mCan_get = mView.findViewById(R.id.can_get);
        mWithdraw = mView.findViewById(R.id.withdraw);
        mTeamReturns = mView.findViewById(R.id.teamReturns);
        mTransferRecord = mView.findViewById(R.id.transferRecord);
        mPromotionRecord = mView.findViewById(R.id.promotionRecord);
        mAccountManager = mView.findViewById(R.id.accountManager);

        setTitle(100000, "推广积分", mPromoteMoney);
        setTitle(121000, "提现金额", mGetMoneyText);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mEditText.setOnClickListener(this);

        mMyTeam.setOnClickListener(this);
        mCan_get.setOnClickListener(this);
        mWithdraw.setOnClickListener(this);
        mTeamReturns.setOnClickListener(this);
        mTransferRecord.setOnClickListener(this);
        mPromotionRecord.setOnClickListener(this);
        mAccountManager.setOnClickListener(this);
    }

    private void setTitle(int _num, String _str, TextView textView) {

        String num;

        if (_num < 1E3) {
            num = _num + "";
        } else if (_num < 1E4) {
            num = (_num / 1E3) + "千+";
        } else if (_num < 1E6)
            num = (_num / 1E4) + "万+";
        else if (_num < 1E7)
            num = (_num / 1E6) + "百万+";
        else if (_num < 1E8)
            num = (_num / 1E7) + "千万+";
        else
            num = (_num / 1E8) + "亿+";


        String str = num + "\n" + _str;

        SpannableString spannableString = new SpannableString(str);

        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtil.dipToPx(15)),
                num.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_text_color)),
                num.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editText:
                ARouter.getInstance().build(ARouterContract.LOGIN_INFO) //跳转到用户信息页面
                        .navigation();
                break;
            case R.id.can_get:
                break;
            case R.id.withdraw:
                break;
            case R.id.teamReturns:
                break;
            case R.id.transferRecord:
                break;
            case R.id.promotionRecord:
                break;
            case R.id.accountManager:
                startActivity(new Intent(getContext(), AccountManagerActivity.class));
                break;

        }
    }
}
