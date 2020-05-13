package com.example.cityservices.presenter;

import com.example.baselib.base.MVPBasePresenter;
import com.example.baselib.util.NetWorkUtils;
import com.example.cityservices.contract.ShowMerchantContract;
import com.example.common_lib.java_bean.StoreBean;

import java.util.ArrayList;
import java.util.List;

public class ShowMerchantPresenter extends MVPBasePresenter<ShowMerchantContract.IView>
        implements ShowMerchantContract.IPresenter {

    private List<StoreBean> mStoreBeans = new ArrayList<>();//一个集合

    @Override
    public void initStoreInfo() {
        if (!isViewAttached())
            return;
        if (!NetWorkUtils.isNetWorkAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }

        mStoreBeans.clear();//清空一下
        mStoreBeans.add(new StoreBean("花花画铺", "花圃", "山东省", "潍坊市", "诸城市",
                "超然路88号", "早7：00-晚21：00", "一个为大家画画的花圃",
                "15264173066"));
        mStoreBeans.add(new StoreBean("花花画铺2", "花圃", "山东省", "青岛市", "青岛区",
                "超然路11号", "早7：00-晚21：00", "一个为大家画画的花圃",
                "45145614361"));

        getView().initStoreList(mStoreBeans);//初始化一下商店信息

    }

    @Override
    public void refreshStoreInfo() {

    }

    @Override
    public void loadMoreStoreInfo() {

    }
}
