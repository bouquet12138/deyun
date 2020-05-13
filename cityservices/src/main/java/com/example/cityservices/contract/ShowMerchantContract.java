package com.example.cityservices.contract;

import com.example.baselib.base.IMVPBaseView;
import com.example.common_lib.java_bean.StoreBean;

import java.util.List;

public interface ShowMerchantContract {

    interface IView extends IMVPBaseView {

        void showNormalView();

        void showNetError();

        void showErrorHint(String msg);//展示错误提示

        void completeRefresh();

        /**
         * 设置团队信息
         *
         * @param storeList 商店列表
         */
        void refreshStoreList(List<StoreBean> storeList);

        /**
         * 完成加载更多
         */
        void completeLoadMore();

        /**
         * 底部没有更多数据
         */
        void setFootNoMoreData();

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
