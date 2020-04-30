package com.example.zhixiaoapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_lib.activity.SelectImagePermissionActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.editText.MyEditText;
import com.example.zhixiaoapp.R;

import java.util.List;

@Route(path = ARouterContract.LOGIN_INFO)
public class InformationActivity extends SelectImagePermissionActivity implements View.OnClickListener {

    private TextView mChangeHeadTextView;
    private ImageView mStudentHeadView;
    private MyEditText mStudentName;
    private RadioGroup mRadioGroup;
    private RadioButton mMen;
    private RadioButton mWomen;
    private LinearLayout mBirthdayLayout;
    private TextView mBirthdayText;
    private LinearLayout mCityLayout;
    private TextView mCityText;
    private MyEditText mIdCard;
    private MyEditText mBandCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initListener();
    }

    @Override
    protected String getTitleName() {
        return "完善个人信息";
    }

    @Override
    protected String getRightTextName() {
        return "提交";
    }

    @Override
    protected int getFloatBtImgRes() {
        return R.drawable.upload;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.login_activity_information;
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mChangeHeadTextView = findViewById(R.id.changeHeadTextView);
        mStudentHeadView = findViewById(R.id.studentHeadView);
        mStudentName = findViewById(R.id.studentName);
        mRadioGroup = findViewById(R.id.radioGroup);
        mMen = findViewById(R.id.men);
        mWomen = findViewById(R.id.women);
        mBirthdayLayout = findViewById(R.id.birthdayLayout);
        mBirthdayText = findViewById(R.id.birthdayText);
        mCityLayout = findViewById(R.id.cityLayout);
        mCityText = findViewById(R.id.cityText);
        mIdCard = findViewById(R.id.idCard);
        mBandCard = findViewById(R.id.bandCard);
    }

    private void initListener() {
        mMen.setOnClickListener(this);
        mWomen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.men) {
        } else if (id == R.id.women) {
        }
    }

    @Override
    protected void addImage(List<Uri> uriList, List<String> stringList) {

    }
}
