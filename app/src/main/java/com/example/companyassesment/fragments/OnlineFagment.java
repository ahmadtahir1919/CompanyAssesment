package com.example.companyassesment.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.companyassesment.R;
import com.example.companyassesment.adapter.AdapterUsers;
import com.example.companyassesment.models.User;
import com.example.companyassesment.viewModel.ViewModelMainActivity;
import com.example.companyassesment.views.ActivityMap;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineFagment extends Fragment {


    ArrayList<User> userArrayList = new ArrayList<>();
    AdapterUsers adapterUsers;
    ProgressDialog progressDialog;

    public ViewModelMainActivity viewModelMainActivity;
    @BindView(R.id.layout_parent)
    ConstraintLayout layoutParent;
    @BindView(R.id.online_recycler_view)
    RecyclerView onlineRecyclerView;
    @BindView(R.id.txt_online_no_data_found)
    TextView txtOnlineNoDataFound;
    User user;
    @BindView(R.id.appCompatEditText)
    AppCompatEditText appCompatEditText;
    boolean isShown = false;

    public OnlineFagment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online, container, false);
        ButterKnife.bind(this, view);
        TextWatcher pickupTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterUsers != null)
                    adapterUsers.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        appCompatEditText.addTextChangedListener(pickupTextWatcher);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initiateProgress();
        initiateViewModel();
        showProgress();
        getDataFromApi();

    }

    private void getDataFromApi() {
        showProgress();
        viewModelMainActivity.getAllUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null) {
                    txtOnlineNoDataFound.setVisibility(View.GONE);
                    hideProgress();
                    userArrayList = users;
                    initilizedAdapter();
                    adapterUsers.setItemCancelClick(position -> {
                        Intent intent = new Intent(getActivity(), ActivityMap.class);
                        makerUser(position);
                        intent.putExtra("user", user);
                        intent.putExtra("address", userArrayList.get(position).getAddress());
                        intent.putExtra("company", userArrayList.get(position).getCompany());
                        startActivity(intent);
                    });
                } else {
                    Toast.makeText(getContext(), "Data not received Please connect to the internet", Toast.LENGTH_SHORT).show();
                    hideProgress();
                    txtOnlineNoDataFound.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar
                .make(layoutParent, "Please connect the internet", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void initiateProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
    }

    public void showProgress() {
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                if (!getActivity().isFinishing()) {
                    if (isShown) {
                        progressDialog.show();
                        isShown = true;
                    }
                }
            }
        }
    }

    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.hide();

            }
        }
    }

    private void initiateViewModel() {
        viewModelMainActivity = ViewModelProviders.of(this).get(ViewModelMainActivity.class);
    }

    private void initilizedAdapter() {
        adapterUsers = new AdapterUsers(getActivity(), userArrayList);
        onlineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        onlineRecyclerView.setAdapter(adapterUsers);
    }

    private void makerUser(int position) {
        user = new User();
        user.setEmail(userArrayList.get(position).getEmail());
        user.setName(userArrayList.get(position).getName());
        user.setPhone(userArrayList.get(position).getPhone());
        user.setUsername(userArrayList.get(position).getUsername());
        user.setWebsite(userArrayList.get(position).getWebsite());
    }
    public void writeCsv() {
        String commaSeparatedValues = "Name , number";

        if (userArrayList != null) {

            for(int i=0;i<userArrayList.size();i++)
            {
                commaSeparatedValues += userArrayList.get(i).getName() + "," +userArrayList.get(i).getPhone() + ",";

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

