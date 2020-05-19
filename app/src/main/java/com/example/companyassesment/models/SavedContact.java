package com.example.companyassesment.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SavedContact  {


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone_no")
    @Expose
    private String phoneNumber = null;

    private String image;


    /**
     * No args constructor for use in serialization
     */
    public SavedContact() {
    }

    public SavedContact(String name, String phoneNumber, String image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null) {
            if (obj instanceof SavedContact) {
                SavedContact savedContact = (SavedContact) obj;
                if (savedContact.getPhoneNumber() != null)
                    return savedContact.getPhoneNumber().equals(this.phoneNumber);
                else if (savedContact.getName() != null)
                    return savedContact.getName().equals(this.name);
            }
            return false;
        }
        return false;
    }


}
