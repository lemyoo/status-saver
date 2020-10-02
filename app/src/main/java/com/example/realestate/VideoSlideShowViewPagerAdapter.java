package com.example.realestate;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VideoSlideShowViewPagerAdapter extends RecyclerView.Adapter<VideoSlideShowViewPagerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mVideoPath;
    LayoutInflater mLayoutInflater;
    int postionnn;

    public VideoSlideShowViewPagerAdapter(Context context, ArrayList<String> videoPath) {
        mContext = context;
        mVideoPath = videoPath;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_slider_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mVideoView.setVideoPath(mVideoPath.get(position));
        holder.go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,MainActivity.class));
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile(mVideoPath.get(position));
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveVideo(mVideoPath.get(position));
                Snackbar.make(v,"download",Snackbar.LENGTH_SHORT).show();
            }
        });

        postionnn = position;


    }

    private void SaveVideo(String path) {
        String rootDirectory = Environment.getExternalStorageState().toString();
        File statusSaverDirectory = new File(rootDirectory+"/dkStatusSaver");
        if(!statusSaverDirectory.exists()){
            statusSaverDirectory.mkdir();
            putVideoIn(statusSaverDirectory,path);
        }else {
            putVideoIn(statusSaverDirectory,path);
        }
    }

    private void putVideoIn(File statusSaverDirectory, String path) {
        String strPath = path.substring(path.lastIndexOf("/",path.length()+1));
        String fileName = strPath;
        File file = new File(statusSaverDirectory,fileName);
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void shareFile(String videoPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("video/mp4");
        ContentValues content = new ContentValues();
        content.put(MediaStore.Video.Media.MIME_TYPE,"video/mp4");
        content.put(MediaStore.Video.Media.DATA,videoPath);
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,content);
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        mContext.startActivity(Intent.createChooser(intent,"Share Video"));
    }


    @Override
    public int getItemCount() {
        return mVideoPath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        VideoView mVideoView;
        FloatingActionButton share,download,go_back;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.video_view_test);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            share = itemView.findViewById(R.id.share_video);
            download = itemView.findViewById(R.id.download_video);
            go_back = itemView.findViewById(R.id.go_back_video);
        }
    }

}
