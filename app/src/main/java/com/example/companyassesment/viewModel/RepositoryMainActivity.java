package com.example.companyassesment.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.companyassesment.dagger.interfaces.APIServices;
import com.example.companyassesment.models.SavedContact;
import com.example.companyassesment.models.User;
import com.example.companyassesment.util.PrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryMainActivity {
    ArrayList<User> users = new ArrayList<>();

    public LiveData<ArrayList<User>> getListOfUsers(Context context, APIServices apiServices) {
        final MutableLiveData<ArrayList<User>> data = new MutableLiveData<>();

        Call<ArrayList<User>> call = apiServices.getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.body() != null) {
                    users.addAll(response.body());
                    //Save contacts because of cache the data, if you asked me to store the data i will preferred to use room database
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    PrefManager.getInstance(context)
                            .setUserData(gson.toJson(response.body()));

                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                users = new ArrayList<>();
                Gson gson = new Gson();
                users.addAll(gson.fromJson(PrefManager.getInstance(context).getUserData(), new TypeToken<List<User>>(){}.getType()));


                data.setValue(users);

            }
        });

        return data;
    }

}
