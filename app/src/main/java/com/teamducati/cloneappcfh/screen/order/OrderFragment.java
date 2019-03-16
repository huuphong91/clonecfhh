package com.teamducati.cloneappcfh.screen.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.tabs.TabLayout;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
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
public class OrderFragment extends Fragment implements OrderContract.View, RepickShipAddressDialog.OnClickItem {

    public static final String TAG = OrderFragment.class.getName();

    @BindView(R.id.tvShipAddress)
    TextView tvShipAddress;
    @BindView(R.id.toolBarShipLocation)
    ConstraintLayout mToolBarShipLocation;
    @BindView(R.id.tabs111)
    TabLayout tabLayout;
    @BindView(R.id.viewpager111)
    ViewPager viewPager;

    private Unbinder unbinder;
    private OrderContract.Presenter mPresenter;
    private ItemProductResponse itemProductResponse;

    private DialogFragment dialogFragment = RepickShipAddressDialog.newInstance();

    public OrderFragment() {

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

        tabLayout.setupWithViewPager(viewPager);

        mPresenter.onGetAllProductPresenter();

        ((RepickShipAddressDialog)dialogFragment).setOnClickItem(this);

        mToolBarShipLocation.setOnClickListener(v -> {
            dialogFragment.show(getChildFragmentManager(), "tag");
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(HighlightFoodFragment.newInstance(itemProductResponse), "Món nổi bật");
        adapter.addFragment(DrinkFragment.newInstance(itemProductResponse), "Thức uống");
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

    @Override
    public void onClickItem(String address) {
        tvShipAddress.setText(address);
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
        ((RepickShipAddressDialog)dialogFragment).setLocation(address);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}