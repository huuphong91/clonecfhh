package com.example.thecoffeehouse.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.thecoffeehouse.ConvertToUTF8;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.order.detail.DetailDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductViewHolder> implements Filterable {

    private Context mContext;
    private List<DataItem> mListValues, mFilterList;
    private CustomFilter filter;
    private OnOrderListItemInteractionListener mListener;

    public OrderProductAdapter(Context mContext, List<DataItem> mListValues) {
        this.mContext = mContext;
        this.mListValues = mListValues;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderProductViewHolder (LayoutInflater.from (mContext)
                .inflate (R.layout.list_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        DataItem product = mListValues.get (position);
        holder.bindToViewHolder (product);
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener (product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListValues.size ();
    }

    public void setValues(List<DataItem> values) {
        mListValues = values;
        mFilterList = values;
        notifyDataSetChanged ();
    }


    public void setListener(OnOrderListItemInteractionListener mListener) {
        this.mListener = mListener;

    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter ();
        }
        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults ();
            List<DataItem> resultList = new ArrayList<> ();
            if (constraint != null && constraint.length () > 0) {
                constraint = constraint.toString ().toLowerCase ();
                for (DataItem item : mFilterList) {
                    if (ConvertToUTF8.onConvert (item.getProductName ()).contains (constraint)
                            || item.getProductName ().toLowerCase ().contains (constraint)
                            || String.valueOf (item.getBasePrice ()).contains (constraint)) {
                        resultList.add (item);
                    }
                }
                results.count = resultList.size ();
                results.values = resultList;
            } else {
                results.count = resultList.size ();
                results.values = resultList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mListValues = (ArrayList<DataItem>) results.values;
            notifyDataSetChanged ();
        }
    }
}
