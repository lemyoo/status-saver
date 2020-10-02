package com.example.realestate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class PictureSlideShowActivity extends AppCompatActivity {
    ArrayList<String> mImagePath = new ArrayList<>();
    final static String POSITION = "com.example.realestate.POSITION ";
    final static String PICTURE = "com.example.realestate.PICTURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_sllide_show);

        Intent intent = getIntent();
        final Integer position = intent.getIntExtra(POSITION,0);
        mImagePath = intent.getStringArrayListExtra(PICTURE);

        ViewPager2 viewPager = findViewById(R.id.view_page_slide_show);
        PhotoSlideShowViewPagerAdapter photoSlideShowViewPagerAdapter = new PhotoSlideShowViewPagerAdapter(this,mImagePath);
        viewPager.setAdapter(photoSlideShowViewPagerAdapter);
        viewPager.setCurrentItem(position,false);

    }


}