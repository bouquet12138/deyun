package com.example.cityservices.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cityservices.R;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.editText.MyEditText;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.scwang.smartrefresh.header.PhoenixHeader;

@Route(path = ARouterContract.CITY_SERVICES)
public class CityServicesActivity extends AppMvpBaseActivity implements View.OnClickListener {

    private String mProvince;
    private String mCity;
    private String mDistrict;

    private TextView mCityText;
    private MyEditText mStoreName;

    private QMUIRoundButton mSearchBt;
    private RecyclerView mRecyclerView;


    //城市选择器
    private CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交不可用
        initView();
        initListener();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
    }

    @Override
    protected String getTitleName() {
        return "城市服务";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.city_layout_services;
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_title_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多不可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        PhoenixHeader header = new PhoenixHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        mCityText = findViewById(R.id.cityText);
        mStoreName = findViewById(R.id.storeName);//商店名字

        mSearchBt = findViewById(R.id.searchBt);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        mCityText.setOnClickListener(this);//点击监听
        mSearchBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cityText) {//展示城市
            showCity();
        } else if (id == R.id.searchBt) {
            ShowMerchantActivity.actionStart(this, mProvince, mCity, mDistrict, mStoreName.getText());
        }
    }


    private static final String TAG = "CityServicesActivity";

    /**
     * 展示城市
     */
    private void showCity() {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo

        Log.d(TAG, "showCity: " + R.color.app_title_color);

        CityConfig cityConfig = new CityConfig.Builder()
                .setShowGAT(true)//显示港澳台数据
                .confirTextColor("#4286E7")
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .districtCyclic(false)//区县滚轮是否循环滚动
                .setLineColor("#fafafa")//中间横线的颜色
                .setLineHeigh(1)//中间横线的高度
                .setCustomItemLayout(R.layout.common_item_city)
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                .build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                mProvince = province.getName();
                mCity = city.getName();
                mDistrict = district.getName();
                mCityText.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
            }

            @Override
            public void onCancel() {
            }
        });

        //显示
        mPicker.showCityPicker();

    }

}
