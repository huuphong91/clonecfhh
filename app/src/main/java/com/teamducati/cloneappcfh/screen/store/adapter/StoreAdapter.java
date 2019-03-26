package com.teamducati.cloneappcfh.screen.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.screen.store.DialogStoreDetail;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreAdapterViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<StoresItem> mApiStores;

    public StoreAdapter(Context context, List<StoresItem> mApiStores) {
        this.context = context;
        this.mApiStores = mApiStores;
        mInflater = LayoutInflater.from(context);
    }

    class StoreAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgStoreMap;
        private TextView txtNameStoreMap;
        private TextView txtAddress;

        private StoreAdapterViewHolder(View itemView) {
            super(itemView);
            imgStoreMap = itemView.findViewById(R.id.imgStoreMap);
            txtNameStoreMap = itemView.findViewById(R.id.txtNameStoreMap);
            txtAddress = itemView.findViewById(R.id.txtAddress);
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
        holder.txtNameStoreMap.setText(mApiStores.get(holder.getAdapterPosition()).getName());
        holder.txtAddress.setText(mApiStores.get(holder.getAdapterPosition()).getAddress().getStreet());
        Glide.with(context)
                .load(mApiStores.get(position).getImages().get(0))
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.imgStoreMap);

        holder.imgStoreMap.setOnClickListener(v -> {

            DialogStoreDetail dialogStoreDetail = new DialogStoreDetail();
            dialogStoreDetail.showDialog(context,
                    mApiStores.get(position).getName(),
                    mApiStores.get(position).getImages(),
                    Double.parseDouble(mApiStores.get(position).getLatitude()),
                    Double.parseDouble(mApiStores.get(position).getLongitude()),
                    mApiStores.get(position).getAddress().getFullAddress(),
                    mApiStores.get(position).getOpeningTime() + " - " +
                            mApiStores.get(position).getClosingTime(),
                    mApiStores.get(position).getPhone());
        });
    }

    @Override
    public int getItemCount() {
        if (mApiStores != null)
            return mApiStores.size();
        else return 0;
    }
}
