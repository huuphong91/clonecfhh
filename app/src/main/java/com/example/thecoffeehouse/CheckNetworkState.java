package com.example.thecoffeehouse;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckNetworkState {

    private static CheckNetworkState INSTANCE;
    private Context context;

    private CheckNetworkState(Application application) {
        context = application.getApplicationContext ();
    }

    public static CheckNetworkState getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new CheckNetworkState (application);
        }
        return INSTANCE;
    }


    public boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo ();
        if (null != activeNetwork) {
            if (activeNetwork.getType () == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
            if (activeNetwork.getType () == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
