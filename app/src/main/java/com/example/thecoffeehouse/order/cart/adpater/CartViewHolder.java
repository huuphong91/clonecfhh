package com.example.thecoffeehouse.order.cart.adpater;

import android.view.View;
import android.widget.TextView;

import com.example.thecoffeehouse.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView tvCount;
    private TextView tvName;
    private TextView tvTotal;
    private TextView tvSize;

    public CartViewHolder(@NonNull View itemView) {
        super (itemView);
        tvCount = itemView.findViewById (R.id.tv_count);
        tvName = itemView.findViewById (R.id.tv_name_product);
        tvTotal = itemView.findViewById (R.id.tv_price_cart);
        tvSize = itemView.findViewById (R.id.tv_size_product);
    }
}
