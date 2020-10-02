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

public class PhotosFragment extends Fragment {

    private ArrayList<String> mImagePath = new ArrayList<>();
    PhotosFragmentRecyclerAdapter mPhotosFragmentRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_photos, container,false);

        fetchData();

        mPhotosFragmentRecyclerAdapter = new PhotosFragmentRecyclerAdapter(root.getContext(),mImagePath);
        RecyclerView photosRecyclerView = root.findViewById(R.id.photo_lists);
        GridLayoutManager mPhotosGridLayoutManager = new GridLayoutManager(getContext(),2);
        photosRecyclerView.setLayoutManager(mPhotosGridLayoutManager);
        photosRecyclerView.setAdapter(mPhotosFragmentRecyclerAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mImagePath.clear();
                        fetchData();
                        ArrayList<String> newData = mImagePath;
                        ((PhotosFragmentRecyclerAdapter) mPhotosFragmentRecyclerAdapter).setValues(mImagePath);
                        mPhotosFragmentRecyclerAdapter.notifyDataSetChanged();
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

    private void fetchData() {
        File[] fecthData = FetchData.getInstance();
        for(File file : fecthData){
            String absolutePath = file.getAbsolutePath();
            String extension = absolutePath.substring(absolutePath.lastIndexOf("."));
            if(extension.equals(".jpg")){
                mImagePath.add(file.getAbsolutePath());
            }
        }
    }


    @Override
    public void onResume() {
        mImagePath.clear();
        fetchData();
        ArrayList<String> newData = mImagePath;
        ((PhotosFragmentRecyclerAdapter) mPhotosFragmentRecyclerAdapter).setValues(newData);
        mPhotosFragmentRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
