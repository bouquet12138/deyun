package com.example.flash_module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.util.ScreenUtil;
import com.example.common_lib.java_bean.FlashContentBean;
import com.example.flash_module.R;

import java.util.List;


public class FlashContentAdapter extends RecyclerView.Adapter<FlashContentAdapter.ViewHolder> {

    private List<FlashContentBean> mPrizeContentBeans;
    private Context mContext;

    public FlashContentAdapter(List<FlashContentBean> flashContentBeans) {
        mPrizeContentBeans = flashContentBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_item_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlashContentBean bean = mPrizeContentBeans.get(position);

        if (TextUtils.isEmpty(bean.getContent()))
            holder.mContent.setVisibility(View.GONE);
        else
            holder.mContent.setVisibility(View.VISIBLE);

        holder.mDescribeText.setVisibility(View.GONE);//先不可见

        holder.mContent.setText(bean.getContent());
        //   holder.mDescribeText.setText(bean.getDescriptions());
        //Glide.with(mContext).load(bean.getImages()).into(holder.mImageView);//加载图片

        setImageLayout(holder, bean.getImage_id());
    }

    private void setImageLayout(ViewHolder holder, int images) {

        Glide.with(mContext).asBitmap()
                .load(images).into(new BitmapImageViewTarget(holder.mImageView) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @androidx.annotation.Nullable Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
                Log.d(TAG, "onResourceReady: " + resource.getWidth() + " " + resource.getHeight());
                int height = resource.getHeight();
                int width = resource.getWidth();
                int imageWidth = ScreenUtil.getScreenWidth(mContext) - DensityUtil.dipToPx(30);
                int imageHeight = (int) (imageWidth * height / (float) width);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
                layoutParams.topMargin = DensityUtil.dipToPx(10);

                holder.mImageView.setLayoutParams(layoutParams);
            }
        });

    }

    private static final String TAG = "FlashContentAdapter";

    @Override
    public int getItemCount() {
        return mPrizeContentBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mContent;
        ImageView mImageView;
        TextView mDescribeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
            mImageView = itemView.findViewById(R.id.imageView);
            mDescribeText = itemView.findViewById(R.id.describeText);
        }
    }
}