package com.example.main_module.activity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.baselib.util.DensityUtil;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.main_module.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class PromotionActivity extends AppMvpBaseActivity {

    private ImageView mAppImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_title_color));
        initView();
        setSubmitEnable(false);
        Glide.with(this).load( getQrCode(DensityUtil.dipToPx(300),
                DensityUtil.dipToPx(300), "http://app/a.jpg")).into(mAppImage);
    }

    @Override
    protected String getTitleName() {
        return "App推广";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_promotion;
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mAppImage = findViewById(R.id.appImage);
    }

    public Bitmap getQrCode(int width, int height, String content) {
        try {
            Map<EncodeHintType, Object> hints = null;
            String encoding = getEncoding(content);//获取编码格式
            if (encoding != null) {
                hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            BitMatrix result = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);//通过字符串创建二维矩阵
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;//根据二维矩阵数据创建数组
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建位图
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);//设置位图像素集为数组
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}
