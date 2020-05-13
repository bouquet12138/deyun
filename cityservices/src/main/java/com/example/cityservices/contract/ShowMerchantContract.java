package com.example.cityservices.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.StoreBean;

import java.util.List;

public interface ShowMerchantContract {

    interface IView extends IAppMvpView {

        /**
         * 设置团队信息
         *
         * @param storeList 商店列表
         */
        void initStoreList(List<StoreBean> storeList);

        /**
         * 完成刷新
         *
         * @param storeList
         */
        void completeRefreshStoreList(List<StoreBean> storeList);

        /**
         * 完成加载更多
         *
         * @param storeList
         */
        void completeLoadMoreStoreList(List<StoreBean> storeList);


        String getProvince();

        String getCity();

        String getDistrict();

        String getStoreName();
    }

    interface IPresenter {

        /**
         * 得到我团队的信息
         */
        void initStoreInfo();

        /**
         * 刷新商店信息
         */
        void refreshStoreInfo();

        /**
         * 加载更多商店信息
         */
        void loadMoreStoreInfo();
    }

}
