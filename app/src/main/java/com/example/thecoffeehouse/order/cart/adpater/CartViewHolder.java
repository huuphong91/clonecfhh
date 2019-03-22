package com.example.thecoffeehouse.order.cart.adpater;

import android.view.View;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.order.FormatPrice;
import com.example.thecoffeehouse.order.cart.model.Cart;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView tvCount;
    private TextView tvName;
    private TextView tvTotal;
    private TextView tvSize;
    private FormatPrice formatPrice = new FormatPrice ();
    private int count;

    public CartViewHolder(@NonNull View itemView) {
        super (itemView);
        tvCount = itemView.findViewById (R.id.tv_count);
        tvName = itemView.findViewById (R.id.tv_name_product);
        tvTotal = itemView.findViewById (R.id.tv_price_cart);
        tvSize = itemView.findViewById (R.id.tv_size_product);
    }

    public void bindTo(Cart cart) {
        tvName.setText (cart.getName ());
        count= Integer.parseInt (String.valueOf (cart.getCount ()));
        tvCount.setText (String.valueOf (cart.getCount ()));
        tvSize.setText (String.valueOf (cart.getSize ()));
        tvTotal.setText (formatPrice.formatPrice (cart.getPrice()*count));
    }
}
