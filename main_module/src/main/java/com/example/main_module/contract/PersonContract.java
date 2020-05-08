package com.example.main_module.contract;

import com.example.baselib.base.IMVPBaseView;

public interface PersonContract {

    interface IView extends IMVPBaseView {

        /**
         * 设置积分
         *
         * @param integral
         */
        void setIntegral(int integral);

        /**
         * 设置提现
         *
         * @param withdraw
         */
        void setWithdraw(int withdraw);
    }

    interface IPresenter {

        /**
         * 得到信息
         */
        void getInfo();

    }


}
