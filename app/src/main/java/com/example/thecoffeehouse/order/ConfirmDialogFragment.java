package com.example.thecoffeehouse.order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.order.cart.CartInstance;
import com.example.thecoffeehouse.order.cart.database.CartViewModel;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.google.android.gms.maps.MapView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class ConfirmDialogFragment extends DialogFragment {

    private TextView tvAddress;
    private TextView tvUserName;
    private TextView tvPhone;
    private TextView tvPrice;
    private MapView mapView;
    private TextView btnChange;
    private TextView btnConfirm;
    private int price;
    private ImageView btnClose;
    private List<Cart> cartList;
    private CartViewModel cartViewModel;
    private OnUpdateListener mListener;

    public static ConfirmDialogFragment newInstance(int price) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment ();
        Bundle bundle = new Bundle ();
        bundle.putInt ("data", price);
        fragment.setArguments (bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (getArguments () != null) {
            price = (int) getArguments ().get ("data");
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
    }

    private void initViewEvent() {
        btnClose.setOnClickListener (v -> {
            ConfirmDialogFragment.this.dismiss ();
        });
        btnConfirm.setOnClickListener (v -> {
            cartViewModel.delall ();
            ConfirmDialogFragment.this.dismiss ();
            mListener.onUpdateFragment ();
        });
        btnChange.setOnClickListener (v -> ConfirmDialogFragment.this.dismiss ());
    }

    private void initViewID(View view) {
        cartViewModel = ViewModelProviders.of (this).get (CartViewModel.class);
        tvAddress = view.findViewById (R.id.tv_address);
        tvUserName = view.findViewById (R.id.tv_user_name);
        tvPhone = view.findViewById (R.id.tv_phone_number);
        tvPrice = view.findViewById (R.id.tv_total_confirm_cart);
        mapView = view.findViewById (R.id.map_view_confirm);
        btnChange = view.findViewById (R.id.btn_change_confirm);
        btnClose = view.findViewById (R.id.img_view_close_confirm_cart);
        btnConfirm = view.findViewById (R.id.btn_confirm);
        tvPrice.setText (price == 0 ? "0" : new FormatPrice ().formatPrice (price));
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mListener = null;
    }
}
