package com.teamducati.cloneappcfh.screen.order.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.APIStoreMap.Address;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RepickShipAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int POSITION_ADDRESS_TYPE = 1;
    private static final int SHIP_ADDRESS_RESULT_TYPE = 2;

    private List<Address> mAddressList;

    private OnClickListener mListener;

    public RepickShipAddressAdapter(OnClickListener listener) {
        this.mAddressList = new ArrayList<>();
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {
            case POSITION_ADDRESS_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_position_ship_item, parent, false);
                return new PositionAddressViewHolder(view);
            case SHIP_ADDRESS_RESULT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custem_ship_address_result_search_item, parent, false);
                return new ShipAddressResultViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_position_ship_item, parent, false);
                return new PositionAddressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case POSITION_ADDRESS_TYPE:
                ((PositionAddressViewHolder) holder).bindPositionAddress(position);
                break;
            case SHIP_ADDRESS_RESULT_TYPE:
                ((ShipAddressResultViewHolder) holder).tvShipAddressResult.setText(mAddressList.get(position).getFullAddress());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mAddressList.get(position).getPositionShipTitle() != null) {
            return POSITION_ADDRESS_TYPE;
        } else {
            return SHIP_ADDRESS_RESULT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public void displayPositionShipTitle(String currentAddress) {
        mAddressList.clear();
        for (int i = 0; i < Constants.positionShipTitle.length; i++) {
            Address address = new Address();
            if (i == 0) {
                address.setPositionShipTitle(Constants.positionShipTitle[0]);
                address.setImgUrl(Constants.imgPositionShipUrl[0]);
                address.setFullAddress(currentAddress);
            } else {
                address.setPositionShipTitle(Constants.positionShipTitle[i]);
                address.setImgUrl(Constants.imgPositionShipUrl[i]);
            }

            mAddressList.add(address);
        }
        notifyDataSetChanged();
    }

    public void displayShipAddressResults(List<String> results) {
        mAddressList.clear();
        for (int i = 0; i < results.size(); i++) {
            Address address = new Address();
            address.setFullAddress(results.get(i));
            mAddressList.add(address);
        }
        notifyDataSetChanged();
    }

    class PositionAddressViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPositionTitle;
        private TextView tvPositionAddress;
        private ImageView imgPositionShip;

        PositionAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPositionAddress = itemView.findViewById(R.id.tvPositionAddress);
            tvPositionTitle = itemView.findViewById(R.id.tvPositionTitle);
            imgPositionShip = itemView.findViewById(R.id.imgPositionShip);
            itemView.setOnClickListener(v -> {
                mListener.onCurrentLocationClick();
            });
        }

        void bindPositionAddress(int position) {
            tvPositionTitle.setText(mAddressList
                    .get(position)
                    .getPositionShipTitle());
            tvPositionAddress.setText(mAddressList
                    .get(position)
                    .getFullAddress());
            Uri path = Uri.parse("android.resource://com.teamducati.cloneappcfh/drawable/" + mAddressList.get(position).getImgUrl());
            Glide.with(itemView.getContext())
                    .load(path)
                    .into(imgPositionShip);
        }
    }

    class ShipAddressResultViewHolder extends RecyclerView.ViewHolder {

        private TextView tvShipAddressResult;

        ShipAddressResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShipAddressResult = itemView.findViewById(R.id.tvShipAddressResult);
            itemView.setOnClickListener(v -> {
                mListener.onPickLocationClick(mAddressList.get(getAdapterPosition()).getFullAddress());
            });
        }
    }

    public interface OnClickListener {
        void onCurrentLocationClick();

        void onRecentLocationCLick();

        void onPickLocationClick(String address);
    }
}
