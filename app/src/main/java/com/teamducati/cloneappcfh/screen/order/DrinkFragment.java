package com.teamducati.cloneappcfh.screen.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;
import com.teamducati.cloneappcfh.screen.order.adapter.OrderAdapter;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrinkFragment extends Fragment {
    private OrderAdapter fragmentOrderAdapter;
    private RecyclerView rvListDrinks;
    private ItemProductResponse productResponse;
    private List<DataItem> dataItems;

    public DrinkFragment() {
        Log.d(OrderFragment.TAG, "DrinkFragment: ");
    }

    public static DrinkFragment newInstance(ItemProductResponse itemProductResponse) {
        DrinkFragment fragment = new DrinkFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BUNDLE_DRINK_FRAGMENT, itemProductResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(OrderFragment.TAG, "onCreate: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(OrderFragment.TAG, "onViewCreated: ");

        rvListDrinks = view.findViewById(R.id.rv_drink);
        getDataItems(9);
        setAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(OrderFragment.TAG, "onCreateView: ");

        return inflater.inflate(R.layout.fragment_drink, container, false);

    }

    private void setAdapter() {
        Log.d(OrderFragment.TAG, "setAdapter: ");

        if (fragmentOrderAdapter == null) {
            rvListDrinks.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
            fragmentOrderAdapter = new OrderAdapter(getActivity(), dataItems, getFragmentManager());
            rvListDrinks.setAdapter(fragmentOrderAdapter);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(OrderFragment.TAG, "onAttach: ");
        if (getArguments() != null) {
            productResponse = getArguments().getParcelable(Constants.KEY_BUNDLE_DRINK_FRAGMENT);
        }
    }

    private void getDataItems(int categ) {
        Log.d(OrderFragment.TAG, "getDataItems: ");

        dataItems = new ArrayList<>();
        for (DataItem item : productResponse.getData()) {
            if (item.getCategId().get(0) != categ) {
                dataItems.add(item);
            }
        }
    }
}
