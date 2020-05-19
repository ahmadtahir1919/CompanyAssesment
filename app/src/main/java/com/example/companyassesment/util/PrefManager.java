package com.example.companyassesment.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;


/**
 * <p>
 * <p>
 * I use this because in documentation asked me to cache the data other for saving the is pereferable to use room
 * This data is not too much to i use for cache in preference
 * </p>
 */

public class PrefManager {
    private static final String PREF_NAME = "myPref";
    private static final String ONLINE_CACHE_DATA = "ONLINE_CACHE_DATA";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static PrefManager prefManager = null;

    public static synchronized PrefManager getInstance(Context context) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager;
    }

    private PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();


    }

    public void setUserData(String userData) {
        editor.putString(ONLINE_CACHE_DATA, userData);
        editor.commit();
    }
    public String getUserData() {
        return pref.getString(ONLINE_CACHE_DATA, null);
    }

}