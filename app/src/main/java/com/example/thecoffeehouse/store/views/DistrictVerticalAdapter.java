package com.example.thecoffeehouse.store.views;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.store.StoreResponeObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistrictVerticalAdapter extends RecyclerView.Adapter<DistrictVerticalAdapter.DistrictViewholder> {

    final int TYPE_STATE = 1;
    final int TYPE_DISTRICT = 2;
    final String TAG = "DistrictVerticalAdapter";

    private List<StoreResponeObject.State> mlistState;
    private List<Object> mListItem;
    private Context context;
    private OnItemClickListener listener;

    public DistrictVerticalAdapter(List<StoreResponeObject.State> mlistState, Context context) {
        this.mlistState = mlistState;
        this.context = context;
        mListItem = new ArrayList<>();
        if (mlistState != null) {
            for (StoreResponeObject.State state : mlistState) {
                mListItem.add(state);
                mListItem.addAll(state.districts);
            }
            Log.d(TAG, "getItemCount: " + mListItem.size());
        }
    }

    @NonNull
    @Override
    public DistrictViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_STATE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_state, parent, false);
            return new DistrictViewholder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_district, parent, false);
            return new DistrictViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewholder holder, int position) {
        Log.d(TAG, "onBindViewHolder: type = " + getItemViewType(position));
        if (getItemViewType(position) == TYPE_STATE) {
            StoreResponeObject.State state = (StoreResponeObject.State) mListItem.get(position);
            holder.tvStateName.setText(state.stateName);
        } else {
            StoreResponeObject.District district = (StoreResponeObject.District) mListItem.get(position);
            holder.tvStateName.setText(district.districtName);
        }

        holder.tvStateName.setOnClickListener(v -> {
            listener.onClick(v,mListItem.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: " + position);
        if (mListItem.get(position) instanceof StoreResponeObject.State) {
            Log.d(TAG, "getItemViewType: TYPE_STATE " + position);
            return TYPE_STATE;
        } else {
            Log.d(TAG, "getItemViewType: TYPE_DISTRICT " + position);
            return TYPE_DISTRICT;
        }
    }


    class DistrictViewholder extends RecyclerView.ViewHolder {

        TextView tvStateName;

        DistrictViewholder(@NonNull View itemView) {
            super(itemView);
            tvStateName = itemView.findViewById(R.id.tvStateName);
        }
    }

    public void setMlistState(List<StoreResponeObject.State> mlistState) {
        this.mlistState = mlistState;
        mListItem.clear();
        if (mlistState != null) {
            for (StoreResponeObject.State state : mlistState) {
                mListItem.add(state);
                mListItem.addAll(state.districts);
            }
            Log.d(TAG, "getItemCount: " + mListItem.size());
        }
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    interface OnItemClickListener {
        void onClick(View v, Object item);
    }
}
