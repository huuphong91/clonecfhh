package com.teamducati.cloneappcfh.screen.order.ShipAddressRepick;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class ShipAddressRepickPresenter implements ShipAddressRepickContract.Presenter {

    private ShipAddressRepickContract.View mView;

    private List<String> addressList = new ArrayList<>();

    public ShipAddressRepickPresenter(ShipAddressRepickContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadPositionShipAddressType() {
        mView.showPositionShipAddressTypes();
    }

    @Override
    public void loadShipAddressGooglePlaces(String input,
                                            FindAutocompletePredictionsRequest.Builder builder,
                                            PlacesClient placesClient) {
        builder.setQuery(input);
        Task<FindAutocompletePredictionsResponse> task =
                placesClient.findAutocompletePredictions(builder.build());
        task.addOnSuccessListener(response -> {
            for (AutocompletePrediction autocompletePrediction :
                    response.getAutocompletePredictions()) {
                addressList.add(String.valueOf(autocompletePrediction.getFullText(null)));
            }
        });
        mView.showShipAddressResults(addressList);
    }
}
