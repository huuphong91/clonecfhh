package com.teamducati.cloneappcfh.screen.store.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.DistrictsItem;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StoresItem;
import com.teamducati.cloneappcfh.utils.eventsbus.EventBusStore;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreInProvinceAdapter extends RecyclerView.Adapter<StoreInProvinceAdapter.StoreInProvinceViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<String> mName;
    private List<DistrictsItem> mApiDistricts;

    public StoreInProvinceAdapter(Context context, List<String> mName, List<DistrictsItem> mApiStores) {
        this.context = context;
        this.mName = mName;
        this.mApiDistricts = mApiStores;
        mInflater = LayoutInflater.from(context);
    }

    class StoreInProvinceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private TextView mStoreInProvince;

        private StoreInProvinceViewHolder(View itemView) {
            super(itemView);

            mStoreInProvince = itemView.findViewById(R.id.txtStoreInProvince);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
    public StoreInProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_store_in_province, parent, false);
        return new StoreInProvinceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreInProvinceViewHolder holder, int position) {
        holder.mStoreInProvince.setText(mName.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistrictsItem districtsItem = mApiDistricts.get(position);
                Double lat = Double.parseDouble(districtsItem.getStores().get(0).getLatitude());
                Double lon = Double.parseDouble(districtsItem.getStores().get(0).getLongitude());
                String name = mName.get(position);
                String address = districtsItem.getStores().get(0).getAddress().getStreet();
                List<StoresItem> storesItems = districtsItem.getStores();
                EventBus.getDefault().post(new EventBusStore(lat, lon, name, address, storesItems, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mName != null)
            return mName.size();
        else return 0;
    }
}
