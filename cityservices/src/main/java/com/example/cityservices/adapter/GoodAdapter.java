package com.example.cityservices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityservices.R;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.ImageBean;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {

    private int mWidth;//宽
    private Context mContext;

    private List<ImageBean> mImageBeans;//图片列表

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_good, parent, false);
     /*   ImageView imageView = view.findViewById(R.id.good_image);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();//布局管理
        layoutParams.width = mWidth / 3;
        layoutParams.height = mWidth / 3;
        imageView.setLayoutParams(layoutParams);*/
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageBean bean = mImageBeans.get(position);//图片bean
        Glide.with(mContext).
                load(ServerInfo.getImageAddress(bean.getImage_url())).
                placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mGood_image);//添加图片
        holder.mImage_describe.setText(bean.getImage_describe());//设置图片的描述
    }

    @Override
    public int getItemCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mGood_image;
        TextView mImage_describe;

        public ViewHolder(View itemView) {
            super(itemView);
            mGood_image = itemView.findViewById(R.id.good_image);
            mImage_describe = itemView.findViewById(R.id.image_describe);
        }
    }
}