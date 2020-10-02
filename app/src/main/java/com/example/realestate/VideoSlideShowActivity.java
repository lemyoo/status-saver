package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.util.ArrayList;

public class VideoSlideShowActivity extends AppCompatActivity {
    public static final String POSITION = "com.example.realestate.POSITION";
    public static final String VIDEOS ="com.example.realestate.VIDEOS";

    ArrayList<String> mVideoPath = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_slide_show);

        Intent intent = getIntent();
        int position = intent.getIntExtra(POSITION,0);
        mVideoPath = intent.getStringArrayListExtra(VIDEOS);
        ViewPager2 viewPager = findViewById(R.id.view_page_video_slide_show);
        VideoSlideShowViewPagerAdapter videoSlideShowViewPagerAdapter = new VideoSlideShowViewPagerAdapter(this, mVideoPath);
        viewPager.setAdapter(videoSlideShowViewPagerAdapter);
        viewPager.setCurrentItem(position,false);
    }

}