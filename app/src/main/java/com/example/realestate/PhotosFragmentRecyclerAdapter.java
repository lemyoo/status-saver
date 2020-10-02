package com.example.realestate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosFragmentRecyclerAdapter extends RecyclerView.Adapter<PhotosFragmentRecyclerAdapter.ViewHolder>{

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    ArrayList<String> mPicUrl;

    public PhotosFragmentRecyclerAdapter(Context context,ArrayList<String> picUrl) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mPicUrl = picUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.photo,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(("file:///"+mPicUrl.get(position)))
                .config(Bitmap.Config.RGB_565).
                into(holder.mStatusImage);
        holder.mPosition = position;
        holder.path = mPicUrl.get(position);
    }

    @Override
    public int getItemCount() {
        return mPicUrl.size();
    }

    public void setValues(ArrayList<String> newData) {
        this.mPicUrl = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mStatusImage;
        int mPosition;
        String path;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mStatusImage = itemView.findViewById(R.id.status_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PictureSlideShowActivity.class);
                    intent.putExtra(PictureSlideShowActivity.POSITION,mPosition);
                    intent.putExtra(PictureSlideShowActivity.PICTURE,mPicUrl);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
