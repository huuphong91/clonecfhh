package com.teamducati.cloneappcfh.screen.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamducati.cloneappcfh.R;
import com.teamok.cloneappcfh.adapter.FragmentOrderAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeaturedFoodFragment extends Fragment {

    private FragmentOrderAdapter fragmentOrderAdapter;
    private RecyclerView rvListFoods;
    private ArrayList<Product> productArrayList;

    public FeaturedFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvListFoods = view.findViewById(R.id.rv_featured_food);
        createList();
        setAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_featured_food, container, false);
    }

    private void setAdapter() {
        if (fragmentOrderAdapter == null) {
            rvListFoods.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            fragmentOrderAdapter = new FragmentOrderAdapter(getActivity(), productArrayList);
            rvListFoods.setAdapter(fragmentOrderAdapter);
        }
    }

    private void createList() {
        productArrayList = new ArrayList<>();
//        productArrayList.add(new Product("1","HAHA",50,"Ngu nguoi",1,));
    }
}
