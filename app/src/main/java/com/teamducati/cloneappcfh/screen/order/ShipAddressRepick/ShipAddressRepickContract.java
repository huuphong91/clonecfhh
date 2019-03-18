package com.teamducati.cloneappcfh.screen.order.ShipAddressRepick;

import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.teamducati.cloneappcfh.utils.BasePresenter;
import com.teamducati.cloneappcfh.utils.BaseView;

import java.util.List;

public interface ShipAddressRepickContract {
    interface Presenter extends BasePresenter {
        void loadPositionShipAddressType();

        void loadShipAddressGooglePlaces(String input,
                                         FindAutocompletePredictionsRequest.Builder builder,
                                         PlacesClient placesClient);
    }

    interface View extends BaseView<Presenter> {
        void showPositionShipAddressTypes();

        void showShipAddressResults(List<String> addressList);
    }

}
