package com.example.thecoffeehouse.order.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.user.User;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.order.confirmcart.ConfirmDialogFragment;
import com.example.thecoffeehouse.order.FormatPrice;
import com.example.thecoffeehouse.order.cart.adpater.CartAdapter;
import com.example.thecoffeehouse.order.cart.adpater.OnOrderListCartListener;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment implements CartFragmentView {
    private RecyclerView recyclerView;
    private MapView mMapView;
    private ImageView imgViewClose;
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private TextView btnChange;
    private TextView tvAddress;
    private EditText edtNoteForDriver;
    private EditText edtNoteForShop;
    private TextView tvPrice;
    private TextView tvTotal, txtCartTotal, txtButtonCartTotal;
    private List<Cart> cartList = new ArrayList<> ();
    private CartAdapter mCartAdapter;
    private OnOrderListCartListener mListener;
    private CartPresenterImp cartPresenter;
    private ConstraintLayout btnOrder;
    private long total = 0;
    private OnUpdateListener onUpdateListener;
    private FormatPrice formatPrice = new FormatPrice ();
    private SharedPreferences mSharedPrefs;
    private double latitude, longitude;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment ();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (context instanceof OnOrderListCartListener) {
            mListener = (OnOrderListCartListener) context;
        }
        if (context instanceof OnUpdateListener) {
            onUpdateListener = (OnUpdateListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_cart, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
//        setStyle (DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewID (view);
        initEvent ();
        checkdataUser ();
        setupMapView (savedInstanceState);

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

    private void initData() {
        cartPresenter.getCart (this);
    }

    private void initEvent() {
        imgViewClose.setOnClickListener (v -> {
            onUpdateListener.onUpdateFragment ();
        });
        btnOrder.setOnClickListener (v -> {
            if (!"".equals (edtUsername.getText ().toString ()) && !"".equals (edtPhoneNumber.getText ().toString ())) {
                List<Cart> listCart  = CartInstance.getInstance().getListCart();
                int listCount = 0;
                int listSize = listCart.size();
                for (int i = 0; i < listSize; i++) {
                    listCount = listCount+listCart.get(i).getCount();
                }
                ConfirmDialogFragment.newInstance ((int) total, listCount, edtUsername.getText ().toString (), edtPhoneNumber.getText ().toString ()).show (getFragmentManager (), "");
            } else {
                Toast.makeText (getContext (), "Điền thông tin giao hàng!", Toast.LENGTH_SHORT).show ();
            }
        });
    }

    private void initViewID(View view) {
        cartPresenter = new CartPresenterImp (getActivity ().getApplication (), this);
        imgViewClose = view.findViewById (R.id.img_view_close);
        mMapView = view.findViewById (R.id.img_view_map_cart);
        edtUsername = view.findViewById (R.id.edt_user_name);
        edtPhoneNumber = view.findViewById (R.id.edt_phone);
        btnChange = view.findViewById (R.id.btn_change);
        tvAddress = view.findViewById (R.id.tv_address);
        edtNoteForDriver = view.findViewById (R.id.edt_note_for_driver);
        edtNoteForShop = view.findViewById (R.id.edt_note_for_shop);
        tvPrice = view.findViewById (R.id.tv_price_cart);
        tvTotal = view.findViewById (R.id.tv_total_cart);
        txtCartTotal = view.findViewById (R.id.tv_total_book_cart);
        txtButtonCartTotal = view.findViewById (R.id.tv_btn_total);
        recyclerView = view.findViewById (R.id.recycler_view_product_cart);
        cartList = new ArrayList<> ();
        recyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));
        mCartAdapter = new CartAdapter (getContext (), cartList);
        recyclerView.setAdapter (mCartAdapter);
        mCartAdapter.setListener (mListener);
        btnOrder = view.findViewById (R.id.btn_book);
        mSharedPrefs = getContext ().getSharedPreferences ("dataUser", Context.MODE_PRIVATE);
    }

    @Override
    public void setData(List<Cart> carts) {
        if (!carts.isEmpty ()) {
            int cartSize = 0;
            total = 0;
            mCartAdapter.setValues (carts);
            for (Cart cart : carts) {
                total += cart.getPrice () * cart.getCount ();
                cartSize += cart.getCount ();
            }
            tvTotal.setText (formatPrice.formatPrice ((int) total));
            tvPrice.setText (formatPrice.formatPrice ((int) total));
            txtCartTotal.setText (formatPrice.formatPrice ((int) total));
            txtButtonCartTotal.setText (formatPrice.formatPrice ((int) total));
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume ();
        initData ();
        mMapView.onResume ();
    }

    private void checkdataUser() {
        Gson gson = new Gson ();
        String json = mSharedPrefs.getString ("myObject", null);
        if (json != null) {
            User user = gson.fromJson (json, User.class);
            if (user != null) {
                edtUsername.setText (user.getFirstName () + " " + user.getLastName ());
                edtPhoneNumber.setText (user.getPhoneNumber ());
            }
        }
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constant.USER_LOCATION, Context.MODE_PRIVATE);
        String address = sharedPreferences.getString (Constant.LAST_KNOWN_LOCATION, "");
        longitude = sharedPreferences.getFloat (Constant.CURRENT_LONGITUDE, 106.6297f);
        latitude = sharedPreferences.getFloat (Constant.CURRENT_LATITUDE, 10.8231f);
        tvAddress.setText (address);
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

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mMapView.onDestroy ();
    }
}
