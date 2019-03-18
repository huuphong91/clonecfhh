package com.example.thecoffeehouse.order.search;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.order.adapter.OrderProductAdapter;
import com.example.thecoffeehouse.order.drinks.DrinksPresenter;
import com.example.thecoffeehouse.order.drinks.DrinksView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchDialogFragment extends DialogFragment implements DrinksView {

    private EditText edtSearch;
    private OrderProductAdapter mAdapter;
    private RecyclerView mList;
    private DrinksPresenter searchPresenter;

    public static SearchDialogFragment newInstance() {
        SearchDialogFragment fragment = new SearchDialogFragment ();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewId (view);
        initData ();
        initEvent ();
    }

    private void initData() {
        searchPresenter.getProduct ();
    }

    private void initEvent() {
        edtSearch.addTextChangedListener (new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter ().filter (s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewId(View view) {
        searchPresenter = new DrinksPresenter (SearchDialogFragment.this);
        edtSearch = view.findViewById (R.id.edt_search);
        mList = view.findViewById (R.id.list_search);
        mList.setLayoutManager (new GridLayoutManager (getContext (), 2));
        List<DataItem> itemList = new ArrayList<> ();
        mAdapter = new OrderProductAdapter (getContext (), itemList);
        mList.setAdapter (mAdapter);
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void displayProduct(List<DataItem> orderResponse) {
        mAdapter.setValues (orderResponse);
    }

    @Override
    public void displayError(String s) {

    }
}
