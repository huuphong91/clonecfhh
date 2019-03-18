package com.example.thecoffeehouse.order.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thecoffeehouse.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView imgViewClose;
    private EditText edtUsername;
    private EditText edtPhoneNumber;
    private Button btnChange;
    private TextView tvAddress;
    private EditText edtNoteForDriver;
    private EditText edtNoteForShop;
    private TextView tvPrice;
    private TextView tvTotal;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment ();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_cart, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewID (view);
    }

    private void initViewID(View view) {
        imgViewClose = view.findViewById (R.id.img_view_close);
        edtUsername = view.findViewById (R.id.edt_user_name);
        edtPhoneNumber = view.findViewById (R.id.edt_phone);
        btnChange = view.findViewById (R.id.btn_change);
        tvAddress = view.findViewById (R.id.tv_address);
        edtNoteForDriver = view.findViewById (R.id.edt_note_for_driver);
        edtNoteForShop = view.findViewById (R.id.edt_note_for_shop);
        tvPrice = view.findViewById (R.id.tv_price_cart);
        tvTotal = view.findViewById (R.id.tv_total_cart);

        recyclerView=view.findViewById (R.id.recycler_view_product_cart);
    }
}
