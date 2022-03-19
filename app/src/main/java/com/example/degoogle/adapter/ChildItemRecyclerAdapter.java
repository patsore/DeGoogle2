package com.example.degoogle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.degoogle.R;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;

public class ChildItemRecyclerAdapter extends RecyclerView.Adapter<ChildItemRecyclerAdapter.ChildItemViewHolder> {
    private Context mContext;
    public ImageView childImage;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    public ArrayList<String> mDescription = new ArrayList<>();
    public ArrayList<CategoryChild> categoryChildren;
    public String TAG = "ChildItemRecyclerAdapter";
    public int ItemPosition;

    FragmentChange mCallback;


    public ChildItemRecyclerAdapter(FragmentChange callback, Context mContext, ArrayList<CategoryChild> categoryChildren) {
        this.mContext = mContext;
          this.categoryChildren = categoryChildren;
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public ChildItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChildItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.child_row_item, parent, false));

    }


    @Override
    public void onBindViewHolder(@NonNull ChildItemViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(mContext).asBitmap().load(categoryChildren.get(position).getIcon()).into(holder.childImage);


        holder.appName.setText(categoryChildren.get(position).getName());
        holder.description.setText(categoryChildren.get(position).getDescription());

        holder.itemView.setOnClickListener(view -> {
            //`TODO ID system
            mCallback.fragmentChange((String) holder.appName.getText());
            //Just get the name of the app I clicked through holder.appName.getText();

            Log.d(TAG, "click: SUCCESS" + position + " " + holder.appName.getText());
        });
    }






    @Override
    public int getItemCount() {
        return categoryChildren.size();
    }

    public static final class ChildItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView childImage;
        public TextView appName;
        public TextView description;
        public ChildItemViewHolder(@NonNull View itemView) {
            super(itemView);
            childImage = itemView.findViewById(R.id.child_image);
            appName = itemView.findViewById(R.id.app_title);
            description = itemView.findViewById(R.id.app_description);
        }
    }

}
