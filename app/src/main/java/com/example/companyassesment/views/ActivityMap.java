package com.example.companyassesment.views;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.companyassesment.BaseActivity;
import com.example.companyassesment.R;
import com.example.companyassesment.models.Address;
import com.example.companyassesment.models.Company;
import com.example.companyassesment.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMap extends BaseActivity implements OnMapReadyCallback {
    User user;
    String userName = "";
    LatLng latLng;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    Address address;
    Company company;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getIntentData();
        initilizedMap();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("user")) {
                user = getIntent().getParcelableExtra("user");
            }
            if (getIntent().hasExtra("address")) {
                address = getIntent().getParcelableExtra("address");
            }
            if (getIntent().hasExtra("company")) {
                company = getIntent().getParcelableExtra("company");
            }
        }
    }

    private void initilizedMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        try {
            drawMarker(googleMap);
            clickListernOfMarker(googleMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clickListernOfMarker(GoogleMap googleMap) {
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.marker_view, null);
                AppCompatTextView txtName = v.findViewById(R.id.txt_name);
                AppCompatTextView txtUserName = v.findViewById(R.id.txt_user_contact_number);
                AppCompatTextView txt_address = v.findViewById(R.id.txt_address_name);
                AppCompatTextView txt_company = v.findViewById(R.id.txt_company_name);
                txtName.setText(user.getName());
                txtUserName.setText(user.getUsername());
                txt_address.setText(address.getCity());
                txt_company.setText(company.getName());

                return v;

            }
        });
        // Adding and showing marker while touching the GoogleMap
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
                googleMap.clear();

                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position on the MarkerOptions
                markerOptions.position(arg0);

                // Animating to the currently touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));

                // Adding marker on the GoogleMap
                Marker marker = googleMap.addMarker(markerOptions);

                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();

            }
        });
    }

    private void drawMarker(GoogleMap googleMap) {
        // Drawing marker on the map
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(Double.parseDouble(address.getGeo().getLat()),
                Double.parseDouble(address.getGeo().getLng())));
        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(address.getGeo().getLat()),
                Double.parseDouble(address.getGeo().getLng()))));
        googleMap.addMarker(markerOptions);
    }
}
