package com.example.degoogle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.R;

import java.util.ArrayList;

public class TagItemRecyclerAdapter extends RecyclerView.Adapter<TagItemRecyclerAdapter.TagItemViewHolder> {


    private ArrayList<String> mTags = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public TagItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);

        return new TagItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class TagItemViewHolder extends RecyclerView.ViewHolder{
        TextView tag;

        public TagItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag_name);
        }
    }
}
