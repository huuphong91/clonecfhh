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

public class HighlightFoodFragment extends Fragment {

    private OrderAdapter fragmentOrderAdapter;
    private RecyclerView rvListFoods;
    private ItemProductResponse productResponse;
    private List<DataItem> dataItems;

    public HighlightFoodFragment() {

    }

    public static HighlightFoodFragment newInstance(ItemProductResponse itemProductResponse) {
        Log.d(OrderFragment.TAG, "newInstance: " + HighlightFoodFragment.class.getSimpleName());

        HighlightFoodFragment fragment = new HighlightFoodFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BUNDLE_FOOD_FRAGMENT, itemProductResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(OrderFragment.TAG, "onAttach: " + HighlightFoodFragment.class.getSimpleName());

        if (getArguments() != null) {
            productResponse = getArguments().getParcelable(Constants.KEY_BUNDLE_FOOD_FRAGMENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(OrderFragment.TAG, "onCreate: " + HighlightFoodFragment.class.getSimpleName());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(OrderFragment.TAG, "onViewCreated: " + HighlightFoodFragment.class.getSimpleName());

        rvListFoods = view.findViewById(R.id.rv_highlight_food);
        getDataItems(9);
        setAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(OrderFragment.TAG, "onCreateView: " + HighlightFoodFragment.class.getSimpleName());

        return inflater.inflate(R.layout.fragment_highlight_food, container, false);
    }

    private void setAdapter() {
        Log.d(OrderFragment.TAG, "setAdapter: " + HighlightFoodFragment.class.getSimpleName());

        if (fragmentOrderAdapter == null) {
            rvListFoods.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
            fragmentOrderAdapter = new OrderAdapter(getActivity(), dataItems, getFragmentManager());
            rvListFoods.setAdapter(fragmentOrderAdapter);
        }
    }

    private void getDataItems(int categ) {
        Log.d(OrderFragment.TAG, "getDataItems: " + HighlightFoodFragment.class.getSimpleName());
        if (productResponse != null) {
            dataItems = new ArrayList<>();
            for (DataItem item : productResponse.getData()) {
                if (item.getCategId().get(0) == categ) {
                    dataItems.add(item);
                }
            }
        }
    }
}
