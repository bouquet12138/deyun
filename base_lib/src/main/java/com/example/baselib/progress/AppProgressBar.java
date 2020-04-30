package com.example.baselib.progress;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselib.R;
import com.example.baselib.util.DensityUtil;


/**
 * app加载进度类
 * Created by xiaohan on 2018/1/30.
 */

public class AppProgressBar extends Dialog {

    private String mHintStr;//提醒文字
    private TextView mHintText;//提醒文本
    private ImageView mGifImg;//gif图片

    public AppProgressBar(Context context, String hintStr) {
        super(context, R.style.DialogBackgroundNull);
        this.mHintStr = hintStr;//提醒文本
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_app_progress);

        initLayout();
        initView();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        window.setLayout(DensityUtil.dipToPx(130),
                WindowManager.LayoutParams.WRAP_CONTENT);

        layoutParams.x = 0;
        layoutParams.y = -DensityUtil.dipToPx(20);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mHintText = findViewById(R.id.hintText);
        mHintText.setText(mHintStr);

        mGifImg = findViewById(R.id.gifImg);
        Glide.with(getContext()).load(R.raw.blue_loading).into(mGifImg);//加载动态图片
        setCancelable(false);//不能取消
    }

    /**
     * 设置提醒文本
     */
    public void setHintText(String hint) {
        if (mHintText != null)
            mHintText.setText(hint);
    }

}
