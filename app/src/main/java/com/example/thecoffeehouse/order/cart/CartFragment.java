package com.example.thecoffeehouse.order.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.order.ConfirmDialogFragment;
import com.example.thecoffeehouse.order.FormatPrice;
import com.example.thecoffeehouse.order.cart.adpater.CartAdapter;
import com.example.thecoffeehouse.order.cart.adpater.OnOrderListCartListener;
import com.example.thecoffeehouse.order.cart.model.Cart;
import com.google.android.gms.maps.MapView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends DialogFragment implements CartFragmentView {
    private RecyclerView recyclerView;
    private MapView mapView;
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
    private FormatPrice formatPrice = new FormatPrice ();

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_cart, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewID (view);
        initEvent ();
        initData ();
    }

    private void initData() {
        cartPresenter.getCart (this);
    }

    private void initEvent() {
        imgViewClose.setOnClickListener (v -> CartFragment.this.dismiss ());
        btnOrder.setOnClickListener (v -> ConfirmDialogFragment.newInstance ((int) total).show (getFragmentManager (), ""));
    }

    private void initViewID(View view) {
        cartPresenter = new CartPresenterImp (getActivity ().getApplication (), this);
        imgViewClose = view.findViewById (R.id.img_view_close);
        mapView = view.findViewById (R.id.img_view_map_cart);
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
        getDialog ().getWindow ().clearFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog ().getWindow ().setStatusBarColor (getResources ().getColor (R.color.colorPrimaryDark));
    }
}
