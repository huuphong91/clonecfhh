package com.teamducati.cloneappcfh.screen.order.ordersearch;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;
import com.teamducati.cloneappcfh.screen.order.OrderFragment;
import com.teamducati.cloneappcfh.screen.order.adapter.OrderAdapter;
import com.teamducati.cloneappcfh.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.SEARCH_SERVICE;

public class OrderSearchDialogFragment extends DialogFragment {
    private View view;
    private Dialog dialog;
    private OrderAdapter fragmentOrderAdapter;
    private ItemProductResponse productResponse;
    private List<DataItem> dataItems;
    private RecyclerView mRvDrink;
    private ImageView mImgBackWebView;
    private SearchView mSearchViewOrderSearch;
    private ImageView mImgBackSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_search, container, false);
        initData();
        initMappingViewId();
        initRecyclerViewOrder();
        initEvent();
        initSearchView();
        return view;
    }

    private void initEvent() {
        mImgBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initSearchView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SearchManager manager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
            SearchableInfo searchableInfo = manager.getSearchableInfo(getActivity().getComponentName());
            mSearchViewOrderSearch.setSearchableInfo(searchableInfo);
        }
        mSearchViewOrderSearch.setFocusable(true);
        mSearchViewOrderSearch.setIconified(false);
        mSearchViewOrderSearch.requestFocusFromTouch();
        mSearchViewOrderSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) {
                    mRvDrink.setVisibility(View.GONE);
                } else {
                    mRvDrink.setVisibility(View.VISIBLE);
                    fragmentOrderAdapter.getFilter().filter(s);
                }

                return false;
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    private void initMappingViewId() {
        mRvDrink = view.findViewById(R.id.rv_order_search);
        mImgBackWebView = view.findViewById(R.id.img_back_web_view);
        mSearchViewOrderSearch = view.findViewById(R.id.search_view_order_search);
        mImgBackSearch = view.findViewById(R.id.img_back_search);
    }

    private void initRecyclerViewOrder() {
        if (fragmentOrderAdapter == null) {
            //hide RecyclerView when search
            mRvDrink.setVisibility(View.GONE);
            mRvDrink.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
            fragmentOrderAdapter = new OrderAdapter(getActivity(), dataItems, getFragmentManager());
            mRvDrink.setAdapter(fragmentOrderAdapter);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.d(OrderFragment.TAG, "onAttach: ");
        if (getArguments() != null) {
            productResponse = getArguments().getParcelable(Constants.KEY_BUNDLE_SEARCH_FRAGMENT);
        }
    }

    private void initData() {
        dataItems = productResponse.getData();

    }

    public static OrderSearchDialogFragment newInstance(ItemProductResponse itemProductResponse) {
        OrderSearchDialogFragment fragment = new OrderSearchDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BUNDLE_SEARCH_FRAGMENT, itemProductResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


}