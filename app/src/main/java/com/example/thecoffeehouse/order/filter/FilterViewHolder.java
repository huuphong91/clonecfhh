package com.example.thecoffeehouse.order.filter;

import android.view.View;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.Category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterViewHolder extends RecyclerView.ViewHolder {

    private TextView txtFilterTitle;

    public FilterViewHolder(@NonNull View itemView) {
        super (itemView);
        txtFilterTitle = itemView.findViewById (R.id.txt_cate_title);
    }

    public void bindTo(Category category) {
        txtFilterTitle.setText (category.getName ());
    }
}
