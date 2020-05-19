package com.example.companyassesment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.companyassesment.dagger.component.DaggerNetworkComponent;
import com.example.companyassesment.dagger.component.NetworkComponent;
import com.example.companyassesment.dagger.interfaces.APIServices;
import com.example.companyassesment.dagger.module.ContextModule;

public abstract class BaseActivity extends AppCompatActivity {
    private APIServices apiServices;

    public APIServices getApiServices() {
        /*If ApiServices is null first get from MyApplication Class*/
        if (apiServices == null) {
            apiServices = ((MyApplication) getApplicationContext()).getNetworkComponent().getAPIServices();
        }
        /*If ApiServices from MyApplication Class Instance is also null then recreate it*/

        if (apiServices == null) {
            NetworkComponent networkComponent = DaggerNetworkComponent.builder()
                    .contextModule(new ContextModule(getApplicationContext()))
                    .build();
            ((MyApplication) getApplicationContext())
                    .setNetworkComponent(networkComponent);
            apiServices = networkComponent.getAPIServices();
        }

        return apiServices;
    }

}
