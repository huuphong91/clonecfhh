package com.example.thecoffeehouse.order.cart;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartInstance {

    private static CartInstance sInstance;
    private static List<Cart> mList;

    private CartInstance() {
        mList = new ArrayList<> ();
    }

    public static CartInstance getInstance() {
        if (sInstance == null) {
            sInstance = new CartInstance ();
        }
        return sInstance;
    }

    public List<Cart> getListCart() {
        return mList;
    }

    public void setListCart(List<Cart> mValues) {
        mList.clear ();
        mList.addAll (mValues);
    }
}
