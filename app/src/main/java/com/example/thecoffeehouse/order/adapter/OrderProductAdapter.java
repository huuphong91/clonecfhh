package com.example.thecoffeehouse.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> {

    private Context mContext;
    private List<DataItem> mListValues;

    public OrderProductAdapter(Context mContext, List<DataItem> mListValues) {
        this.mContext = mContext;
        this.mListValues = mListValues;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderProductViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        DataItem product = mListValues.get(position);
        holder.bindToViewHolder(product);
    }
    public void setValues(List<DataItem> values) {
        mListValues = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListValues.size();
    }
}
