package com.example.thecoffeehouse.order.confirmcart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.dxloadingbutton.lib.LoadingButton;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.user.User;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.order.FormatPrice;
import com.example.thecoffeehouse.order.cart.CartInstance;
import com.example.thecoffeehouse.order.cart.database.CartViewModel;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class ConfirmDialogFragment extends DialogFragment implements IConfirmCartContract.View {

    private IConfirmCartContract.Presenter presenter;
    private TextView tvAddress, tvUserName, tvPhone, tvPrice, btnChange;
    private LoadingButton btnConfirm;
    private MapView mMapView;
    private int mPrice;
    private int mCupCount;
    private ImageView btnClose;
    private List<Cart> cartList;
    private CartViewModel cartViewModel;
    private OnUpdateListener mListener;
    private String mUsername, mPhonenumber;
    private double longitude, latitude;

    public static ConfirmDialogFragment newInstance(int price,int totalCup, String username, String phonenumber) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment ();
        Bundle bundle = new Bundle ();
        bundle.putInt (Constant.PRICE, price);
        bundle.putInt (Constant.CUP, totalCup);
        bundle.putString (Constant.USERNAME, username);
        bundle.putString (Constant.PHONENUMBER, phonenumber);
        fragment.setArguments (bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (getArguments () != null) {
            mPrice = (int) getArguments ().get (Constant.PRICE);
            mCupCount = (int) getArguments ().get (Constant.CUP);
            mUsername = getArguments ().getString (Constant.USERNAME);
            mPhonenumber = getArguments ().getString (Constant.PHONENUMBER);
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
        }
        cartList = CartInstance.getInstance ().getListCart ();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_dialog_confirm_cart, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewID (view);
        initViewEvent ();
        checkdataUser ();
        setupMapView (savedInstanceState);
    }

    private void initViewEvent() {
        btnClose.setOnClickListener (v -> {
            ConfirmDialogFragment.this.dismiss ();
        });
        btnConfirm.setOnClickListener (v -> {
            btnConfirm.startLoading();
            cartViewModel.delall ();
            presenter.confirmCart(mPhonenumber,mPrice,mCupCount,1);
        });
        btnChange.setOnClickListener (v -> ConfirmDialogFragment.this.dismiss ());
    }

    private void initViewID(View view) {
        cartViewModel = ViewModelProviders.of (this).get (CartViewModel.class);
        tvAddress = view.findViewById (R.id.tv_address);
        tvUserName = view.findViewById (R.id.tv_user_name);
        tvPhone = view.findViewById (R.id.tv_phone_number);
        tvPrice = view.findViewById (R.id.tv_total_confirm_cart);
        mMapView = view.findViewById (R.id.map_view_confirm);
        btnChange = view.findViewById (R.id.btn_change_confirm);
        btnClose = view.findViewById (R.id.img_view_close_confirm_cart);
        btnConfirm = view.findViewById (R.id.btn_confirm);
        tvPrice.setText (mPrice == 0 ? "0" : new FormatPrice().formatPrice (mPrice));
        presenter = new ConfirmCartPresenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mListener = null;
        mMapView.onDestroy ();
    }

    @Override
    public void onResume() {
        super.onResume ();
        mMapView.onResume ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        mMapView.onStart ();
    }

    @Override
    public void onStop() {
        super.onStop ();
        mMapView.onStop ();
    }

    private void checkdataUser() {
        Gson gson = new Gson ();
        SharedPreferences mSharedPrefs = getActivity ().getSharedPreferences ("dataUser", Context.MODE_PRIVATE);
        String json = mSharedPrefs.getString ("myObject", null);
        if (json != null) {
            User user = gson.fromJson (json, User.class);
            if (user != null) {
                tvUserName.setText (user.getFirstName () + " " + user.getLastName ());
                tvPhone.setText (user.getPhoneNumber ());
            }
        } else {
            tvUserName.setText (mUsername);
            tvPhone.setText (mPhonenumber);
        }
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constant.USER_LOCATION, Context.MODE_PRIVATE);
        String address = sharedPreferences.getString (Constant.LAST_KNOWN_LOCATION, "");
        longitude = sharedPreferences.getFloat (Constant.CURRENT_LONGITUDE, 106.6297f);
        latitude = sharedPreferences.getFloat (Constant.CURRENT_LATITUDE, 10.8231f);
        tvAddress.setText (address);
    }

    private void setupMapView(Bundle savedInstanceState) {
        mMapView.onCreate (savedInstanceState);
        mMapView.getMapAsync (googleMap -> {
            googleMap.getUiSettings ().setMapToolbarEnabled (false);
            googleMap.getUiSettings ().setMyLocationButtonEnabled (false);
            googleMap.getUiSettings ().setZoomControlsEnabled (false);
            MarkerOptions markerOptions = new MarkerOptions ();
            markerOptions.position (new LatLng (latitude, longitude));
            markerOptions.icon (BitmapDescriptorFactory.fromResource (R.drawable.ic_marker_map));
            googleMap.addMarker (markerOptions);
            googleMap.moveCamera (CameraUpdateFactory.newLatLngZoom (new LatLng (latitude, longitude), 14f));
        });
    }

    @Override
    public void onConfirmSucess(String result) {
        btnConfirm.loadingSuccessful();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { ConfirmDialogFragment.this.dismiss ();
            mListener.onUpdateFragment ();
            }
        }, 2000);
    }

    @Override
    public void onConfirmFaild(String result) {
        btnConfirm.loadingFailed();
        btnConfirm.reset();
    }
}
