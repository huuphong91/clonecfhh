package com.teamducati.cloneappcfh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentOrderAdapter extends RecyclerView.Adapter<FragmentOrderAdapter.ViewHolderOder> {

    private Context context;
    private ArrayList<DataItem> productArrayList;
//    private IItemClickCallback iItemClickCallback;

    public FragmentOrderAdapter(@NonNull Context context, @NonNull ArrayList<DataItem> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
//        this.iItemClickCallback = iItemClickCallback;
    }

    @NonNull
    @Override
    public FragmentOrderAdapter.ViewHolderOder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_oder, viewGroup, false);
        return new ViewHolderOder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentOrderAdapter.ViewHolderOder viewHolderOder, final int position) {
        DataItem p = productArrayList.get(position);
//        Glide.with(context)
//                .load(p.getImage())
//                .centerCrop()
//                .placeholder(R.drawable.icon_loading)
//                .into(viewHolderOder.ivDrink);
//
//        viewHolderOder.tvNameDrink.setText(p.getProductName());
//        //viewHolderOder.tvClassify.setText(p.get());
//        viewHolderOder.tvPrice.setText(String.valueOf(p.getBasePrice()));
//
//        Glide.with(context)
//                .load(R.drawable.icon_add)
//                .centerCrop()
//                .placeholder(R.drawable.icon_loading)
//                .into(viewHolderOder.ivAdd);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
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



