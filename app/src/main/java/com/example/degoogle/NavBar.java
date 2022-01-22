package com.example.degoogle;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.databinding.ActivityNavBarBinding;

import java.util.ArrayList;
import java.util.List;

public class NavBar extends AppCompatActivity {

    RecyclerView CategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;
    private ActivityNavBarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityNavBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_updates)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_nav_bar);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //add dummy categories
        List<CategoryChild> categoryChildren = new ArrayList<>();
        categoryChildren.add(new CategoryChild(1, "source.unsplash.com/random"));


        List<AllCategories> allCategoriesList = new ArrayList<>();
        allCategoriesList.add(new AllCategories("Category 1", categoryChildren));
        allCategoriesList.add(new AllCategories("Category 2", categoryChildren));
        allCategoriesList.add(new AllCategories("Category 3", categoryChildren));

        setCategoryRecycler(allCategoriesList);

    }


    private void setCategoryRecycler(List<AllCategories> allCategoriesList){
        CategoryRecycler = findViewById(R.id.recycler_view_home);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoriesList);
        CategoryRecycler.setAdapter(mainRecyclerAdapter);
    }


}