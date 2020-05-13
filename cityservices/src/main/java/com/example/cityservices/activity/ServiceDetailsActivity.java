package com.example.cityservices.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cityservices.R;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.StoreBean;

public class ServiceDetailsActivity extends AppMvpBaseActivity {

    private TextView mStoreName;
    private ImageView mHeadImg;
    private ImageView mLocationImg;
    private TextView mDetailedAddress;
    private ImageView mPhoneImg;
    private TextView mBusinessTime;
    private TextView mStoreDescribe;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//隐藏提交
        intView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        StoreBean bean = (StoreBean) intent.getSerializableExtra("storeBean");
        if (bean == null) {
            finish();
            return;
        }
        mStoreName.setText(bean.getStore_name());//商店名称

        if (bean.getHead_img() == null) {
            mHeadImg.setImageResource(R.drawable.image_loading);
        } else
            Glide.with(this).
                    load(ServerInfo.getImageAddress(bean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(mHeadImg);
        mDetailedAddress.setText(bean.getDetailed_address());//详细地址
        mBusinessTime.setText("营业时间:" + bean.getBusiness_hours());//营业时间
        mStoreDescribe.setText(bean.getStore_describe());//详细

    }

    /**
     * 初始化view
     */
    private void intView() {
        mStoreName = findViewById(R.id.storeName);
        mHeadImg = findViewById(R.id.headImg);
        mLocationImg = findViewById(R.id.locationImg);
        mDetailedAddress = findViewById(R.id.detailedAddress);
        mPhoneImg = findViewById(R.id.phoneImg);
        mBusinessTime = findViewById(R.id.businessTime);
        mStoreDescribe = findViewById(R.id.storeDescribe);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected String getTitleName() {
        return "服务详情";
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
        return R.layout.city_layout_service_details;
    }

    @Override
    protected void onFloatBtClick() {

    }

    public static void actionStart(Context context, StoreBean storeBean) {
        Intent intent = new Intent(context, ServiceDetailsActivity.class);
        intent.putExtra("storeBean", storeBean);
        context.startActivity(intent);
    }

}
