package com.example.baselib.util;


import com.example.baselib.base.MyApplication;

public class DensityUtil {

    public static int dipToPx(float dpValue){
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDp(float pxValue){
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
