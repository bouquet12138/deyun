package com.example.payroll_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.common_lib.java_bean.PayrollBean;

import java.util.List;

public interface PayrollRecordContract {

    interface IView extends IAppMvpView {

        /**
         * 设置推广记录
         *
         * @param payrollBeans 工资记录
         */
        void setPromoteIncomeRecord(List<PayrollBean> payrollBeans);

        /**
         * 设置互转记录
         *
         * @param payrollBeans 工资记录
         */
        void setTransferBetweenRecord(List<PayrollBean> payrollBeans);

        String getPayrollType();//得到工资类型
    }

    interface IPresenter {

        /**
         * 得到工资的信息
         */
        void getPayrollRecord(int user_id);

    }

}
