package com.example.degoogle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.R;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;
import java.util.Map;


public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private static final String TAG = "MainRecyclerAdapter";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    private ArrayList<String> mDescription = new ArrayList<>();
    private Map<String,ArrayList<CategoryChild>> allCategories;
    FragmentChange fragmentChange;
    ArrayList<String> categories;

    public MainRecyclerAdapter(FragmentChange callback, Context context, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> description, Map<String,ArrayList<CategoryChild>> allCategories) {

        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
        mDescription = description;
        this.allCategories = allCategories;
        this.fragmentChange = callback;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_item, parent, false);
        categories = new ArrayList<>(allCategories.keySet());
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + allCategories.keySet());

        holder.categoryTitles.setText(categories.get(position));
        setCatItemRecycler(holder.childRecycler, allCategories.get(categories.get(position)));



    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitles;
        RecyclerView childRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitles = itemView.findViewById(R.id.category_name);
            childRecycler = itemView.findViewById(R.id.child_recycler);
        }
    }


    private void setCatItemRecycler(RecyclerView recyclerView, ArrayList<CategoryChild> categoryChildren) {
        ChildItemRecyclerAdapter childItemRecyclerAdapter = new ChildItemRecyclerAdapter(fragmentChange, mContext, mNames, mImageUrls, mDescription, categoryChildren);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(childItemRecyclerAdapter);
    }

}


