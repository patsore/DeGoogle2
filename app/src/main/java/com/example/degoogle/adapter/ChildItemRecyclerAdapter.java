package com.example.degoogle.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degoogle.NavBar;
import com.example.degoogle.R;
import com.example.degoogle.model.CategoryChild;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChildItemRecyclerAdapter extends RecyclerView.Adapter<ChildItemRecyclerAdapter.ChildItemViewHolder> {
    private Context mContext;
    public ImageView childImage;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    public ChildItemRecyclerAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> mImageUrls) {
        this.mContext = mContext;
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
    }

    @NonNull
    @Override
    public ChildItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChildItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.child_row_item, parent, false));

    }



    @Override
    public void onBindViewHolder(@NonNull ChildItemViewHolder holder, int position) {

        Glide.with(mContext).asBitmap().load(mImageUrls.get(position)).into(holder.childImage);

        holder.appName.setText(mNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public static final class ChildItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView childImage;
        public TextView appName;

        public ChildItemViewHolder(@NonNull View itemView) {
            super(itemView);
            childImage = itemView.findViewById(R.id.child_image);
            appName = itemView.findViewById(R.id.app_title);

        }
    }

}
