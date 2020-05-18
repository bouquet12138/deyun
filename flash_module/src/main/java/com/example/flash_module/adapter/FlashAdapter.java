package com.example.flash_module.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baselib.util.CollectionsUtil;
import com.example.baselib.util.DateUtil;
import com.example.baselib.util.DensityUtil;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.java_bean.FlashContentBean;
import com.example.flash_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FlashAdapter extends RecyclerView.Adapter<FlashAdapter.ViewHolder> {

    private List<FlashBean> mFlashBeans;//快讯bean
    private Context mContext;

    public FlashAdapter(List<FlashBean> flashBeans) {
        mFlashBeans = flashBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_item_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mViewGroup.setRadiusAndShadow(DensityUtil.dipToPx(5), DensityUtil.dipToPx(0), 1f);
        FlashBean bean = mFlashBeans.get(position);
        if (TextUtils.isEmpty(bean.getTitle()))
            holder.mTitleText.setVisibility(View.GONE);//不可见
        else {
            holder.mTitleText.setVisibility(View.VISIBLE);//可见
            holder.mTitleText.setText(bean.getTitle());//设置标题
        }
        holder.mDateAndReadVolume.setText(DateUtil.formatDate(bean.getPublic_time())
                + " 阅览量：" + bean.getReading_volume());

        List<Integer> imageList = new ArrayList<>();

        if (!CollectionsUtil.isEmpty(bean.getContent_list()))
            for (FlashContentBean flashContentBean : bean.getContent_list())
                imageList.add(flashContentBean.getImage_id());

        setImage(imageList, holder);
        initListener(holder, position, imageList);
    }

    /**
     * 初始化监听
     *
     * @param holder
     * @param position
     * @param imageList
     */
    private void initListener(ViewHolder holder, int position, List<Integer> imageList) {
        holder.mViewGroup.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onClick(mFlashBeans.get(position));
        });
    }

    private static final String TAG = "FlashAdapter";

    private void setImage(List<Integer> imageList, ViewHolder holder) {
        if (!CollectionsUtil.isEmpty(imageList)) {
            if (imageList.size() == 1) {
                holder.mImage.setVisibility(View.VISIBLE);//可见
                holder.mMyGridLayout.setVisibility(View.GONE);//图片列表不可见

                loadImg(imageList.get(0), holder.mImage);
            } else {
                holder.mImage.setVisibility(View.GONE);//不可见
                holder.mMyGridLayout.setVisibility(View.VISIBLE);//网格图片可见

                loadImg(imageList.get(0), holder.mImage1);
                loadImg(imageList.get(1), holder.mImage2);
                Log.d(TAG, "setImage: 加载图片 ");

                if (imageList.size() == 2) {
                    holder.mImage3.setVisibility(View.GONE);//图片3不可见
                } else {
                    holder.mImage3.setVisibility(View.VISIBLE);//图片3可见
                    loadImg(imageList.get(2), holder.mImage3);
                }
            }
        } else {
            holder.mImage.setVisibility(View.GONE);//图片消失
            holder.mMyGridLayout.setVisibility(View.GONE);//图片列表消失
        }

        if (!CollectionsUtil.isEmpty(imageList) && imageList.size() > 3) {
            holder.mImageGroup.setVisibility(View.VISIBLE);//可见
            holder.mImageSizeText.setText((imageList.size() - 3) + "");
        } else
            holder.mImageGroup.setVisibility(View.GONE);//不可见

    }

    /**
     * 加载图片
     */
    private void loadImg(Integer imagePath, ImageView imageView) {

        Glide.with(mContext).load(imagePath).
                placeholder(R.drawable.image_loading).error(R.drawable.image_error).
                into(imageView);//设置图片
    }

    public interface OnItemClickListener {
        void onClick(FlashBean flashBean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mFlashBeans == null ? 0 : mFlashBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mViewGroup;
        TextView mTitleText;
        ImageView mImage;
        ViewGroup mMyGridLayout;
        ImageView mImage1;
        ImageView mImage2;
        ImageView mImage3;
        TextView mDateAndReadVolume;

        ViewGroup mImageGroup;
        TextView mImageSizeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewGroup = itemView.findViewById(R.id.viewGroup);
            mTitleText = itemView.findViewById(R.id.describeText);
            mImage = itemView.findViewById(R.id.image);
            mMyGridLayout = itemView.findViewById(R.id.myGridLayout);
            mImage1 = itemView.findViewById(R.id.image1);
            mImage2 = itemView.findViewById(R.id.image2);
            mImage3 = itemView.findViewById(R.id.image3);
            mDateAndReadVolume = itemView.findViewById(R.id.dateAndReadVolume);

            mImageGroup = itemView.findViewById(R.id.imageGroup);
            mImageSizeText = itemView.findViewById(R.id.imageSizeText);
        }
    }
}