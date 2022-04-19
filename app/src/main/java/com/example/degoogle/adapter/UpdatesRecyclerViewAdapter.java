package com.example.degoogle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degoogle.R;
import com.example.degoogle.interfaces.UpdateButtonCallback;
import com.example.degoogle.model.Product;

import java.util.ArrayList;

public class UpdatesRecyclerViewAdapter extends RecyclerView.Adapter<UpdatesRecyclerViewAdapter.UpdatesRecyclerViewHolder>{
    ArrayList<Product> updatedProducts = new ArrayList<>();
    Context context;
    UpdateButtonCallback updateButtonCallback;
    public UpdatesRecyclerViewAdapter(){

    }

    public UpdatesRecyclerViewAdapter(Context context , ArrayList<Product>  updatedProducts, UpdateButtonCallback updateButtonCallback){
        this.updatedProducts = updatedProducts;
        this.context = context;
        this.updateButtonCallback = updateButtonCallback;
    }

    @NonNull
    @Override
    public UpdatesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpdatesRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.updates_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpdatesRecyclerViewHolder holder, int position) {
        Glide.with(context).load(updatedProducts.get(position).getIcon()).into(holder.icon);
        holder.name.setText(updatedProducts.get(position).getName());
        holder.updateButton.setOnClickListener(v -> {
        updateButtonCallback.onUpdateButtonClicked(updatedProducts.get(position).getPackageName());
        });

    }

    @Override
    public int getItemCount() {
        return updatedProducts.size();
    }


    public class UpdatesRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        Button updateButton;
        public UpdatesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            updateButton = itemView.findViewById(R.id.update_button);
            icon = itemView.findViewById(R.id.update_icon);
            name = itemView.findViewById(R.id.update_title);
        }
    }
}
