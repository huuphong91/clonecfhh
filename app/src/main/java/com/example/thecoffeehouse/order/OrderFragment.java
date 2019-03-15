package com.example.thecoffeehouse.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.order.drinks.DrinksFragment;
import com.example.thecoffeehouse.order.FoodFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class OrderFragment extends Fragment {

    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        addTab(DrinksFragment.newInstance());
    }

    private void initView(View view) {
        mFragmentManager = getFragmentManager();
        mTabLayout = view.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Drinks"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Food"));
        mTabLayout.setOnTabSelectedListener(onTabSelectedListener);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            switch (position) {
                case 0:
                    addTab(DrinksFragment.newInstance());
                    break;
                case 1:
                    addTab(FoodFragment.newInstance());
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void addTab(Fragment fragment) {

        mFragmentManager.beginTransaction()
                .replace(R.id.order_frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

}
