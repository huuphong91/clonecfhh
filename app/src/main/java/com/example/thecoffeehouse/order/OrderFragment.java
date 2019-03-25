package com.example.thecoffeehouse.order;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoffeehouse.CheckNetworkState;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.FragmentInteractionListener;
import com.example.thecoffeehouse.order.cart.CartFragment;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.example.thecoffeehouse.order.drinks.DrinksFragment;
import com.example.thecoffeehouse.order.filter.FabAnimation;
import com.example.thecoffeehouse.order.filter.FilterDialogFragment;
import com.example.thecoffeehouse.order.hightlight.HighLightDrinks;
import com.example.thecoffeehouse.order.search.SearchDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class OrderFragment extends Fragment implements OrderView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, FabAnimation {

    private TabLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private DrinksFragment drinksFragment;
    private HighLightDrinks highLightDrinks;
    private ImageView imgViewSearch;
    private ConstraintLayout constraintLayout;
    private List<Cart> mValues = new ArrayList<> ();
    private OrderPresenterImp orderPresenter;
    private TextView txtCartSize, txtTotal, txtAddress;
    private FloatingActionButton fabFilter;
    private FormatPrice formatPrice = new FormatPrice ();
    private FragmentInteractionListener mListener;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private FilterDialogFragment filterDialogFragment;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

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
            mListener.onChangeFragment (CartFragment.newInstance (), Constant.CART_FRAGMENT);
        });
        fabFilter.setOnClickListener (v -> {
            filterDialogFragment = FilterDialogFragment.newInstance (constraintLayout.getVisibility () == View.GONE ? true : false);
            filterDialogFragment.setFabAnimation (this);
            filterDialogFragment.show (mFragmentManager, "filter");
        });
    }

    private void initView(View view) {
        orderPresenter = new OrderPresenterImp (getActivity ().getApplication (), this);
        mFragmentManager = getChildFragmentManager ();
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
        txtAddress = view.findViewById (R.id.order_text_address);

        fab_open = AnimationUtils.loadAnimation (getContext (), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation (getContext (), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation (getContext (), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation (getContext (), R.anim.rotate_backward);


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
    public void onLocationAddressUpdate(Address address) {
        txtAddress.setText (address.getAddressLine (0));
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constant.USER_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString (Constant.LAST_KNOWN_LOCATION, address.getAddressLine (0));
        editor.putFloat (Constant.CURRENT_LATITUDE, (float) address.getLatitude ());
        editor.putFloat (Constant.CURRENT_LONGITUDE, (float) address.getLongitude ());
        editor.commit ();
    }

    @Override
    public void onResume() {
        super.onResume ();
        if (CheckNetworkState.getInstance (getActivity ().getApplication ()).checkNetwork ()) {
            initData ();
            getLocation ();
        } else {
            Toast.makeText (getContext (), "Không có kết nối internet" , Toast.LENGTH_SHORT).show ();
        }

    }

    private void getLocation() {
        locationRequest = LocationRequest.create ();
        locationRequest.setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval (Constant.LOCATION_INTERVAL);
        locationRequest.setFastestInterval (Constant.LOCATION_INTERVAL);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder (getContext ())
                .addApi (LocationServices.API)
                .addConnectionCallbacks (this)
                .addOnConnectionFailedListener (this)
                .build ();
        if (googleApiClient != null) {
            googleApiClient.connect ();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getActivity () != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity ().checkSelfPermission (Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions (Objects.requireNonNull (getActivity ()), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 696);
                    return;
                }
            }
        }
        fusedLocationProviderApi.requestLocationUpdates (googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            orderPresenter.getLocationAddress (location);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == 696) {
            if (permissions[0].equals (Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            if (ContextCompat.checkSelfPermission (getContext (), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation ();
            }
        }
    }

    @Override
    public void onOpen() {
        fabFilter.setImageDrawable (getContext ().getResources ().getDrawable (R.drawable.ic_action_fab_24dp));
        fabFilter.startAnimation (rotate_forward);
    }

    @Override
    public void onClose() {
        fabFilter.startAnimation (rotate_backward);
        fabFilter.setImageDrawable (getContext ().getResources ().getDrawable (R.drawable.ic_action_filter));
    }
}
