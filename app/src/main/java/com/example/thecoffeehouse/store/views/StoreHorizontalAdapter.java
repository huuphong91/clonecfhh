package com.example.thecoffeehouse.store.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.store.Store;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreHorizontalAdapter extends RecyclerView.Adapter<StoreHorizontalAdapter.StoreViewHolder> {

    private Context mContext;
    private List<Store> mList;
    private OnItemClickListener itemClickListener = null;

    public StoreHorizontalAdapter(Context mContext, List<Store> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_store_horizontal, viewGroup, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, int i) {
        Store store = mList.get(i);
        Log.d("MY_TAG", "onBindViewHolder: DISTANCE"+store.storeDistance);
        storeViewHolder.tvStoreName.setText(store.storeName);
        storeViewHolder.tvStoreDistrict.setText(store.storeAddress.district);
        Glide.with(mContext).load(store.storeImages.get(0))
                .placeholder(R.mipmap.store_placeholder)
                .override(400, 300)
                .optionalCenterCrop()
                .into(storeViewHolder.storeImage);
    }

    @Override
    public int getItemCount() {
        Log.d(this.getClass().getName(), "getItemCount: ");
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        ImageView storeImage;
        TextView tvStoreName;
        TextView tvStoreDistrict;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImage = itemView.findViewById(R.id.storeImage);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvStoreDistrict = itemView.findViewById(R.id.tvStoreDistrict);
            itemView.setOnClickListener(v -> itemClickListener.onClick(v, getPosition()));
        }

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
