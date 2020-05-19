package com.example.companyassesment.dagger.component;



import com.example.companyassesment.dagger.interfaces.APIServices;
import com.example.companyassesment.dagger.interfaces.NetworkCallApplicationScope;
import com.example.companyassesment.dagger.module.OkHttpClientModule;
import com.example.companyassesment.dagger.module.RetrofitModule;

import dagger.Component;
import okhttp3.OkHttpClient;

@NetworkCallApplicationScope
@Component(modules = {RetrofitModule.class,
        OkHttpClientModule.class})
public interface NetworkComponent {

    APIServices getAPIServices();

}
