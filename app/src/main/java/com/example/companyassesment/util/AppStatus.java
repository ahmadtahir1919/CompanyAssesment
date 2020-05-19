package com.example.companyassesment.util;

/*
 * This class is used by all the other classes to check Internet connection
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class AppStatus {

    private static AppStatus instance;
    private boolean connected = false;

    public static AppStatus getInstance() {
        if (instance == null) {
            instance = new AppStatus();
        }
        return instance;
    }

    public boolean isNetworkConnected(Context con) {
        try {
            ConnectivityManager connectManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = null;
            if (connectManager != null) {
                networkInfo = connectManager.getActiveNetworkInfo();
            }
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            //CheckConnectivity Exception
            e.getMessage();
        }
        return connected;
    }




}
