package com.teamducati.cloneappcfh.screen.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.screen.store.ProvinceAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>{
    private Context context;
    private List<String> mListCategory;
    private LayoutInflater mInflater;

    public CategoryAdapter(Context context, List<String> mListCategory) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mListCategory = mListCategory;
    }

    class CategoryAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameCategory;

        private CategoryAdapterViewHolder(View itemView) {
            super(itemView);
            mNameCategory = itemView.findViewById(R.id.txtCategoryItem);
        }
    }

    @NonNull
    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_category, parent, false);
        return new CategoryAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterViewHolder holder, int position) {
        holder.mNameCategory.setText(mListCategory.get(position));

        holder.mNameCategory.setOnClickListener(v -> {
            Toast.makeText(context, ""+mListCategory.get(position), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null)
            return mListCategory.size();
        else return 0;
    }


}
