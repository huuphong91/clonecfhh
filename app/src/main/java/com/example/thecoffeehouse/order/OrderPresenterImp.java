package com.example.thecoffeehouse.order;

import android.app.Application;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.example.thecoffeehouse.order.cart.database.CartRepository;

import java.util.Locale;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void getLocationAddress(Location location) {
        if (orderView.getActivity () != null) {
            Geocoder geocoder = new Geocoder (orderView.getActivity (), Locale.getDefault ());
            Disposable disposable = Single
                    .fromCallable (() -> geocoder.getFromLocation (location.getLatitude (), location.getLongitude (), 1))
                    .subscribeOn (Schedulers.io ())
                    .toObservable ()
                    .flatMap (Observable::fromIterable)
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (address -> {
                        orderView.onLocationAddressUpdate (address);
                    }, throwable -> Log.d ("getLocationAddress: ", throwable + "--"));
        }
    }
}
