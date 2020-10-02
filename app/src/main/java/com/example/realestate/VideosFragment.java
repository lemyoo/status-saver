package com.example.realestate;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.io.File;
import java.util.ArrayList;

public class VideosFragment extends Fragment {
    ArrayList<String> mVideoPath = new ArrayList<>();
    private VideosFragmentRecyclerAdapter mVideosFragmentRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videos, container, false);
        fecthData();

        mVideosFragmentRecyclerAdapter = new VideosFragmentRecyclerAdapter(getContext(),mVideoPath);
        RecyclerView videoRecyclerView = root.findViewById(R.id.video_lists);
        GridLayoutManager videoGridLayoutManager = new GridLayoutManager(getContext(),2);
        videoRecyclerView.setLayoutManager(videoGridLayoutManager);
        videoRecyclerView.setAdapter(mVideosFragmentRecyclerAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_container_video);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoPath.clear();
                        fecthData();
                        ArrayList<String> newData = mVideoPath;
                        mVideosFragmentRecyclerAdapter.setValues(newData);
                        mVideosFragmentRecyclerAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light)
        );
        return root;
    }

    private void fecthData() {
        File[] fetchData = FetchData.getInstance();
            for (File file : fetchData) {
                String absolutePath = file.getAbsolutePath();
                String extension = absolutePath.substring(absolutePath.lastIndexOf("."));
                if (extension.equals(".mp4")) {
                    mVideoPath.add(file.getAbsolutePath());
                    Log.i("TAG", "onCreateView: "+file.getAbsolutePath());
            }
        }
    }

    @Override
    public void onResume() {
        mVideoPath.clear();
        fecthData();
        ArrayList<String> newData = mVideoPath;
        mVideosFragmentRecyclerAdapter.setValues(newData);
        mVideosFragmentRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
}