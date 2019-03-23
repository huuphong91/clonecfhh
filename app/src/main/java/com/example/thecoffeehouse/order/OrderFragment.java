package com.example.thecoffeehouse.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.order.cart.CartFragment;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.example.thecoffeehouse.order.drinks.DrinksFragment;
import com.example.thecoffeehouse.order.filter.FilterDialogFragment;
import com.example.thecoffeehouse.order.hightlight.HighLightDrinks;
import com.example.thecoffeehouse.order.search.SearchDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class OrderFragment extends Fragment implements OrderView {

    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private DrinksFragment drinksFragment;
    private HighLightDrinks highLightDrinks;
    private ImageView imgViewSearch;
    private ConstraintLayout constraintLayout;
    private List<Cart> mValues = new ArrayList<> ();
    private OrderPresenterImp orderPresenter;
    private TextView txtCartSize, txtTotal;
    private FloatingActionButton fabFilter;
    private FormatPrice formatPrice = new FormatPrice ();
    private FragmentInteractionListener mListener;

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment ();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_order, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initView (view);
        initEvent ();
        addTab (drinksFragment);
    }

    private void initData() {
        orderPresenter.getCartItem (this);
    }

    private void initEvent() {
        imgViewSearch.setOnClickListener (v ->
                SearchDialogFragment.newInstance ().show (mFragmentManager, "data"));

        constraintLayout.setOnClickListener (v ->
        {
//            CartFragment.newInstance ().show (mFragmentManager, Constant.CART_FRAGMENT);
            mListener.onChangeFragment (CartFragment.newInstance (), Constant.CART_FRAGMENT);
        });
        fabFilter.setOnClickListener (v -> {
//            FilterDialogFragment.newInstance ().show (mFragmentManager, "as");
        });
    }

    private void initView(View view) {
        orderPresenter = new OrderPresenterImp (getActivity ().getApplication (), this);
        mFragmentManager = getFragmentManager ();
        mTabLayout = view.findViewById (R.id.tabLayout);
        imgViewSearch = view.findViewById (R.id.order_action_search);
        constraintLayout = view.findViewById (R.id.layout_display_cart);
        mTabLayout.addTab (mTabLayout.newTab ().setText ("Món nổi bật"));
        mTabLayout.addTab (mTabLayout.newTab ().setText ("Thức uống"));
        mTabLayout.setOnTabSelectedListener (onTabSelectedListener);
        highLightDrinks = HighLightDrinks.newInstance ();
        drinksFragment = DrinksFragment.newInstance ();
        txtCartSize = view.findViewById (R.id.tv_cart_size);
        txtTotal = view.findViewById (R.id.tv_cart_total);
        fabFilter = view.findViewById (R.id.fab_filter);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener () {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition ();
            switch (position) {
                case 0:
                    addTab (drinksFragment);
                    break;
                case 1:
                    addTab (highLightDrinks);
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
        mFragmentManager.beginTransaction ()
                .replace (R.id.order_frame_layout, fragment)
                .addToBackStack (null)
                .commit ();
    }

    @Override
    public void setCartLayout(List<Cart> carts) {
        if (!carts.isEmpty ()) {
            int cartSize = 0;
            long total = 0;
            mValues.addAll (carts);
            constraintLayout.setVisibility (View.VISIBLE);
            for (Cart cart : carts) {
                total += cart.getPrice () * cart.getCount ();
                cartSize += cart.getCount ();
            }
            txtTotal.setText (formatPrice.formatPrice ((int) total));
            txtCartSize.setText (String.valueOf (cartSize));
        } else {
            constraintLayout.setVisibility (View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume ();
        initData ();
    }
}
