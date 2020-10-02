package com.example.realestate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestate.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_ASK_PERMISSION = 1;

    private final static String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            checkForPermission();
        } catch (InterruptedException e) {
            finish();
            e.printStackTrace();
        }
    }

    private void init() {
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.AddFragment(new PhotosFragment());
        sectionsPagerAdapter.AddFragment(new VideosFragment());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    private void checkForPermission() throws InterruptedException {
        final ArrayList<String> missingPermissions = new ArrayList<String>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS){
            final int result = ContextCompat.checkSelfPermission(this,permission);
            if(result != PackageManager.PERMISSION_GRANTED){
                missingPermissions.add(permission);
            }
        }
        if(!missingPermissions.isEmpty()){
            final String[]permissions = missingPermissions.toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE_ASK_PERMISSION);
        }else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults,PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSION,REQUIRED_SDK_PERMISSIONS,grantResults);
        }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSION:
                for (int index = permissions.length-1; index>=0; --index){
                    if(grantResults[index] != PackageManager.PERMISSION_GRANTED){
                        finish();
                        return;
                    }
                    else {
                        init();
                    }
                }
                break;
        }
    }
}