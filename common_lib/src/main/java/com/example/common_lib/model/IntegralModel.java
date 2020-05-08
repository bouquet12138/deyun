package com.example.common_lib.model;

import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.IntegralBean;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class IntegralModel {

    private static final String TAG = "IntegralModel";

    /**
     * 用户获得总积分
     *
     * @param user_id  用户id
     * @param listener
     */
    public void totalIntegral(int user_id, OnGetInfoListener<BaseBean<Integer>> listener) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("total_integral"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<Integer> baseBean = BaseBean.analysisBaseBean(responseData, Integer.class);

                if (baseBean != null)
                    listener.onResult(baseBean);//传递出去
                else
                    listener.onNetError();

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                listener.onComplete();
                listener.onNetError();//网络错误
            }
        });
    }

    /**
     * 积分记录
     *
     * @param user_id
     * @param listener
     */
    public void total_integral_record(int user_id, OnGetInfoListener<BaseBean<List<IntegralBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("total_integral_record"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<IntegralBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, IntegralBean.class);

                if (baseBean != null)
                    listener.onResult(baseBean);//传递出去
                else
                    listener.onNetError();

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                listener.onComplete();
                listener.onNetError();//网络错误
            }
        });

    }

    /**
     * 积分互转
     *
     * @param integralBean
     * @param listener
     */
    public void integral_rotation(IntegralBean integralBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(integralBean, "integral_rotation", listener);
    }

}
