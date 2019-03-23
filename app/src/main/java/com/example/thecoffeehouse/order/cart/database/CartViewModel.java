package com.example.thecoffeehouse.order.cart.database;

import android.app.Application;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CartViewModel extends AndroidViewModel {

    private CartRepository mRepository;

    private LiveData<List<Cart>> allCarts;

    public CartViewModel(Application application) {
        super (application);
        mRepository = new CartRepository (application);
        allCarts = mRepository.getCarts ();
    }

    public LiveData<List<Cart>> getAllCarts() {
        return allCarts;
    }

    public void insert(Cart cart) {
        mRepository.insert (cart);
    }

    public void delete(Cart carts) {
        mRepository.delete (carts);
    }

    public void delall(){
        mRepository.delall ();
    }
}
