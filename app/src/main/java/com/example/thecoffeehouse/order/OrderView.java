package com.example.thecoffeehouse.order;

import android.app.Activity;
import android.location.Address;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

import androidx.fragment.app.FragmentActivity;

public interface OrderView {

    void setCartLayout(List<Cart> carts);
    void onLocationAddressUpdate(Address address);
    FragmentActivity getActivity();
}
