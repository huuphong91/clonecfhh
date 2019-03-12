package com.teamducati.cloneappcfh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreAdapterViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<StoresItem> mApiStores;
    private List<StoresItem> mStore;

    public StoreAdapter(Context context, List<StoresItem> mApiStores) {
        this.context = context;
        this.mApiStores = mApiStores;
        mInflater = LayoutInflater.from(context);
    }

    class StoreAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private ImageView imgStoreMap;
        private TextView txtNameStoreMap;
        private TextView txtAddress;
        private ItemClickListener itemClickListener;

        private StoreAdapterViewHolder(View itemView) {
            super(itemView);
            imgStoreMap = itemView.findViewById(R.id.imgStoreMap);
            txtNameStoreMap = itemView.findViewById(R.id.txtNameStoreMap);
            txtAddress = itemView.findViewById(R.id.txtAddress);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @NonNull
    @Override
    public StoreAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_store_map, parent, false);
        return new StoreAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapterViewHolder holder, int position) {
        StoresItem storesItem = mApiStores.get(holder.getAdapterPosition());
        holder.txtNameStoreMap.setText(mApiStores.get(holder.getAdapterPosition()).getName());
        holder.txtAddress.setText(mApiStores.get(holder.getAdapterPosition()).getAddress().getStreet());
        Glide.with(context)
                .load(mApiStores.get(holder.getAdapterPosition()).getImages())
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgStoreMap);

        holder.imgStoreMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mApiStores != null)
            return mApiStores.size();
        else return 0;
    }


}
