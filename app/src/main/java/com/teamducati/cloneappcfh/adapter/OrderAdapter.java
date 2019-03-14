package com.teamducati.cloneappcfh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolderOder> {

    private Context context;
    private List<DataItem> productList;
//    private IItemClickCallback iItemClickCallback;

    public OrderAdapter(@NonNull Context context) {
        this.context = context;
//        this.iItemClickCallback = iItemClickCallback;
    }

    public void setValues(List<DataItem> values) {
        productList = values;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolderOder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_oder, viewGroup, false);
        return new ViewHolderOder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolderOder viewHolderOder, final int position) {
        DataItem item = productList.get(position);
        Glide.with(context)
                .load(item.getImage())
                .centerCrop()
                .placeholder(R.drawable.icon_loading)
                .into(viewHolderOder.ivDrink);

        viewHolderOder.tvNameDrink.setText(item.getProductName());
        viewHolderOder.tvClassify.setText(item.getVariants().get(0).getVal());
        viewHolderOder.tvPrice.setText(String.valueOf(item.getBasePrice()));

        Glide.with(context)
                .load(R.drawable.icon_add)
                .centerCrop()
                .placeholder(R.drawable.icon_loading)
                .into(viewHolderOder.ivAdd);
    }

    @Override
    public int getItemCount() {
        if (productList != null)
            return productList.size();
        return 0;
    }

    public class ViewHolderOder extends RecyclerView.ViewHolder {

        private ImageView ivDrink;
        private TextView tvNameDrink;
        private TextView tvClassify;
        private TextView tvPrice;
        private ImageView ivAdd;

        public ViewHolderOder(@NonNull View itemView) {
            super(itemView);

            ivDrink = itemView.findViewById(R.id.iv_drink);
            tvNameDrink = itemView.findViewById(R.id.tv_name);
            tvClassify = itemView.findViewById(R.id.tv_classify);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivAdd = itemView.findViewById(R.id.iv_add);
        }
    }
}



