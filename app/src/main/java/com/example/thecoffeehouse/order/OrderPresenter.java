package com.example.thecoffeehouse.order;

import android.location.Location;

import androidx.lifecycle.LifecycleOwner;

public interface OrderPresenter {

    void getCartItem(LifecycleOwner owner);
    void getLocationAddress(Location location);
}
