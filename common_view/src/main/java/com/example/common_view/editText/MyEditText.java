package com.example.common_view.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.common_view.R;

import androidx.annotation.Nullable;

public class MyEditText extends LinearLayout {

    private View rootView;//根布局
    private EditText editText;//编辑文本
    private ImageView deleteImage;//删除图片

    private String mHintStr;
    private Integer mInputType;
    private Integer mMaxLength;


    public MyEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        mHintStr = typedArray.getString(R.styleable.MyEditText_hint);
        mInputType = typedArray.getInteger(R.styleable.MyEditText_inputType, 0);//输入类型
        mMaxLength = typedArray.getInteger(R.styleable.MyEditText_maxLength, -1);

        typedArray.recycle();

        initView();
        initListener();
    }

    private void initView() {
        rootView = inflate(getContext(), R.layout.layout_editor, this);
        editText = rootView.findViewById(R.id.editText);//编辑文本
        editText.setHint(mHintStr);//设置提醒文本
        if (mInputType == 1)
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        else if (mInputType == 2) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (mMaxLength != -1)
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});

        deleteImage = rootView.findViewById(R.id.deleteBtn);//删除按钮
    }

    private void initListener() {

        deleteImage.setOnClickListener(v -> {
            editText.setText("");//将文本设为空
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    deleteImage.setVisibility(INVISIBLE);//删除按钮可见
                } else {
                    if (editText.hasFocus() && deleteImage.getVisibility() != VISIBLE)//当长度为0时，如果清空按钮可见，那么就消失
                        deleteImage.setVisibility(VISIBLE);
                }

                if (mOnTextChangedListener != null)
                    mOnTextChangedListener.afterTextChanged();//文字更新
            }
        });

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {//当获得焦点
                if (!TextUtils.isEmpty(editText.getText().toString())) {//如果输入文本框的内容不为空
                    deleteImage.setVisibility(VISIBLE);//删除按钮可见
                }
            } else {
                deleteImage.setVisibility(INVISIBLE);//不可见
            }
        });

    }

    /**
     * 设置提示文本
     */
    public void setHintText(String hintText) {
        editText.setHint(hintText);
    }

    /**
     * 设置文本
     */
    public void setText(String text) {
        editText.setText(text);
    }

    /**
     * 得到文本
     */

    public String getText() {
        return editText.getText().toString();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        editText.setEnabled(enabled);
        deleteImage.setEnabled(enabled);
        if (enabled)
            deleteImage.setImageAlpha(255);
        else
            deleteImage.setImageAlpha(127);
    }

    public interface OnTextChangedListener {
        void afterTextChanged();
    }

    private OnTextChangedListener mOnTextChangedListener;

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        mOnTextChangedListener = onTextChangedListener;
    }
}
