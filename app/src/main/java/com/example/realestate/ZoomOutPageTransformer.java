package com.example.realestate;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_ALPA = 0.5f;
    private static float MIN_SCALE = 0.85f;
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        if (position <-1){
            page.setAlpha(0);
        }
        else if(position <=1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float verMargin = pageHeight*(1-scaleFactor)/2;
            float horzMargin = pageWidth*(1-scaleFactor)/2;
            if (position <0){
                page.setTranslationX(horzMargin-verMargin/2);
            }
            else {
                page.setTranslationX(-horzMargin+verMargin/2);
            }
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setAlpha(MIN_ALPA +(scaleFactor - MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_ALPA));

        }
        else {
            page.setAlpha(0);
        }
    }
}
