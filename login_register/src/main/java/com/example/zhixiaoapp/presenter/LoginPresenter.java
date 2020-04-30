package com.example.zhixiaoapp.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.baselib.base.MVPBasePresenter;
import com.example.zhixiaoapp.contract.LoginContract;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter extends MVPBasePresenter<LoginContract.IView>
        implements LoginContract.IPresenter {

    private static final String TAG = "LoginPresenter";

//    private LoginModel mModel = new LoginModel();

    private final int ON_RESULT = 0;
    private final int NET_ERROR = 1;//网络错误
    private final int ON_COMPLETE = 2;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;

        }
    };

    @Override
    public void login() {
        if (!isViewAttached())
            return;
        getView().showLoading("登陆中...");

    }

    /**
     * 跳转到主activity
     */
    private void startMain() {
        getView().startMainActivity(); //启动主Activity
    }

    /**
     * 存储数据
     */
    private void saveData() {
        SharedPreferences preferences = getShare(getView().getOccu());
        SharedPreferences.Editor editor = preferences.edit();

        boolean isAutoLand = getView().isAutoLogin();//是否自动登陆
        boolean rememberPassword = getView().isRememberPassword();//是否记住密码
        String account = getView().getAccount();//账号
        String password = getView().getPassword();//密码

        editor.putBoolean("isAutoLogin", isAutoLand);
        editor.putBoolean("isRememberPassWord", rememberPassword);
        //如果记住密码
        if (rememberPassword) {
            editor.putString("account", account);
            editor.putString("password", password);
        } else {
            editor.putString("account", "");
            editor.putString("password", "");
        }
        editor.apply();
    }

    @Override
    public void initInfo() {
        if (!isViewAttached())
            return;

        SharedPreferences preferences = getShare(getView().getOccu());

        boolean isAutoLogin = preferences.getBoolean("isAutoLogin", false);
        boolean isRememberPassWord = preferences.getBoolean("isRememberPassWord", false);

        if (isAutoLogin)
            getView().setAutoLandChecked();//自动登陆
        if (isRememberPassWord) {
            getView().setRememberChecked();//记住密码
            String account = preferences.getString("account", "");//账号
            String password = preferences.getString("password", "");//密码
            getView().setAccount(account);//设置账号
            getView().setPassword(password);//设置密码
        }
    }


    private SharedPreferences getShare(int occu) {
        return getView().getContext().getSharedPreferences
                ("loginShare", MODE_PRIVATE);
    }

}
