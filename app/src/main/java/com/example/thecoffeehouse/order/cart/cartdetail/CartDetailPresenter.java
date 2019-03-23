package com.example.thecoffeehouse.order.cart.cartdetail;

import com.example.thecoffeehouse.order.cart.model.Cart;

public interface CartDetailPresenter {

    void getCartItem(String id);

    void removeCartItem(Cart carts);

    void updateCartItem(Cart cart);

    void insertCartItem(Cart cart);

}
