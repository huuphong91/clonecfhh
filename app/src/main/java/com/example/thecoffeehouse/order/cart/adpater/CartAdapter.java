package com.example.thecoffeehouse.order.cart.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private Context context;
    private List<Cart> cartList;
    private OnOrderListCartListener mListener;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder (LayoutInflater.from (context).inflate (R.layout.list_product_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartList.get (position);
        holder.bindTo (cart);
        holder.itemView.setOnClickListener (v -> mListener.onListCartItemClickListener (cart));
    }

    @Override
    public int getItemCount() {
        return cartList.size ();
    }

    public void setValues(List<Cart> values) {
        cartList.clear ();
        cartList.addAll (values);
        notifyDataSetChanged ();
    }

    public void setListener(OnOrderListCartListener onOrderListCartListener) {
        this.mListener = onOrderListCartListener;
    }
}
