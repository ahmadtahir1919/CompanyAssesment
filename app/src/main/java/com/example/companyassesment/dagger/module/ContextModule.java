package com.example.companyassesment.dagger.module;

import android.content.Context;

import com.example.companyassesment.dagger.interfaces.ApplicationContext;
import com.example.companyassesment.dagger.interfaces.NetworkCallApplicationScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @ApplicationContext
    @NetworkCallApplicationScope
    @Provides
    public Context context(){ return context.getApplicationContext(); }
}
