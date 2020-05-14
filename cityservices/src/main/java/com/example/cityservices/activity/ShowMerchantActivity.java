package com.example.cityservices.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.MVPBaseActivity;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.cityservices.R;
import com.example.cityservices.adapter.StoreAdapter;
import com.example.cityservices.contract.ShowMerchantContract;
import com.example.cityservices.presenter.ShowMerchantPresenter;
import com.example.common_lib.java_bean.StoreBean;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public class ShowMerchantActivity extends MVPBaseActivity implements ShowMerchantContract.IView {

    private String mProvince;
    private String mCity;
    private String mDistrict;
    private String mStoreName;


    private ImageView mBackButton;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private ViewGroup mNetErrorLayout;
    private Button mRefresh;


    private StoreAdapter mStoreAdapter;//商店适配器
    private ShowMerchantPresenter mPresenter = new ShowMerchantPresenter();
    private boolean mBottomNoMoreData;//没有更多数据了


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_layout_show_merchant);
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定view
        mPresenter.initStoreInfo();//初始化一下商店信息
    }


    /**
     * 初始化view
     */
    private void initView() {

        mBackButton = findViewById(R.id.backButton);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);

        mNetErrorLayout = findViewById(R.id.netErrorLayout);
        mRefresh = findViewById(R.id.refresh);

        mNetErrorLayout.setVisibility(View.GONE);//不可见
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

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackButton.setOnClickListener(view -> finish());
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refreshStoreInfo();//刷新商店信息
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMoreStoreInfo();//加载更多信息
        });
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
    public String getProvince() {
        return TextUtils.isEmpty(mProvince) ? "" : mProvince;
    }

    @Override
    public String getCity() {
        return TextUtils.isEmpty(mCity) ? "" : mCity;
    }

    @Override
    public String getDistrict() {
        return TextUtils.isEmpty(mDistrict) ? "" : mDistrict;
    }

    @Override
    public String getStoreName() {
        return TextUtils.isEmpty(mStoreName) ? "" : mStoreName;
    }

    private static final String TAG = "ShowMerchantActivity";

    /**
     * 刷新商店列表
     */
    @Override
    public void refreshStoreList(List<StoreBean> storeList) {

        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(storeList);
            mRecyclerView.setAdapter(mStoreAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(5)));
            mStoreAdapter.setOnItemClickListener(storeBean -> {
                Log.d(TAG, "refreshStoreList: 点击 ");
                ServiceDetailsActivity.actionStart(ShowMerchantActivity.this, storeBean);//启动一下
            });
        } else {
            mStoreAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    /**
     * 没有更多数据
     */
    @Override
    public void setFootNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        mBottomNoMoreData = true;//底部没有更多数据
    }

    @Override
    public void showNormalView() {
        mRecyclerView.setVisibility(View.VISIBLE);//可见
        if (!mBottomNoMoreData)
            mRefreshLayout.setEnableLoadMore(true);
        mNetErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mRecyclerView.setVisibility(View.GONE);//可见
        if (!mBottomNoMoreData)
            mRefreshLayout.setEnableLoadMore(false);
        mNetErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorHint(String msg) {
        QMUITipDialog errorDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
                .create();
        errorDialog.show();//展示一下
        mBackButton.postDelayed(() -> errorDialog.dismiss(), 500);
    }

    @Override
    public void completeRefresh() {
        if (mBottomNoMoreData)
            setFootNoMoreData();//没有更多数据
        mRefreshLayout.finishRefresh();//结束刷新
    }

    @Override
    public void completeLoadMore() {
        mRefreshLayout.finishLoadMore();//完成加载更多
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
