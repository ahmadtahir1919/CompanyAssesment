package com.example.companyassesment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import androidx.multidex.MultiDex;

import com.example.companyassesment.dagger.component.DaggerNetworkComponent;
import com.example.companyassesment.dagger.component.NetworkComponent;
import com.example.companyassesment.dagger.module.ContextModule;

public class MyApplication extends Application {


    private NetworkComponent networkComponent;


    private static MyApplication instance;

    public static MyApplication get(Activity activity) {
        return (MyApplication) activity.getApplication();
    }


    @Override
    public void onCreate() {
        super.onCreate();



        MultiDex.install(this);

        networkComponent = DaggerNetworkComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

        if (instance == null) instance = this;


    }


    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public void setNetworkComponent(NetworkComponent networkComponent) {
        this.networkComponent = networkComponent;
    }

    public static MyApplication getInstance() {
        return instance;
    }


    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            multiDexException.printStackTrace();
        }
    }


}
