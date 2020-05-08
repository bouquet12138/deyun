package com.example.withdraw_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.WithdrawBean;

import java.util.List;

public interface WithdrawRecordContract {

    interface IView extends IAppMvpView {

        /**
         * 设置提现记录
         *
         * @param withdrawRecords 提现记录
         */
        void setWithdrawRecord(List<WithdrawBean> withdrawRecords);
    }

    interface IPresenter {

        /**
         * 得到提现的信息
         */
        void getWithdrawInfo(int user_id);

    }

}
