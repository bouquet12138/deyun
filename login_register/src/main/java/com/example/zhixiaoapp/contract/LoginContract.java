package com.example.zhixiaoapp.contract;


import com.example.baselib.base.IMVPBaseView;

public interface LoginContract {

    interface IView extends IMVPBaseView {

        void setAccount(String account);

        void setPassword(String password);

        void setRememberChecked();

        void setAutoLandChecked();


        boolean isRememberPassword();

        boolean isAutoLogin();

        String getAccount();//得到账号

        String getPassword();//得到密码

        void startMainActivity();//跳转到主界面

        int getOccu();

    }


    interface IPresenter {

        void login();//登陆

        void initInfo();//初始化信息

    }


}
