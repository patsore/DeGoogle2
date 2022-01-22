package com.example.degoogle.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degoogle.NavBar;
import com.example.degoogle.R;
import com.example.degoogle.model.CategoryChild;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChildItemRecyclerAdapter extends RecyclerView.Adapter<ChildItemRecyclerAdapter.ChildItemViewHolder> {
    private Context mContext;
    public List<CategoryChild> categoryChildren;
    public ChildItemRecyclerAdapter(Context context, List<CategoryChild> categoryChildren) {
        this.mContext = context;
        this.categoryChildren = categoryChildren;
    }


    @NonNull
    @Override
    public ChildItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChildItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.child_row_item, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull ChildItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryChildren.size();
    }

    public static final class ChildItemViewHolder extends RecyclerView.ViewHolder{

        ImageView childImage;


        public ChildItemViewHolder(@NonNull View itemView) {
            super(itemView);
            childImage = itemView.findViewById(R.id.child_image);
        }
    }

}
