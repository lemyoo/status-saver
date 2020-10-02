package com.example.realestate;

import android.content.Context;
import android.content.Intent;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideosFragmentRecyclerAdapter extends RecyclerView.Adapter<VideosFragmentRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<String> mVideoUrl;

    public VideosFragmentRecyclerAdapter(Context context,ArrayList<String> videoUrl){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mVideoUrl = videoUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.video,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .override(96,96);
        Glide.with(mContext)
                .asDrawable()
                .override(300,300)
                .load( mVideoUrl.get(position))
                .thumbnail(Glide.with(mContext).load(mVideoUrl.get(position)))
                .dontAnimate()
                .into(holder.mStatusVideo);
        holder.mPosition = position;
        holder.path = mVideoUrl.get(position);
    }

    @Override
    public int getItemCount() {
        return mVideoUrl.size();
    }

    public void setValues(ArrayList<String> newData) {
        this.mVideoUrl = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mStatusVideo;
        int mPosition;
        String path;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStatusVideo = itemView.findViewById(R.id.status_video);

            itemView.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, VideoSlideShowActivity.class);
                    intent.putExtra(VideoSlideShowActivity.POSITION, mPosition);
                    intent.putExtra(VideoSlideShowActivity.VIDEOS, mVideoUrl);
                    mContext.startActivity(intent);
               }
            });
        }
    }
}
