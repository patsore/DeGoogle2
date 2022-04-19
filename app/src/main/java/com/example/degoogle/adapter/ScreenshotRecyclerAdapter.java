package com.example.degoogle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degoogle.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ScreenshotRecyclerAdapter extends RecyclerView.Adapter<ScreenshotRecyclerAdapter.ScreenshotViewHolder>{
    ArrayList<String> screenshotList;
    Context context;
    public ScreenshotRecyclerAdapter(){


    }
    public ScreenshotRecyclerAdapter(Context context, ArrayList<String> screenshotList) {
        this.screenshotList = screenshotList;
        this.context = context;
    }


    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScreenshotViewHolder(LayoutInflater.from(context).inflate(R.layout.screenshot_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        Glide.with(context).load(screenshotList.get(position)).into(holder.screenshotView);
    }

    @Override
    public int getItemCount() {
        return screenshotList.size();
    }

    public class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView screenshotView;
        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            screenshotView = itemView.findViewById(R.id.screenshot_view);
        }
    }
}
