package com.example.common_lib.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baselib.base.ActivityCollector;
import com.example.baselib.base.MyApplication;
import com.example.baselib.util.GsonUtil;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.UserBean;


public class NowUserInfo {

    private static final String TAG = "NowUserInfo";

    private static UserBean sUserBean;

    /**
     * 设置学生信息
     */
    public static void setNowUserInfo(UserBean userBean) {
        sUserBean = userBean;//设置一下
        Log.d(TAG, "setUser: " + userBean);
        SharedPreferences sharedPreferences = MyApplication.getContext().
                getSharedPreferences("nowUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userBean", GsonUtil.GsonString(userBean));
        editor.apply();
    }

    /**
     * 得到当前学生信息
     */
    public static UserBean getNowUserInfo() {
        if (sUserBean == null) {
            SharedPreferences sharedPreferences = MyApplication.getContext().
                    getSharedPreferences("nowUser", Context.MODE_PRIVATE);
            String userInfo = sharedPreferences.getString("userBean", "");//学生信息
            sUserBean = GsonUtil.GsonToBean(userInfo, UserBean.class);
        }

        if (sUserBean == null) {//还是空的话
            Log.e(TAG, "getNowStudentInfo: ");
            // Toast.makeText(MyApplication.getContext(), "用户信息获取失败", Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAll();//销毁所有activity
            ARouter.getInstance().build(ARouterContract.LOGIN_LOGIN) // 目标页面
                    .navigation();
        }
        return sUserBean;
    }


    /**
     * 得到当前用户的id
     *
     * @return
     */
    public static int getNowUserId() {

        UserBean userBean = getNowUserInfo();
        if (userBean == null)
            return 0;
        return userBean.getUser_id();//返回用户id

    }

    /**
     * 得到当前用户的手机号
     *
     * @return
     */
    public static String getNowUserPhone() {

        UserBean userBean = getNowUserInfo();
        if (userBean == null)
            return "";
        return userBean.getPhone_num();//返回用户id

    }


}
