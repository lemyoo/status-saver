package com.example.realestate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PhotoSlideShowViewPagerAdapter extends RecyclerView.Adapter<PhotoSlideShowViewPagerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImagePath;
    private LayoutInflater mLayoutInflater;

    public PhotoSlideShowViewPagerAdapter(Context context, ArrayList<String> imagePath) {
        mContext = context;
        mImagePath = imagePath;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.picture_slider_holder,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

       holder.mPhotoView.setImageURI(Uri.parse(mImagePath.get(position)));
       holder.mDownload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SaveImage(mImagePath.get(position));
               Snackbar.make(v,"Iamge saved",Snackbar.LENGTH_SHORT).show();
           }
       });

       holder.mShare.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               shareFile(mImagePath.get(position));
           }
       });

       holder.mGoBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(mContext, MainActivity.class);
               mContext.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mImagePath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        PhotoView mPhotoView;
        FloatingActionButton mGoBack, mShare, mDownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.photo_view_test);
            mGoBack = itemView.findViewById(R.id.go_back);
            mShare = itemView.findViewById(R.id.share);
            mDownload = itemView.findViewById(R.id.download);
        }
    }
private void SaveImage(String path) {
    Bitmap bitmap = BitmapFactory.decodeFile(path);

    String rootDirectory = Environment.getExternalStorageDirectory().toString();
    File statusSaverDirectory = new File(rootDirectory + "/dkStatusSaver");
    if (!statusSaverDirectory.exists()) {
        statusSaverDirectory.mkdirs();
        putImageIn(statusSaverDirectory, bitmap, path);
    } else {
        putImageIn(statusSaverDirectory, bitmap, path);
    }
}
 private void putImageIn(File directory, Bitmap bitmap,String path){
        String strPath = path.substring(path.lastIndexOf("/",path.length()));
        String fieName = strPath;
        File file = new File(directory,fieName);
        if(file.exists()){
            file.delete();
        }
        try {
            FileOutputStream out =new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
 }

    private void shareFile(String pathe) {
        Bitmap bitmap = BitmapFactory.decodeFile(pathe);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver()
                ,
                bitmap,"",null);
        Uri uri = Uri.parse(path);

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(shareIntent,"Share Image"));
    }

}
