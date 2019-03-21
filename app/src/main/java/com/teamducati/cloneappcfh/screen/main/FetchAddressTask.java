package com.teamducati.cloneappcfh.screen.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.teamducati.cloneappcfh.entity.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FetchAddressTask extends AsyncTask<Location, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    FetchAddressTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(Location... locations) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        Location location = locations[0];

        List<Address> addresses;

        String resultMessage = "";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
            if (addresses == null || addresses.size() == 0) {
                if (resultMessage.isEmpty()) {
                    resultMessage = "No address Found";
                    Log.e("TAG", resultMessage);
                }
            } else {
                Address address = Objects.requireNonNull(addresses).get(0);
                resultMessage = address.getSubThoroughfare() + " " + address.getThoroughfare();
            }
        } catch (IOException ioException) {
            resultMessage = "Not available";
        } catch (IllegalArgumentException illegalArgumentException) {
            resultMessage = "invalid_lat_long_used";
        }

        return resultMessage;
    }

    @Override
    protected void onPostExecute(String lastLocation) {
        if (lastLocation != null) {
            MessageEvent event = new MessageEvent();
            event.setAddress(lastLocation);
            EventBus.getDefault().postSticky(event);
        }
        super.onPostExecute(lastLocation);
    }
}
