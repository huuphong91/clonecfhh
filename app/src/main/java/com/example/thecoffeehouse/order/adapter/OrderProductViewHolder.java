package com.example.thecoffeehouse.order.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;

import androidx.recyclerview.widget.RecyclerView;

public class OrderProductViewHolder extends RecyclerView.ViewHolder {

    private TextView mTxtProductName, mTxtProductSize, mTxtProductPrice;
    private ImageView mProductImage;

    public OrderProductViewHolder(View view) {
        super (view);
        mTxtProductName = view.findViewById (R.id.tv_name_product);
        mTxtProductSize = view.findViewById (R.id.tv_size);
        mTxtProductPrice = view.findViewById (R.id.tv_price_cart);
        mProductImage = view.findViewById (R.id.img_product);
    }

    public void bindToViewHolder(DataItem currentProduct) {
        mTxtProductName.setText (currentProduct.getProductName ());
        mTxtProductSize.setText (currentProduct.getVariants ().get (0).getVal ());
        mTxtProductPrice.setText (String.valueOf (currentProduct.getBasePrice ()));
        Glide.with (itemView.getContext ()).load (currentProduct.getImage ()).into (mProductImage);
    }

}
