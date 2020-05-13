package com.example.cityservices.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.cityservices.R;
import com.example.cityservices.adapter.StoreAdapter;
import com.example.cityservices.contract.ShowMerchantContract;
import com.example.cityservices.presenter.ShowMerchantPresenter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.java_bean.StoreBean;
import com.scwang.smartrefresh.header.PhoenixHeader;

import java.util.List;

public class ShowMerchantActivity extends AppMvpBaseActivity implements ShowMerchantContract.IView {

    private String mProvince;
    private String mCity;
    private String mDistrict;
    private String mStoreName;

    private RecyclerView mRecyclerView;
    private StoreAdapter mStoreAdapter;//商店适配器
    private ShowMerchantPresenter mPresenter = new ShowMerchantPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initData();
        mPresenter.attachView(this);//绑定view
        mPresenter.initStoreInfo();//初始化一下商店信息
    }

    /**
     * 初始化view
     */
    private void initView() {

        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_title_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多不可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        PhoenixHeader header = new PhoenixHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//recyclerView
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();

        mProvince = intent.getStringExtra("province");
        mCity = intent.getStringExtra("city");
        mDistrict = intent.getStringExtra("district");
        mStoreName = intent.getStringExtra("store_name");
    }


    @Override
    protected String getTitleName() {
        return "商家展示";
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
        return R.layout.city_layout_show_merchant;
    }

    @Override
    protected void onFloatBtClick() {

    }

    /**
     * "province": "山东省",
     * "city": "潍坊市",
     * "district": "诸城市",
     * "store_name": "商店名称"
     * 活动启动
     */
    public static void actionStart(Context context, String province, String city, String district, String store_name) {
        Intent intent = new Intent(context, ShowMerchantActivity.class);
        intent.putExtra("province", province);
        intent.putExtra("city", city);
        intent.putExtra("district", district);
        intent.putExtra("store_name", store_name);
        context.startActivity(intent);
    }


    @Override
    public void initStoreList(List<StoreBean> storeList) {
        if (CollectionsUtil.isEmpty(storeList)) {
            showNoMoreData();//没有更多数据
        } else {
            showNormalView();//展示正常数据
            refreshStoreList(storeList);
        }
    }

    @Override
    public void completeRefreshStoreList(List<StoreBean> storeList) {
        mSmartRefreshLayout.finishRefresh();//结束刷新
        refreshStoreList(storeList);
    }

    @Override
    public void completeLoadMoreStoreList(List<StoreBean> storeList) {
        mSmartRefreshLayout.finishLoadMore();//结束加载更多
        refreshStoreList(storeList);
    }


    @Override
    public String getProvince() {
        return mProvince;
    }

    @Override
    public String getCity() {
        return mCity;
    }

    @Override
    public String getDistrict() {
        return mDistrict;
    }

    @Override
    public String getStoreName() {
        return mStoreName;
    }

    private static final String TAG = "ShowMerchantActivity";

    /**
     * 刷新商店列表
     */
    private void refreshStoreList(List<StoreBean> storeList) {

        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(storeList);
            mRecyclerView.setAdapter(mStoreAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(10)));
            mStoreAdapter.setOnItemClickListener(storeBean -> {
                Log.d(TAG, "refreshStoreList: 点击 ");
                ServiceDetailsActivity.actionStart(ShowMerchantActivity.this, storeBean);//启动一下
            });
        } else {
            mStoreAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
