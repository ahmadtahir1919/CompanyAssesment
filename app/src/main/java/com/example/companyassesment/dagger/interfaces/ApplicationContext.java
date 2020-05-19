package com.example.companyassesment.dagger.interfaces;

import javax.inject.Qualifier;


/**
 * Alternative to @Named annotation is @Qualifier annotation to use multiple time in one app
 * Annotate this interface on that metods who provides Application Level Context.
 */
@Qualifier
public @interface ApplicationContext {

}
