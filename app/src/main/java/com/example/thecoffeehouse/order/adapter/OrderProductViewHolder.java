package com.example.thecoffeehouse.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderProductViewHolder extends RecyclerView.ViewHolder {

    private TextView mTxtProductName, mTxtProductSize, mTxtProductPrice;
    private ImageView mProductImage;
    private Context mContext;
    private View mView;

    public OrderProductViewHolder(@NonNull View view, Context mContext) {
        super(view);
        this.mContext = mContext;
        mTxtProductName = view.findViewById(R.id.tv_name_product);
        mTxtProductSize = view.findViewById(R.id.tv_size);
        mTxtProductPrice = view.findViewById(R.id.tv_cost);
        mProductImage = view.findViewById(R.id.img_product);
        this.mView = view;
    }

    public void bindToViewHolder(DataItem currentProduct) {
        mTxtProductName.setText(currentProduct.getProductName());
        mTxtProductSize.setText(currentProduct.getVariants().get(0).getVal());
        mTxtProductPrice.setText(String.valueOf(currentProduct.getPrice()));
        Glide.with(mContext).load(currentProduct.getImage()).into(mProductImage);
    }
}
