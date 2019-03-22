package com.example.thecoffeehouse.order;

import android.app.Application;

import com.example.thecoffeehouse.order.cart.database.CartRepository;

import androidx.lifecycle.LifecycleOwner;

public class OrderPresenterImp implements OrderPresenter {

    private OrderView orderView;
    private CartRepository cartRepository;

    public OrderPresenterImp(Application application, OrderView orderView) {
        this.orderView = orderView;
        cartRepository = new CartRepository (application);
    }

    @Override
    public void getCartItem(LifecycleOwner owner) {
        cartRepository.getCarts ().observe (owner, carts -> orderView.setCartLayout (carts));
    }
}
