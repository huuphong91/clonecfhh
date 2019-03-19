package com.teamducati.cloneappcfh.screen.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.StatesItem;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ProvinceAdapterViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<StatesItem> mStatesItems;
    private StoreInProvinceAdapter adapter;

    public ProvinceAdapter(Context context,
                           List<StatesItem> mApiState) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mStatesItems = mApiState;
    }

    class ProvinceAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameProvince;
        private RecyclerView mListStore;

        private ProvinceAdapterViewHolder(View itemView) {
            super(itemView);
            mNameProvince = itemView.findViewById(R.id.txtNameProvince);
            mListStore = itemView.findViewById(R.id.rcvListStore);
        }

    }

    @NonNull
    @Override
    public ProvinceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_list_province, parent, false);
        return new ProvinceAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceAdapterViewHolder holder, int position) {
        holder.mNameProvince.setText(mStatesItems.get(position).getName());
        List<String> mStore = new ArrayList<>();
        for (int i = 0; i < mStatesItems.get(position).getDistricts().size(); i++) {
            mStore.add(mStatesItems.get(position).getDistricts().get(i).getName());
        }
        holder.mListStore.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.mListStore.setLayoutManager(linearLayout);
        adapter = new StoreInProvinceAdapter(context, mStore, mStatesItems.get(position).getDistricts());
        holder.mListStore.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        if (mStatesItems != null)
            return mStatesItems.size();
        else return 0;
    }
}
