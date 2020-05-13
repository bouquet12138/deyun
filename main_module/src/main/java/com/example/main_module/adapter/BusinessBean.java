package com.example.main_module.adapter;

public class BusinessBean {

    private int mImageId;//业务图片

    private String mBusinessDescribe;//业务描述

    public BusinessBean(int imageId, String businessDescribe) {
        mImageId = imageId;
        mBusinessDescribe = businessDescribe;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public String getBusinessDescribe() {
        return mBusinessDescribe;
    }

    public void setBusinessDescribe(String businessDescribe) {
        mBusinessDescribe = businessDescribe;
    }
}
