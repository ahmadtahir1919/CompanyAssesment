package com.example.companyassesment.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.example.companyassesment.dagger.interfaces.NetworkCallApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 *
 * This Module provide activity Context to our app.
 */
@Module
public class ActivityModule {

    private final Context context;

    ActivityModule(Activity context){
        this.context = context;
    }

    /**
     * This annotation helps us to differentiate the Context.
     *  We can differentiate the method context() in ActivityModule and ContextModule by adding the @Named annotation as below.
     * @return
     */
    @Named("activity_context")
    @NetworkCallApplicationScope
    @Provides
    public Context context(){ return context; }
}
