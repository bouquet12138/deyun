package com.example.main_module.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.main_module.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class CityServicesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackButton;
    private TextView mCityText;
    private QMUIRoundButton mSearchBt;
    private SmartRefreshLayout mSmartRefresh;
    private RecyclerView mRecyclerView;

    //城市选择器
    private CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_city_services);
        initView();
        initListener();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
    }

    private void initView() {
        mBackButton = findViewById(R.id.backButton);
        mCityText = findViewById(R.id.cityText);
        mSearchBt = findViewById(R.id.searchBt);
        mSmartRefresh = findViewById(R.id.smartRefresh);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        mCityText.setOnClickListener(this);//点击监听
        mBackButton.setOnClickListener(this);
        mSearchBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cityText:
                showCity();
                break;
            case R.id.backButton:
                finish();//销毁
                break;
            case R.id.searchBt:
                break;
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
