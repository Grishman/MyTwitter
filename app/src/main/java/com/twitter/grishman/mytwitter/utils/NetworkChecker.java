package com.twitter.grishman.mytwitter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by grishman on 12/07/17.
 */

public class NetworkChecker implements NetChecker {
    private ConnectivityManager connectivityManager;

    @Inject
    public NetworkChecker(@NonNull Context context) {
        connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
