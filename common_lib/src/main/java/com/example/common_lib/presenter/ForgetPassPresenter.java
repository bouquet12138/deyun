package com.example.common_lib.presenter;

import com.example.baselib.base.MVPBasePresenter;
import com.example.common_lib.contract.ForgetPassContract;

public class ForgetPassPresenter  extends MVPBasePresenter<ForgetPassContract.IView>
        implements ForgetPassContract.IPresenter {

    @Override
    public void modifyPassword(String phoneNum, String new_login_password) {

    }
}
