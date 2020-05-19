package com.example.companyassesment.dagger.interfaces;


import com.example.companyassesment.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface APIServices {

    @GET("/users")
    Call<ArrayList<User>> getUsers();

}