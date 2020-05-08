package com.example.common_lib.model;

import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.IntegralBean;
import com.example.common_lib.java_bean.PayrollBean;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PayrollModel {

    private static final String TAG = "PayrollModel";

    /**
     * 工资记录
     *
     * @param user_id
     * @param listener
     */
    public void total_payroll_record(int user_id, OnGetInfoListener<BaseBean<List<PayrollBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("payroll_record"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<PayrollBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, PayrollBean.class);

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
     * 工资互转
     *
     * @param payrollBean
     * @param listener
     */
    public void integral_rotation(PayrollBean payrollBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(payrollBean, "payroll_rotation", listener);
    }


    /**
     * 用户总工资
     *
     * @param user_id  用户id
     * @param listener
     */
    public void total_payroll(int user_id, OnGetInfoListener<BaseBean<Integer>> listener) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("total_payroll"), jsonObject.toString(), new okhttp3.Callback() {

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


}
