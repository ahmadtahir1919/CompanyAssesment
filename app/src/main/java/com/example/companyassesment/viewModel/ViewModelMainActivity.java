package com.example.companyassesment.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.companyassesment.MyApplication;
import com.example.companyassesment.dagger.interfaces.APIServices;
import com.example.companyassesment.models.User;

import java.util.ArrayList;

public class ViewModelMainActivity extends AndroidViewModel {
    private RepositoryMainActivity repositoryMainActivity;
    private LiveData<ArrayList<User>> usersListLiveData;
    APIServices apiServices;

    public ViewModelMainActivity(@NonNull Application application) {
        super(application);
        repositoryMainActivity=new RepositoryMainActivity();
        apiServices = ((MyApplication) application.getApplicationContext()).getNetworkComponent().getAPIServices();

    }
    public LiveData<ArrayList<User>> getAllUsers() {
        this.usersListLiveData = repositoryMainActivity.getListOfUsers(getApplication(),apiServices);
        return usersListLiveData;
    }
}
