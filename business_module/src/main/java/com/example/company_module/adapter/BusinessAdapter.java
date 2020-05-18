package com.example.company_module.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.company_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    private static final int NORMAL_VIEW = 0;
    private static final int FOOT_VIEW = 1;

    private List<BusinessBean> mBusinessBeans;

    public BusinessAdapter(List<BusinessBean> businessBeans) {
        mBusinessBeans = businessBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_business, parent, false);
            ViewHolder viewHolder = new ViewHolder(view, NORMAL_VIEW);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_foot, parent, false);
            ViewHolder viewHolder = new ViewHolder(view, FOOT_VIEW);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == NORMAL_VIEW) {
            BusinessBean bean = mBusinessBeans.get(position);
            //holder.mLinearLayout.setRadiusAndShadow(DensityUtil.dipToPx(5), DensityUtil.dipToPx(5), 0.2f);
            holder.mImageView.setImageResource(bean.getImageId());
            holder.mBusinessDescribe.setText(bean.getBusinessDescribe());//得到描述
        }
    }

    private static final String TAG = "BusinessAdapter";

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: position " + position + " " + (getItemCount() - 1));
        if (position == getItemCount() - 1)
            return FOOT_VIEW;
        return NORMAL_VIEW;
    }

    @Override
    public int getItemCount() {
        return mBusinessBeans == null ? 1 : mBusinessBeans.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mLinearLayout;

        ImageView mImageView;
        TextView mBusinessDescribe;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_VIEW) {
                mLinearLayout = itemView.findViewById(R.id.linearLayout);
                mImageView = itemView.findViewById(R.id.imageView);
                mBusinessDescribe = itemView.findViewById(R.id.businessDescribe);
            }
        }
    }
}