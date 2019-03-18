package com.example.thecoffeehouse.order.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DetailDialogFragment extends DialogFragment {
    private ImageView imgViewProduct;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvPrice;
    private Button btnAddToCart;
    private ImageView imgViewDecrease;
    private ImageView imgViewIncrease;
    private ImageView imgViewClose;
    private DataItem dataItem;
    private TextView tvQuality;
    private  TextView tvSl;
    private int count = 1;

    public static DetailDialogFragment newInstance(DataItem item) {
        DetailDialogFragment fragment = new DetailDialogFragment ();
        Bundle bundle = new Bundle ();
        bundle.putSerializable ("Product", item);
        fragment.setArguments (bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (getArguments () != null) {
            dataItem = (DataItem) getArguments ().getSerializable ("Product");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setStyle (DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewId (view);
        initEvent ();
        initData ();
    }

    private void initData() {
        Glide.with (getContext ()).load (dataItem.getImage ()).into (imgViewProduct);
        tvName.setText (dataItem.getProductName ());
        tvDescription.setText (dataItem.getDescription ());
        tvPrice.setText (String.valueOf (dataItem.getPrice ()));
    }

    private void initEvent() {
        imgViewClose.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DetailDialogFragment.this.dismiss ();
            }
        });
        imgViewDecrease.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                } else {
                    count = 1;
                }
                tvQuality.setText (String.valueOf (count));
                tvPrice.setText (String.format ("+%s", String.valueOf (dataItem.getPrice () * count)));
            }
        });
        imgViewIncrease.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (count < 100)
                    count++;
                else
                    count = 99;
                tvQuality.setText (String.valueOf (count));
                tvPrice.setText (String.format ("+%s", String.valueOf (dataItem.getPrice () * count)));
            }
        });
        btnAddToCart.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DetailDialogFragment.this.dismiss ();
                Toast.makeText (getContext (), String.format ("%s - Đã được thêm vào", dataItem.getProductName ()), Toast.LENGTH_SHORT).show ();
            }
        });
    }

    private void initViewId(View view) {
        imgViewProduct = view.findViewById (R.id.img_btn_product);
        tvName = view.findViewById (R.id.tv_name_product);
        tvDescription = view.findViewById (R.id.tv_description);
        tvPrice = view.findViewById (R.id.tv_price);
        btnAddToCart = view.findViewById (R.id.btn_add_cart);
        imgViewDecrease = view.findViewById (R.id.img_btn_decrease);
        imgViewIncrease = view.findViewById (R.id.img_btn_increase);
        imgViewClose = view.findViewById (R.id.img_btn_close);
        tvQuality = view.findViewById (R.id.tv_quatity);
    }
}
