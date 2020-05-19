package com.example.companyassesment.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.companyassesment.R;
import com.example.companyassesment.adapter.AdapterContactList;
import com.example.companyassesment.models.SavedContact;
import com.example.companyassesment.viewModel.ReadContactsViewModel;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LocalFragment extends Fragment {
    ArrayList<SavedContact> contactList;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_no_data_found)
    TextView txtNoDataFound;
    ReadContactsViewModel readContactsViewModel;
    @BindView(R.id.appCompatEditText)
    AppCompatEditText appCompatEditText;

    public LocalFragment() {
    }

    AdapterContactList adapterContactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, view);
        readContactsViewModel = ViewModelProviders.of(this)
                .get(ReadContactsViewModel.class);
        readContacts();
        TextWatcher pickupTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterContactList != null)
                    adapterContactList.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        appCompatEditText.addTextChangedListener(pickupTextWatcher);
        return view;
    }

    private void readContacts() {
        PermissionListener lowLevelPermissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                readContactsViewModel.getAllContacts().observe(getViewLifecycleOwner(), new Observer<ArrayList<SavedContact>>() {
                    @Override
                    public void onChanged(ArrayList<SavedContact> savedContacts) {
                        if (savedContacts != null && savedContacts.size() > 0) {
                            contactList = new ArrayList<>();
                            contactList.addAll(savedContacts);
                            initilizedAdapter();
                            txtNoDataFound.setVisibility(View.GONE);
                        }
                    }
                });
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {


            }
        };

        TedPermission.with(getActivity())
                .setPermissionListener(lowLevelPermissionListener)
                .setDeniedMessage(R.string.permission_denied)
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }

    private void initilizedAdapter() {
        adapterContactList = new AdapterContactList(getActivity(), contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterContactList);
    }
    public void writeCsv() {
        String commaSeparatedValues = "Name , number";

        if (contactList != null) {
            for(int i=0;i<contactList.size();i++)
            {
                commaSeparatedValues += contactList.get(i).getName() + "," +contactList.get(i).getPhoneNumber() + ",";

            }

            if (commaSeparatedValues.endsWith(",")) {
                commaSeparatedValues = commaSeparatedValues.substring(0,
                        commaSeparatedValues.lastIndexOf(","));
            }
        }
        try {
            String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "OnlineContacts.csv";
            String filePath = baseDir + File.separator + fileName;
            File f = new File(filePath);
            if(!f.exists())
            {
                f.createNewFile();
            }
            else
            {
                f.delete();
                f.createNewFile();
            }
            FileWriter fstream = new FileWriter(f, false);

            BufferedWriter out = new BufferedWriter(fstream);
            out.write(commaSeparatedValues);
            out.close();

            Intent intentShareFile = new Intent(Intent.ACTION_SEND);

            if(f.exists()) {
                intentShareFile.setType("text/plain");
                intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + f.getAbsolutePath()));

                intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                        "Sharing File...");
                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                startActivity(Intent.createChooser(intentShareFile, "Share File"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

