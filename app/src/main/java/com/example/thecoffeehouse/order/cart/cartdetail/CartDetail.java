package com.example.thecoffeehouse.order.cart.cartdetail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thecoffeehouse.Constant;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.data.model.product.DataItem;
import com.example.thecoffeehouse.data.model.product.VariantsItem;
import com.example.thecoffeehouse.main.OnUpdateListener;
import com.example.thecoffeehouse.order.FormatPrice;
import com.example.thecoffeehouse.order.cart.CartInstance;
import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CartDetail extends DialogFragment implements CartDetailView {
    private ImageView imgViewProduct;
    private TextView tvName;
    private TextView tvDescription, tvSeeMore;
    private TextView tvPrice;
    private Button btnAddToCart;
    private ImageView imgViewDecrease;
    private ImageView imgViewIncrease;
    private ImageView imgViewClose;
    private DataItem dataItem;
    private Cart cartItem;
    private TextView tvQuality;
    private int count;
    private int price, valCount;
    private String size = "";
    private RadioButton radSmall, radMedium, radLarge;
    private CartDetailPresenterImp cartDetailPresenter;
    private List<VariantsItem> varList;
    private FormatPrice formatPrice = new FormatPrice ();
    private int isCheck;
    private OnUpdateListener mListener;

    public static CartDetail newInstance(Cart item) {
        CartDetail fragment = new CartDetail ();
        Bundle bundle = new Bundle ();
        bundle.putSerializable ("cartItem", item);
        fragment.setArguments (bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach (context);
        if (getArguments () != null) {
            cartItem = (Cart) getArguments ().getSerializable ("cartItem");
        }
        if (context instanceof OnUpdateListener) {
            mListener = (OnUpdateListener) context;
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
        setStyle (DialogFragment.STYLE_NORMAL, R.style.DetailDialogFragment);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initViewId (view);
        initEvent ();
        cartDetailPresenter.getCartItem (cartItem.getPid ());
    }


    private void initData() {
        price = dataItem.getPrice ();
        count = Integer.parseInt (String.valueOf (cartItem.getCount ()));
        Glide.with (getContext ()).load (dataItem.getImage ()).into (imgViewProduct);
        tvName.setText (dataItem.getProductName ());
        tvDescription.setText (dataItem.getDescription ());
        tvPrice.setText (String.format ("+%s", formatPrice.formatPrice (dataItem.getBasePrice () * count)));
        tvQuality.setText (String.valueOf (cartItem.getCount ()));
        for (VariantsItem item : varList) {
            if (item.getVal ().toLowerCase ().equals ("nhỏ")) {
                radSmall.setVisibility (View.VISIBLE);
            }
            if (item.getVal ().toLowerCase ().equals ("vừa")) {
                radMedium.setVisibility (View.VISIBLE);
            }
            if (item.getVal ().toLowerCase ().equals ("lớn")) {
                radLarge.setVisibility (View.VISIBLE);
            }
        }

        switch (cartItem.getSize ().toLowerCase ()) {
            case Constant.SMALL_SIZE:
                radSmall.setChecked (true);
                break;
            case Constant.MEDIUM_SIZE:
                radMedium.setChecked (true);
                break;
            case Constant.LARGE_SIZE:
                radLarge.setChecked (true);
                break;
        }
    }

    private void initEvent() {
        imgViewClose.setOnClickListener (v -> CartDetail.this.dismiss ());
        tvDescription.setOnClickListener (v -> {
            showMore ();
        });
        tvSeeMore.setOnClickListener (v -> showMore ());
        imgViewDecrease.setOnClickListener (v -> {
            count--;
            if (count <= 0) {
                count = 0;
                btnAddToCart.setText (getActivity ().getResources ().getString (R.string.button_remove_from_cart));
            }
            tvQuality.setText (String.valueOf (count));
            formatPrice (size);
        });

        imgViewIncrease.setOnClickListener (v -> {
            btnAddToCart.setText (getActivity ().getResources ().getString (R.string.button_add_to_cart));
            if (count < 100) {
                count++;
            } else
                count = 99;
            tvQuality.setText (String.valueOf (count));
            formatPrice (size);
        });

        radSmall.setOnCheckedChangeListener ((buttonView, isChecked) -> {
            if (isChecked) {
                size = radSmall.getText ().toString ();
                formatPrice (size);
            }
        });

        radMedium.setOnCheckedChangeListener ((buttonView, isChecked) -> {
            if (isChecked) {
                size = radMedium.getText ().toString ();
                formatPrice (size);
            }
        });

        radLarge.setOnCheckedChangeListener ((buttonView, isChecked) -> {
            if (isChecked) {
                size = radLarge.getText ().toString ();
                formatPrice (size);
            }
        });
        btnAddToCart.setOnClickListener (v -> {
            if (!"".equals (size)) {
                if (count == 0) {
                    if (size.toLowerCase ().equals (cartItem.getSize ().toLowerCase ())) {
                        cartDetailPresenter.removeCartItem (cartItem);
                        if (CartInstance.getInstance ().getListCart ().size () == 1) {
                            CartDetail.this.dismiss ();
                            mListener.onUpdateFragment ();
                            return;
                        }
                    }
                } else {
                    Cart item = newCartItem ();
                    if (item != null) {
                        item.setId (cartItem.getId ());
                        cartDetailPresenter.updateCartItem (item);
                        Toast.makeText (getContext (), dataItem.getProductName () + " Được thêm vào",
                                Toast.LENGTH_SHORT).show ();
                    }
                }
                CartDetail.this.dismiss ();
            } else {
                Toast.makeText (getContext (), "Thử lại", Toast.LENGTH_SHORT).show ();
            }
        });
    }

    private boolean valOption(String val) {
        for (VariantsItem variantsItem : varList) {
            if (variantsItem.getVal ().equalsIgnoreCase (val)) return true;
        }
        return false;
    }

    private void formatPrice(String size) {
        tvPrice.setText (String.format ("+%s", formatPrice.formatPrice (Integer.parseInt (String.valueOf (count * ("".equalsIgnoreCase (size)
                ? dataItem.getBasePrice () : getPrice (size)))))));
    }

    private void initViewId(View view) {
        cartDetailPresenter = new CartDetailPresenterImp (getActivity ().getApplication (), this);
        imgViewProduct = view.findViewById (R.id.img_btn_product);
        tvName = view.findViewById (R.id.tv_name_product);
        tvDescription = view.findViewById (R.id.tv_description);
        tvSeeMore = view.findViewById (R.id.tv_see_more);
        tvPrice = view.findViewById (R.id.tv_price);
        btnAddToCart = view.findViewById (R.id.btn_add_cart);
        imgViewDecrease = view.findViewById (R.id.img_btn_decrease);
        imgViewIncrease = view.findViewById (R.id.img_btn_increase);
        imgViewClose = view.findViewById (R.id.img_btn_close);
        tvQuality = view.findViewById (R.id.tv_quatity);
        radSmall = view.findViewById (R.id.radio_small);
        radSmall.setVisibility (View.GONE);
        radMedium = view.findViewById (R.id.radio_medium);
        radMedium.setVisibility (View.GONE);
        radLarge = view.findViewById (R.id.radio_large);
        radLarge.setVisibility (View.GONE);
    }

    @Override
    public void getCartItem(DataItem item) {
        dataItem = item;
        varList = dataItem.getVariants ();
        valCount = varList.size ();
        if (dataItem != null) {
            initData ();
        }
    }

    private Cart newCartItem() {
        Cart itemCart = null;
        if ((!"".equals (size))) {
            itemCart = new Cart (dataItem.getId (), count, dataItem.getProductName (), size, price);
        }
        return itemCart;
    }

    private int getPrice(String size) {
        for (VariantsItem item : varList) {
            if (item.getVal ().equalsIgnoreCase (size)) {
                price = item.getPrice ();
            }
        }
        return price;
    }

    private void showMore() {
        if (isCheck == 0) {
            tvDescription.setMaxLines (30);
            tvSeeMore.setVisibility (View.GONE);
            isCheck = 1;
        } else {
            tvDescription.setMaxLines (3);
            tvSeeMore.setVisibility (View.VISIBLE);
            isCheck = 0;
        }
    }
}
