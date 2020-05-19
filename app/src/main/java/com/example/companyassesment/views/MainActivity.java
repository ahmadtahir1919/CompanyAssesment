package com.example.companyassesment.views;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.companyassesment.adapter.AdapterUsers;
import com.example.companyassesment.fragments.LocalFragment;
import com.example.companyassesment.fragments.OnlineFagment;
import com.example.companyassesment.BaseActivity;
import com.example.companyassesment.R;
import com.example.companyassesment.models.SavedContact;
import com.example.companyassesment.models.User;
import com.example.companyassesment.viewModel.ViewModelMainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.layout_parent)
    ConstraintLayout layoutParent;
    @BindView(R.id.txt_no_data_found)
    AppCompatTextView txtNoDataFound;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    OnlineFagment onlineFagment;
    LocalFragment localFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onlineFagment = new OnlineFagment();
        localFragment = new LocalFragment();
        TabLayout();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void TabLayout() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position); // for text
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(onlineFagment, "Online Users");
        adapter.addFragment(localFragment, "Local Users");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.online_data) {
            checkPermission(false);

            return true;
        } else if (id == R.id.local_data) {
            checkPermission(true);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPermission(boolean exportLocal) {
        PermissionListener lowLevelPermissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (exportLocal) {
                    localFragment.writeCsv();

                } else {
                    onlineFagment.writeCsv();

                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {


            }
        };

        TedPermission.with(MainActivity.this)
                .setPermissionListener(lowLevelPermissionListener)
                .setDeniedMessage(R.string.permission_denied)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


}
