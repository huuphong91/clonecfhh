package com.example.thecoffeehouse.order.cart.cartdetail;

import android.app.Application;

import com.example.thecoffeehouse.data.AppRespositoryImp;
import com.example.thecoffeehouse.order.cart.database.CartRepository;
import com.example.thecoffeehouse.order.cart.model.Cart;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartDetailPresenterImp implements CartDetailPresenter {

    private CartDetailView cartDetailView;
    private AppRespositoryImp appRespositoryImp;
    private CartRepository cartRepository;

    public CartDetailPresenterImp(Application application, CartDetailView cartDetailView) {
        this.cartDetailView = cartDetailView;
        appRespositoryImp = new AppRespositoryImp (application);
        cartRepository = new CartRepository (application);
    }

    @Override
    public void getCartItem(String id) {
        appRespositoryImp.getCartItem ()
                .subscribeOn (Schedulers.io ())
                .flatMap (order -> Observable.fromIterable (order.getData ()))
                .filter (dataItem -> dataItem.getId ().equals (id))
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe ((itemList) -> {
                    cartDetailView.getCartItem (itemList);
                });
    }

    @Override
    public void removeCartItem(Cart carts) {
        cartRepository.delete (carts);

    }

    @Override
    public void updateCartItem(Cart cart) {
        cartRepository.update (cart);
    }

    @Override
    public void insertCartItem(Cart cart) {
        cartRepository.insert (cart);
    }


}
