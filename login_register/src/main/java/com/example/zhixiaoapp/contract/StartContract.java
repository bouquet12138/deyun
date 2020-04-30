package com.example.zhixiaoapp.contract;


import com.example.baselib.base.IMVPBaseView;

public interface StartContract {

    interface IView extends IMVPBaseView {
        void startMainActivity();

        void startLoginActivity();
    }

    interface IPresenter {
        void initInfo();

        int getOccu();//得到身份
    }

}
