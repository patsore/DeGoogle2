package com.example.degoogle.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.degoogle.R;
import java.util.ArrayList;




public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private ArrayList<String> mCategoryTitles = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();

    public MainRecyclerAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls,  ArrayList<String> titles, ArrayList<String> description) {
        mCategoryTitles = titles;
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
        mDescription = description;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_item,parent,false);
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
    holder.categoryTitles.setText(mCategoryTitles.get(position));
    setCatItemRecycler(holder.childRecycler, mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategoryTitles.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitles;
        RecyclerView childRecycler;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitles = itemView.findViewById(R.id.category_name);
            childRecycler = itemView.findViewById(R.id.child_recycler);
        }
    }



    private void  setCatItemRecycler(RecyclerView recyclerView, String categoryChildren ){
        ChildItemRecyclerAdapter childItemRecyclerAdapter = new ChildItemRecyclerAdapter(mContext, mNames, mImageUrls, mDescription);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(childItemRecyclerAdapter);
    }

}


