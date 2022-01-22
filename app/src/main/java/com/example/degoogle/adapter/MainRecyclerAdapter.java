package com.example.degoogle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.MainActivity;
import com.example.degoogle.R;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<AllCategories> allCategoriesList;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
    holder.categoryTitle.setText(allCategoriesList.get(position).getCategoryTitle());
    setCatItemRecycler(holder.childRecycler, allCategoriesList.get(position).getCategoryChildList());
    }

    @Override
    public int getItemCount() {
        return allCategoriesList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;
        RecyclerView childRecycler;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_name);
            childRecycler = itemView.findViewById(R.id.child_recycler);
        }
    }

    public MainRecyclerAdapter(Context context, List<AllCategories> allCategoriesList) {
        this.context = context;
        this.allCategoriesList = allCategoriesList;
    }
    private void  setCatItemRecycler(RecyclerView recyclerView, List<CategoryChild> categoryChildren ){
        ChildItemRecyclerAdapter childItemRecyclerAdapter = new ChildItemRecyclerAdapter(context, categoryChildren);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(childItemRecyclerAdapter);
    }

}


