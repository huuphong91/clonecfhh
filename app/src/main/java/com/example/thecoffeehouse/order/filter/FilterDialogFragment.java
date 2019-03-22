package com.example.thecoffeehouse.order.filter;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.AppRepository;
import com.example.thecoffeehouse.data.model.product.Category;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FilterDialogFragment extends DialogFragment implements FilterView {

    private RecyclerView mRecyclerFilter;
    private FilterAdapter mAdapter;
    private FilterPresenter filterPresenter;

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_fragment_style);
        window.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        int y = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, metrics);
        int x = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);
        params.height = y;
        params.width = x;
        window.setAttributes(params);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mRecyclerFilter = view.findViewById(R.id.recycler_filter);
        mRecyclerFilter.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Category> list = new ArrayList<>();
        mAdapter = new FilterAdapter(getContext(), list);
        mRecyclerFilter.setAdapter(mAdapter);
        filterPresenter = new FilterPresenterImp(getActivity().getApplication(), this);
        filterPresenter.getCategory();
    }

    @Override
    public void getCategory(List<Category> categoryList) {
        mAdapter.setValues(categoryList);
    }
}
