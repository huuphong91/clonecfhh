package com.teamducati.cloneappcfh.screen.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.screen.order.ViewDialogAdd;
import com.teamducati.cloneappcfh.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolderOder> {

    private Context context;
    private List<DataItem> dataItemList;
    private FragmentManager fragmentManager;

    public OrderAdapter(@NonNull Context context, List<DataItem> values, FragmentManager fragmentManager) {
        this.context = context;
        this.dataItemList = values;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolderOder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_oder, viewGroup, false);
        return new ViewHolderOder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOder viewHolderOder, final int position) {
        DataItem item = dataItemList.get(viewHolderOder.getAdapterPosition());
        Glide.with(context)
                .load(item.getImage())
                .centerCrop()
                .placeholder(R.drawable.icon_loading)
                .into(viewHolderOder.ivDrink);

        viewHolderOder.tvNameDrink.setText(item.getProductName());
        if ("Nhỏ".equals(item.getVariants().get(0).getVal())) {
            viewHolderOder.tvClassify.setText(item.getVariants().get(0).getVal());
        }
        viewHolderOder.tvPrice.setText(Utils.formatMoney(item.getBasePrice()));

        Glide.with(context)
                .load(R.drawable.icon_add)
                .centerCrop()
                .placeholder(R.drawable.icon_loading)
                .into(viewHolderOder.ivAdd);

        viewHolderOder.itemView.setOnClickListener(v -> {
            ViewDialogAdd.newInstance(item).show(fragmentManager, "Detail");
        });
    }

    @Override
    public int getItemCount() {
        if (dataItemList != null)
            return dataItemList.size();
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



