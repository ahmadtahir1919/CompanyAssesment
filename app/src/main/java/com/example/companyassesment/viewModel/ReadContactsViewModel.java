package com.example.companyassesment.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.companyassesment.models.SavedContact;


import java.util.ArrayList;


public class ReadContactsViewModel extends AndroidViewModel {
    private LiveData<ArrayList<SavedContact>> contactList;
    private RepositoryContactList repositoryContactList;

    public ReadContactsViewModel(@NonNull Application application) {
        super(application);
        repositoryContactList=new RepositoryContactList();
        contactList=new MutableLiveData<>();

    }
    public LiveData<ArrayList<SavedContact>> getAllContacts() {
        this.contactList = repositoryContactList.getContacts(getApplication());
        return contactList;
    }

}
