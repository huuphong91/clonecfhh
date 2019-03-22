package com.example.thecoffeehouse.order.cart;

import android.app.Application;

import com.example.thecoffeehouse.order.cart.database.CartRepository;

import androidx.lifecycle.LifecycleOwner;

public class CartPresenterImp implements CartPresenter {
    private CartFragmentView cartFragmentView;
    private CartRepository cartRepository;

    public CartPresenterImp(Application application, CartFragmentView cartFragmentView) {
        this.cartFragmentView = cartFragmentView;
        cartRepository = new CartRepository (application);
    }

    @Override
    public void getCart(LifecycleOwner lifecycleOwner) {
        cartRepository.getCarts ().observe (lifecycleOwner, (carts) -> cartFragmentView.setData (carts));
    }
}
