package com.teamducati.cloneappcfh.screen.order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.utils.Constants;
import com.teamducati.cloneappcfh.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewDialogAdd extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_name_product)
    TextView tvNameProduct;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_see_more)
    TextView tvSeeMore;
    @BindView(R.id.btn_small)
    Button btnSmall;
    @BindView(R.id.btn_normal)
    Button btnNormal;
    @BindView(R.id.btn_large)
    Button btnLarge;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_minus)
    ImageView ivMinus;
    @BindView(R.id.tv_number_product)
    TextView tvNumberProduct;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.btn_like)
    Button btnLike;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private static int numberProduct = 1;
    private static int money;

    private DataItem dataItem;

    public ViewDialogAdd() {

    }

    public static ViewDialogAdd newInstance(DataItem dataItem) {
        ViewDialogAdd fragment = new ViewDialogAdd();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BUNDLE_DETAIL, dataItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            dataItem = getArguments().getParcelable(Constants.KEY_BUNDLE_DETAIL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dialog_add_cart, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);

        setTitle();
        setEvents();
    }

    public void setTitle() {
        Glide.with(getActivity())
                .load(dataItem.getImage())
                .centerCrop()
                .placeholder(R.drawable.icon_loading)
                .into(ivProduct);

        tvNameProduct.setText(dataItem.getProductName());
        tvDescription.setText(dataItem.getDescription());

        money = dataItem.getBasePrice();
        tvPrice.setText(" + " + Utils.formatMoney(money));
    }

    private void setEvents() {
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        btnSmall.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnLarge.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_minus:
                numberProduct = numberProduct > 1 ? --numberProduct : 1;
                tvNumberProduct.setText(String.valueOf(numberProduct));
                break;
            case R.id.iv_plus:
                tvNumberProduct.setText(String.valueOf(++numberProduct));
                break;
            case R.id.btn_small:
                money = dataItem.getPrice();
                tvPrice.setText(String.valueOf(money));
                break;
            case R.id.btn_normal:
                money = dataItem.getPrice();
                tvPrice.setText(String.valueOf(money));
                break;
            case R.id.btn_large:
                money = dataItem.getPrice();
                tvPrice.setText(String.valueOf(money));
                break;
        }
    }
}