package com.teamducati.cloneappcfh.screen.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetAddressesTask extends AsyncTask<String, Void, List<String>> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private GetAddressesTask.OnTaskCompleted mListener;

    GetAddressesTask(Context mContext, GetAddressesTask.OnTaskCompleted listener) {
        this.mContext = mContext;
        mListener = listener;
    }

    @Override
    protected List<String> doInBackground(String... strings) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        String searchShipAddress = strings[0];

        List<Address> addresses;

        List<String> addressList = new ArrayList<>();

        try {
            addresses = geocoder.getFromLocationName(searchShipAddress, 5);
            if (addresses == null || addresses.size() == 0) {
                addressList.add("Not Found");
            } else {
                for (int i = 0; i < addresses.size(); i++) {
                    String address = addresses.get(i).getAddressLine(i);
                    addressList.add(address);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
        }

        return addressList;
    }

    @Override
    protected void onPostExecute(List<String> addressList) {
        mListener.onTaskCompleted(addressList);
        super.onPostExecute(addressList);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(List<String> addressList);
    }
}
