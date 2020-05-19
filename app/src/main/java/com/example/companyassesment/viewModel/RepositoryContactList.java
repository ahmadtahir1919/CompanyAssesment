package com.example.companyassesment.viewModel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import com.example.companyassesment.models.SavedContact;

import java.util.ArrayList;

public class RepositoryContactList {
    private MutableLiveData<ArrayList<SavedContact>> contactList;

    public MutableLiveData<ArrayList<SavedContact>> getContacts(Context context) {
        if (contactList == null) {
            String phoneNumber;
            ArrayList<SavedContact> savedContacts = new ArrayList<>();
            SavedContact savedContact;
            ContentResolver cr = context.getContentResolver();
            ArrayList<String> idList = new ArrayList<>();
            if (cr != null) {
                Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
                if (cur != null) {
                    while (cur.moveToNext()) {


                        phoneNumber = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber = phoneNumber.replaceAll("\\s+", "");
                        if (phoneNumber.length() > 9) {
                            savedContact = new SavedContact();
                            String id = phoneNumber.substring(phoneNumber.length() - 7);
                            String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            String photoThumb = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                            savedContact.setPhoneNumber(phoneNumber);
                            savedContact.setImage(photoThumb);
                            savedContact.setName(name);


                            if (!idList.contains(id)) {
                                savedContacts.add(savedContact);
                                idList.add(id);
                            }
                        }
                    }

                    cur.close();
                    idList.clear();
                }

            }

            contactList = new MutableLiveData<>();
            contactList.setValue(savedContacts);
        }
        return contactList;
    }

}
