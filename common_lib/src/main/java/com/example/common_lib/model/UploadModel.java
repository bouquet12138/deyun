package com.example.common_lib.model;

import android.text.TextUtils;
import android.util.Log;

import com.example.baselib.listener.OnGetInfoListener;
import com.example.baselib.util.OkHttpUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class UploadModel {

    private static final String TAG = "UploadModel";

    /**
     * 同步上传图片
     *
     * @param imagePath
     * @param user_id
     * @param uuid
     * @param occu
     * @param image_type
     */
    public String uploadImage(String imagePath, int user_id, String uuid, int occu, String image_type) {
        String address = ServerInfo.getServerAddress("upload_image");

        Log.d(TAG, "uploadImage: " + address);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("user_id", user_id + "");
        map.put("uuid", uuid);
        map.put("occu", occu + "");
        map.put("image_type", image_type);

        String responseData = OkHttpUtil.postAynForm(address, map, "image", imagePath);

        Log.d(TAG, "uploadImage: " + responseData);

        if (!TextUtils.isEmpty(responseData)) {
            BaseBean<String> info = BaseBean.analysisBaseBean(responseData, String.class);
            if (info != null && info.getCode() == 1)//得到返回的信息
                return info.getData();
        }
        return null;
    }

    /**
     * 异步上传图片
     */
    public void uploadImage(String imageUrl, int user_id, String uuid, int occu, String image_type,
                            OnGetInfoListener<BaseBean<String>> listener) {

        String address = ServerInfo.getServerAddress("upload_image");

        Log.d(TAG, "uploadImage: " + address);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("user_id", user_id + "");
        map.put("uuid", uuid);
        map.put("occu", occu + "");
        map.put("image_type", image_type);

        OkHttpUtil.postForm(address, map, "image", imageUrl, new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                listener.onComplete();
                listener.onNetError();//网络错误
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<String> info = BaseBean.analysisBaseBean(responseData, String.class);
                if (info == null)
                    listener.onNetError();
                else
                    listener.onResult(info);//传递出去
            }
        });
    }

    /**
     * 同步提交Json
     *
     * @return
     */
    public BaseBean<Integer> uploadJson(String address, String json) {

        String responseData = OkHttpUtil.postAynJson(address, json);

        Log.d(TAG, "uploadJson: address " + address);
        Log.d(TAG, "uploadJson: json " + json);
        Log.d(TAG, "uploadJson: " + responseData);

        return BaseBean.analysisBaseBean(responseData, Integer.class);
    }

}
