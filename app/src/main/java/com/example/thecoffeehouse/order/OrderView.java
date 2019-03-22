package com.example.thecoffeehouse.order;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

public interface OrderView {

    void setCartLayout(List<Cart> carts);
}
