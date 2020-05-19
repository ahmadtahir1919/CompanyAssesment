package com.example.companyassesment.dagger.module;


import android.content.Context;

import com.example.companyassesment.dagger.interfaces.APIServices;
import com.example.companyassesment.dagger.interfaces.ApplicationContext;
import com.example.companyassesment.dagger.interfaces.NetworkCallApplicationScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = OkHttpClientModule.class)
public class RetrofitModule {

    @Provides
    public APIServices APPServerAPI(Retrofit retrofit){
        return retrofit.create(APIServices.class);
    }

    @NetworkCallApplicationScope
    @Provides
    public Retrofit retrofit(@ApplicationContext Context context, OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson){


        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }
    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }


}
