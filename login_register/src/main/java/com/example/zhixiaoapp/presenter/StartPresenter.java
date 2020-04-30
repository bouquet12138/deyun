package com.example.zhixiaoapp.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.baselib.base.MVPBasePresenter;
import com.example.zhixiaoapp.contract.StartContract;

import static android.content.Context.MODE_PRIVATE;

public class StartPresenter extends MVPBasePresenter<StartContract.IView> implements StartContract.IPresenter {

    /* private LoginModel mModel = new LoginModel();*/

    private final int ON_RESULT = 0;
    private final int END = 1;

    private boolean mLoginSuccess = false;//是否登陆成功

    private int mOccu;//身份标识
    private String mAccount;
    private String mPassword;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case ON_RESULT:

                    break;
                case END:
                    Log.d("StartPresenter", "handleMessage: ");
                    if (mLoginSuccess)
                        getView().startMainActivity();
                    else
                        getView().startLoginActivity();//启动登陆界面
                    break;
            }
        }
    };

    public void login() {
        if (!isViewAttached())
            return;

    }


    @Override
    public void initInfo() {
        if (!isViewAttached())
            return;
        Log.d("StartPresenter", "initInfo: ");
        mHandler.sendEmptyMessageDelayed(END, 4000);//发送空消息

        SharedPreferences preferences = getShare();//得到一下sharePreference
        boolean isAutoLogin = preferences.getBoolean("isAutoLogin", false);

        if (isAutoLogin) {
            mAccount = preferences.getString("account", "");//账号
            mPassword = preferences.getString("password", "");//密码
            login();//去登陆
        }
    }

    @Override
    public int getOccu() {
        return mOccu;
    }

    private SharedPreferences getShare() {
        return getView().getContext().getSharedPreferences
                ("loginShare", MODE_PRIVATE);

    }

}
