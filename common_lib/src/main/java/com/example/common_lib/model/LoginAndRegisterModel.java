package com.example.common_lib.model;

import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginAndRegisterModel {

    private static final String TAG = "LoginAndRegisterModel";

    /**
     * 登陆
     * @param phone_num
     * @param login_password
     * @param listener
     */
    public void login(String phone_num, String login_password, OnGetInfoListener<BaseBean<UserBean>> listener) {

        String json = "{\"phone_num\": \"" + phone_num + "\",\"login_password\": \"" + login_password + "\"}";

        Log.d(TAG, "login: " + json);
        Log.d(TAG, "login: 地址 " + ServerInfo.getServerAddress("student_login"));
        OkHttpUtil.postJson(ServerInfo.getServerAddress("login"), json, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e);
                listener.onComplete();//完成
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                listener.onComplete();//完成
                BaseBean<UserBean> result = BaseBean.analysisBaseBean(responseData, UserBean.class);
                if (result == null)
                    listener.onNetError();
                else
                    listener.onResult(result);
            }
        });
    }

    /**
     * "phone_num": "1575215661",
     * 	"login_password": "123456",
     * 	"pay_password": "212121",
     * 	"recommend_user_phone": "12112333213"
     * 	"vertex_user_phone":"123"
     */
    /**
     * 注册
     * @param phone_num
     * @param login_password
     * @param listener
     */
    public void register(String phone_num, String login_password, String pay_password,String recommend_user_phone,
                         OnGetInfoListener<BaseBean<UserBean>> listener) {

    }

}
