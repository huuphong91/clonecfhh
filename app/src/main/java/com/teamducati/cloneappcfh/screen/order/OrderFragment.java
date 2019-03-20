package com.teamducati.cloneappcfh.screen.order;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.entity.api_order.ItemProductResponse;
import com.teamducati.cloneappcfh.screen.order.ShipAddressRepick.ShipAddressRepick;
import com.teamducati.cloneappcfh.screen.order.ordersearch.OrderSearchDialogFragment;
import com.teamducati.cloneappcfh.utils.Constants;
import com.teamducati.cloneappcfh.utils.Utils;
import com.teamducati.cloneappcfh.utils.eventsbus.EventsBusCart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
public class OrderFragment extends Fragment implements OrderContract.View, ShipAddressRepick.OnClickItem {

    public static final String TAG = OrderFragment.class.getName();
    private static int numberProductInCartCurrent;
    private static int priceProductInCartCurrent;
    @BindView(R.id.tvShipAddress)
    TextView tvShipAddress;
    @BindView(R.id.toolBarShipLocation)
    ConstraintLayout mToolBarShipLocation;
    @BindView(R.id.tabs111)
    TabLayout tabLayout;
    @BindView(R.id.viewpager111)
    ViewPager viewPager;
    @BindView(R.id.csl_cart)
    ConstraintLayout constraintLayoutCart;
    @BindView(R.id.tv_number_product_in_cart)
    TextView tvNumberProduct;
    @BindView(R.id.tv_price_in_cart)
    TextView tvPrice;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imgSearchProduct)
    ImageView imgSearchProduct;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Unbinder unbinder;
    private OrderContract.Presenter mPresenter;
    private ItemProductResponse itemProductResponse;
    private SharedPreferences sp;
    private DialogFragment dialogFragment = ShipAddressRepick.newInstance();

    public OrderFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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

        ((ShipAddressRepick) dialogFragment).setOnClickItem(this);

        mToolBarShipLocation.setOnClickListener(v -> {
            dialogFragment.show(getChildFragmentManager(), "tag");
        });
        imgSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSearchDialogFragment.newInstance(itemProductResponse).show(getFragmentManager(), "");
            }
        });

        numberProductInCartCurrent = -1;
        priceProductInCartCurrent = -1;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(HighlightFoodFragment.newInstance(itemProductResponse), "Món nổi bật");
        adapter.addFragment(DrinkFragment.newInstance(itemProductResponse), "Thức uống");
        viewPager.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventsBusCart event) {
        constraintLayoutCart.setVisibility(View.VISIBLE);
        tvNumberProduct.setText(String.valueOf(event.numberProduct));
        tvPrice.setText(Utils.formatMoney(event.price * event.numberProduct));


        //        sp = Utils.getSharedPreferences(this.getActivity(), sp);
//        SharedPreferences.Editor editor = sp.edit();
//        Gson gson = new Gson();
//        String productGson = new Gson().toString();
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


    public void setLocation(String address) {
        tvShipAddress.setText(address);
        ((ShipAddressRepick) dialogFragment).setLocation(address);
    }

    public void writeSharedPreferences(String objectGson) {
        SharedPreferences sharedPreferences = Utils.getSharedPreferencesInstance(this.getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_PRODUCT_SHARED_PREF, objectGson);
    }

    public void readSharedPreferences() {
        SharedPreferences sharedPreferences = Utils.getSharedPreferencesInstance(this.getActivity());
        String productPref = sharedPreferences.getString(Constants.KEY_PRODUCT_SHARED_PREF, "");
        if (!"".equals(productPref)) {
            DataItem dataItem = Utils.getGsonInstance().fromJson(productPref, DataItem.class);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
}