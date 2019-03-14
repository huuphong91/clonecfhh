package com.example.thecoffeehouse.order.drinks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.order.adapter.OrderProductAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DrinksFragment extends Fragment implements DrinksView {

    private RecyclerView mListProduct;
    private DrinksPresenter orderPresenter;
    private OrderProductAdapter mAdapter;

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
        setupMVP();
    }

    private void initView(View view) {
        mListProduct = view.findViewById(R.id.list_products);
        mListProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<DataItem> listProducts = new ArrayList<>();
        mAdapter = new OrderProductAdapter(getContext(), listProducts);
        mListProduct.setAdapter(mAdapter);
    }

    private void setupMVP() {
        orderPresenter = new DrinksPresenter(this);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayProduct(List<DataItem> orderResponse) {
        if (orderResponse != null) {
            mAdapter.setValues(orderResponse);
        }
    }

    @Override
    public void displayError(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void getProduct() {
        orderPresenter.getProduct();
    }

    @Override
    public void onResume() {
        super.onResume();
        getProduct();
    }
}
