package com.example.thecoffeehouse.order.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DetailDialogFragment extends DialogFragment {

    private static DetailDialogFragment fragment;

    public static DetailDialogFragment newInstance(DataItem dataItem) {
        DetailDialogFragment fragment = new DetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA_ITEM, dataItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
    }

}
