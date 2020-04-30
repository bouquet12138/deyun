package com.example.common_lib.bean;

import java.io.Serializable;

public class ImageUploadBean implements Serializable {

    public ImageUploadBean() {
    }

    public ImageUploadBean(String imageLocalUrl, boolean isUpload, String imageServerUrl) {
        mImageLocalUrl = imageLocalUrl;
        mIsUpload = isUpload;
        mImageServerUrl = imageServerUrl;
    }

    private String mImageLocalUrl;//图片本地地址

    private boolean mIsUpload = false;//是否上传

    private String mImageServerUrl;//图片服务器地址

    public String getImageLocalUrl() {
        return mImageLocalUrl;
    }

    public void setImageLocalUrl(String imageLocalUrl) {
        mImageLocalUrl = imageLocalUrl;
    }

    public boolean isUpload() {
        return mIsUpload;
    }

    public void setUpload(boolean upload) {
        mIsUpload = upload;
    }

    public String getImageServerUrl() {
        return mImageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        mImageServerUrl = imageServerUrl;
    }


}
