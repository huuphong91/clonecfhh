package com.teamducati.cloneappcfh.screen.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.IAPIClient;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements OrderContract.View {

    private OrderContract.Presenter mPresenter;
    TextView responseText;
    IAPIClient apiClient;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = presenter;
    }


    /////////////////////////////////////////////////////////////////////////////////////////
}
