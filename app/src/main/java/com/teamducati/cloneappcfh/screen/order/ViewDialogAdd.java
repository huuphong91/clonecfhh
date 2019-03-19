package com.teamducati.cloneappcfh.screen.order;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamducati.cloneappcfh.R;
import com.teamducati.cloneappcfh.entity.api_order.DataItem;
import com.teamducati.cloneappcfh.entity.api_order.VariantsItem;
import com.teamducati.cloneappcfh.utils.Constants;
import com.teamducati.cloneappcfh.utils.Utils;
import com.teamducati.cloneappcfh.utils.eventsbus.EventsBusCart;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;

    private static int numberProduct;
    private static int money;

    private DataItem dataItem;
    private boolean firstClick;

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
        bindingData();
        setEvents();
    }

    public void bindingData() {
        setVisible();
        firstClick = true;
        numberProduct = 1;

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
        tvSeeMore.setOnClickListener(this);
        tvDescription.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        btnSmall.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnLarge.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_description:
            case R.id.tv_see_more:
                getFullDescription();
                break;
            case R.id.iv_minus:
                numberProduct = numberProduct > 1 ? --numberProduct : 1;
                tvNumberProduct.setText(String.valueOf(numberProduct));
                tvPrice.setText(" + " + Utils.formatMoney(numberProduct * money));
                break;
            case R.id.iv_plus:
                ++numberProduct;
                tvNumberProduct.setText(String.valueOf(numberProduct));
                tvPrice.setText(" + " + Utils.formatMoney(numberProduct * money));
                break;
            case R.id.btn_small:
                getMoney("Nhỏ");
                tvPrice.setText(" + " + Utils.formatMoney(money * numberProduct));
                break;
            case R.id.btn_normal:
                getMoney("Vừa");
                tvPrice.setText(" + " + Utils.formatMoney(money * numberProduct));
                break;
            case R.id.btn_large:
                getMoney("Lớn");
                tvPrice.setText(" + " + Utils.formatMoney(money * numberProduct));
                break;
            case R.id.btn_add:
                EventBus.getDefault().post(new EventsBusCart(dataItem, numberProduct, money));
                dismiss();
                break;
            case R.id.iv_delete:
                dismiss();
                break;
        }
    }

    private void getMoney(String size) {
        for (VariantsItem variantsItem : dataItem.getVariants()) {
            if (size.equals(variantsItem.getVal())) {
                money = variantsItem.getPrice();
                break;
            }
        }
    }

    private void setVisible() {
        switch (dataItem.getVariants().size()) {
            case 1:
                btnSmall.setVisibility(View.GONE);
                btnLarge.setVisibility(View.GONE);
                break;
            case 2:
                btnLarge.setVisibility(View.GONE);
                break;
            case 3:
                break;
        }
    }

    private void getFullDescription() {
        if (firstClick) {
            firstClick = false;
            tvDescription.setEllipsize(null);
            tvDescription.setMaxLines(30);
            tvSeeMore.setVisibility(View.GONE);
        } else {
            firstClick = true;
            tvDescription.setEllipsize(TextUtils.TruncateAt.END);
            tvDescription.setMaxLines(3);
            tvSeeMore.setVisibility(View.VISIBLE);
        }
    }
}