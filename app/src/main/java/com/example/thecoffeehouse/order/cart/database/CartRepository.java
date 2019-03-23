package com.example.thecoffeehouse.order.cart.database;

import android.app.Application;
import android.util.Log;

import com.example.thecoffeehouse.order.cart.model.Cart;

import java.util.List;
import java.util.Observable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartRepository {
    private CartDAO cartDAO;
    private LiveData<List<Cart>> mAllCart;

    public CartRepository(Application application) {
        database db = database.getDatabase (application);
        cartDAO = db.cartDAO ();
        mAllCart = cartDAO.getAllCarts ();
    }

    public LiveData<List<Cart>> getCarts() {
        return mAllCart;
    }

    public void insert(Cart cart) {
        Single.fromCallable (() -> (cartDAO.insert (cart)))
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe ((aLong, throwable) -> {
//                    Do nothing
                    Log.d ("insert: ", aLong + " ");
                });
    }

    public void update(Cart cart) {
        Single.fromCallable (() -> (cartDAO.update (cart)))
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe ((aLong, throwable) -> {
                    Log.d ("update: ", aLong + " ");
                });
    }

    public void delete(Cart cart) {
        Single.fromCallable (() -> (cartDAO.deleteCart (cart)))
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe ((aLong, throwable) -> {
                    Log.d ("delete: ", aLong + "-----");
                });
    }

    public void delall() {
        Single.fromCallable (() -> (cartDAO.delall ()))
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe ((aLong, throwable) -> {
                    // Do nothing
                });
    }
}
