package com.example.thecoffeehouse.order.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.Category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    private Context mContext;
    private List<Category> mValues;
    private FilterAdapterInteractionListener mListener;

    public FilterAdapter(Context mContext, List<Category> mValues) {
        this.mContext = mContext;
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterViewHolder (
                LayoutInflater.from (mContext)
                        .inflate (R.layout.filter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        Category category = mValues.get (position);
        holder.bindTo (category);
        holder.itemView.setOnClickListener (v -> {
            mListener.onClickListener ();
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size ();
    }

    public void setValues(List<Category> categList) {
        mValues.clear ();
        mValues.addAll (categList);
        notifyDataSetChanged ();
    }

    public void setListener(FilterAdapterInteractionListener filterAdapterInteractionListener) {
        this.mListener = filterAdapterInteractionListener;
    }
}
