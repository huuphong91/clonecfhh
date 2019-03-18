package com.example.thecoffeehouse.order.cart.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>  {
    private Context context;
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder (LayoutInflater.from(context).inflate (R.layout.list_product_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
