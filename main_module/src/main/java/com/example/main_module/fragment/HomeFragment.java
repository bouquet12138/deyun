package com.example.main_module.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.util.CornerUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.common_lib.contract.ARouterContract;
import com.example.flash_module.adapter.FlashAdapter;
import com.example.flash_module.info.FlashBeanList;
import com.example.main_module.R;
import com.example.main_module.activity.AboutUsActivity;
import com.example.main_module.activity.IntegralShopActivity;
import com.example.main_module.activity.PromotionActivity;
import com.example.main_module.activity.ServiceCenterActivity;
import com.example.main_module.activity.ShoppingActivity;
import com.example.main_module.adapter.BusinessAdapter;
import com.example.main_module.adapter.BusinessBean;
import com.example.main_module.adapter.ImageAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    protected View mView;

    private TextView mTitle;
    private Banner mBanner;

    private ViewGroup mMyShop;
    private ViewGroup mIntegralShop;
    private ViewGroup mCity;
    private ViewGroup mPopularize_app;

    private ViewGroup mCompanyBusiness;//公司业务
    private ViewGroup mMember_register;
    private ViewGroup mUser_center;
    private ViewGroup mAbout_us;

    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;

    private FlashAdapter mFlashAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_fragment_home, container, false);
        initView();
        initListener();
        initBanner();
        initData();
        return mView;
    }

    private void initBanner() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.main_shop_a);
        imageList.add(R.drawable.main_shop_b);
        //    imageList.add(R.drawable.main_gongsi_c);
        mBanner.setAdapter(new ImageAdapter(imageList, getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //加个圆角
            CornerUtil.clipViewCornerByDp(mBanner, DensityUtil.dipToPx(8));

        mBanner.start();//启动banner
    }

    private void initData() {
        List<BusinessBean> businessBeans = new ArrayList<>();
        businessBeans.add(new BusinessBean(R.drawable.main_clip, "视频剪辑，商业广告"));
        businessBeans.add(new BusinessBean(R.drawable.main_ad, ""));
        businessBeans.add(new BusinessBean(R.drawable.main_ad, "企业宣传片"));
        businessBeans.add(new BusinessBean(R.drawable.main_ad, "人物VCR"));
        businessBeans.add(new BusinessBean(R.drawable.main_ad, "活动花絮等拍摄与制作"));

        businessBeans.add(new BusinessBean(R.drawable.main_network, "网站建设"));
        businessBeans.add(new BusinessBean(R.drawable.main_applet, "小程序开发"));
        businessBeans.add(new BusinessBean(R.drawable.main_applet, "App开发"));
        BusinessAdapter businessAdapter = new BusinessAdapter(businessBeans);
        mRecyclerView2.setAdapter(businessAdapter);
        mRecyclerView2.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(5)));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));//布局管理器

        initFlashAdapter();//初始化快讯adapter

    }

    private void initView() {
        mTitle = mView.findViewById(R.id.title);
        mBanner = mView.findViewById(R.id.banner);
        mMyShop = mView.findViewById(R.id.myShop);
        mIntegralShop = mView.findViewById(R.id.integralShop);//积分商城

        mCompanyBusiness = mView.findViewById(R.id.companyBusiness);//公司业务
        mCity = mView.findViewById(R.id.city);
        mPopularize_app = mView.findViewById(R.id.popularize_app);
        mMember_register = mView.findViewById(R.id.member_register);
        mUser_center = mView.findViewById(R.id.user_center);
        mAbout_us = mView.findViewById(R.id.about_us);
        mRecyclerView1 = mView.findViewById(R.id.recyclerView1);
        mRecyclerView2 = mView.findViewById(R.id.recyclerView2);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mMyShop.setOnClickListener(this);
        mIntegralShop.setOnClickListener(this);//点击监听
        mCity.setOnClickListener(this);

        mCompanyBusiness.setOnClickListener(this);//公司业务
        mPopularize_app.setOnClickListener(this);
        mMember_register.setOnClickListener(this);
        mUser_center.setOnClickListener(this);
        mAbout_us.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBanner.stop();//停止
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myShop:
                startActivity(new Intent(getContext(), ShoppingActivity.class));//启动积分商城activity
                break;
            case R.id.integralShop://积分商城
                startActivity(new Intent(getContext(), IntegralShopActivity.class));//启动积分商城activity
                break;
            case R.id.city:
                ARouter.getInstance().build(ARouterContract.CITY_SERVICES) //城市服务
                        .navigation();
                break;
            case R.id.companyBusiness://公司业务
                break;
            case R.id.popularize_app:
                startActivity(new Intent(getContext(), PromotionActivity.class));//启动推广activity
                break;
            case R.id.member_register:
                ARouter.getInstance().build(ARouterContract.LOGIN_REGISTER) //跳转到注册页面
                        .navigation();
                break;
            case R.id.user_center:
                startActivity(new Intent(getContext(), ServiceCenterActivity.class));//启动activity
                break;
            case R.id.about_us:
                startActivity(new Intent(getContext(), AboutUsActivity.class));//启动activity
                break;

        }
    }

    /**
     * 初始化适配器
     */
    private void initFlashAdapter() {
        mFlashAdapter = new FlashAdapter(FlashBeanList.getInstance().getFlashBeans());
        mRecyclerView1.setAdapter(mFlashAdapter);//设置适配器
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView1.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(5)));
        mFlashAdapter.setOnItemClickListener(flashBean -> {
            ARouter.getInstance().build(ARouterContract.FLASH_DETAIL).withSerializable("flashBean", flashBean) //城市服务
                    .navigation();
        });
    }

}
