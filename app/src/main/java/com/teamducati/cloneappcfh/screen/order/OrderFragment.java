package com.teamducati.cloneappcfh.screen.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements OrderContract.View {

    public static final String TAG = OrderFragment.class.getName();

    @BindView(R.id.toolBarShipLocation)
    ConstraintLayout mToolBarShipLocation;
    @BindView(R.id.tvShipAddress)
    TextView tvShipAddress;

    private Unbinder unbinder;

    private OrderContract.Presenter mPresenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ItemProductResponse itemProductResponse;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidgets(view);
    }

    private void getWidgets(View view) {
        viewPager = view.findViewById(R.id.viewpager111);
//        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs111);
        tabLayout.setupWithViewPager(viewPager);

        mPresenter.onGetAllProductPresenter();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(HighlightFoodFragment.newInstance(itemProductResponse), "Featured Food");
        adapter.addFragment(DrinkFragment.newInstance(itemProductResponse), "Drinks");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGetAllProductView(ItemProductResponse itemListProductResponse) {
        this.itemProductResponse = itemListProductResponse;
        setupViewPager(viewPager);
    }

    @Override
    public void displayError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void setLocation(String address) {
        tvShipAddress.setText(address);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
