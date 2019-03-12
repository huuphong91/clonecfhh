package com.example.thecoffeehouse.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrinksFragment extends Fragment {

    private RecyclerView mListProduct;

    public static DrinksFragment newInstance() {
        DrinksFragment fragment = new DrinksFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_product, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mListProduct = view.findViewById(R.id.list_products);
        mListProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<OrderProduct> listProducts = new ArrayList<>();
        listProducts.add(new OrderProduct("1"));
        listProducts.add(new OrderProduct("2"));
        listProducts.add(new OrderProduct("2"));
        listProducts.add(new OrderProduct("2"));
        OrderProductAdapter mAdapter = new OrderProductAdapter(getContext(), listProducts);
        mListProduct.setAdapter(mAdapter);
    }
}
