package com.example.common_lib.contract;

import com.example.baselib.base.IMVPBaseView;
import com.example.common_lib.base.IAppMvpView;

public interface ForgetPassContract {

    interface IView extends IAppMvpView {

    }

    interface IPresenter {

        /**
         * 修改账号密码
         *
         * @param phoneNum
         * @param new_login_password
         */
        void modifyPassword(String phoneNum, String new_login_password);

    }

}
