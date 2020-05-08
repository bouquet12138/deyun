package com.example.main_module.contract;

import com.example.common_lib.base.IAppMvpView;


public interface OptionContract {

    interface IView extends IAppMvpView {

        String getTitleStr();

        String getContentStr();

    }

    interface IPresenter {

        /**
         * 提交
         */
        void submit();

    }

}
