package com.example.integral_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.IntegralBean;

import java.util.List;

public interface IntegralRecordContract {

    interface IView extends IAppMvpView {

        /**
         * 设置积分记录
         *
         * @param integralBeans 积分记录
         */
        void setIntegralTransfersRecord(List<IntegralBean> integralBeans);

        String getIntegralType();//得到积分类型
    }

    interface IPresenter {

        /**
         * 得到提现的信息
         */
        void getIntegralTransfersRecord(int user_id);

    }

}
