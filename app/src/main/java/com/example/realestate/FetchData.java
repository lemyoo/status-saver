package com.example.realestate;

import android.os.Environment;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.Arrays;

public class FetchData  {
    private static File[] dirFiles = new File(Environment.getExternalStorageDirectory()+"/WhatsApp/Media/.Statuses").listFiles();
    private File mFileDirectory;

    private FetchData(){

    }
    public static File[] getInstance(){
        Arrays.sort(dirFiles,LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        return dirFiles;
    }

}
