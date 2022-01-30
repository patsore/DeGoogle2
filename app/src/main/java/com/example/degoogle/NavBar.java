package com.example.degoogle;

import android.os.Bundle;
import android.util.Log;

import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;
import com.example.degoogle.retrofit.JsonPlaceholderApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.databinding.ActivityNavBarBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Deprecated()
public class NavBar extends AppCompatActivity {
    RecyclerView CategoryRecycler;
    MainRecyclerAdapter mainRecyclerAdapter;
    private ActivityNavBarBinding binding;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mCategoryTitles = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    private String TAG;
    ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
    ArrayList<CategoryChild> categoryChildren;




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


       // retrofit();

//        getEverything();
    }




    private void retrofit(){




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        Call<ArrayList<AllCategories>> call = jsonPlaceholderApi.allCats();
        call.enqueue(new Callback<ArrayList<AllCategories>>() {
            @Override
            public void onResponse(Call<ArrayList<AllCategories>> call, Response<ArrayList<AllCategories>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: SUCCESS");
                    return;
                }
                ArrayList<AllCategories> everything = response.body();

                for (AllCategories allCategories: everything) {

                    allCategoriesMain.add(new AllCategories(allCategories.getCategoryChildren(), allCategories.getCategoryTitle()));
                    setCategoryRecycler(allCategoriesMain);



                }


            }

            @Override
            public void onFailure(Call<ArrayList<AllCategories>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed");
            }
        });





    }

    private void getEverything(){











        ArrayList<CategoryChild> categoryChildren = new ArrayList<>();
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));


        ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
        allCategoriesMain.add(new AllCategories(categoryChildren, "test"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test2"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test2"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test2"));


        setCategoryRecycler(allCategoriesMain);



        //УСТАНОВКА ПРИЛОЖЕНИЯ
        // Retrofit Library for application installation
        //Animation should be done as last
        //
        // onscroll detect how many left ask for more
        //
        // make it one giant index.json
        //

    }






    private void getImages(){
        mCategoryTitles.add("Category 1");
        mCategoryTitles.add("Category 2");
        mCategoryTitles.add("Category 3");


        mDescription.add("Lorem Ipsum");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Havasu Falls");

        mDescription.add("Lorem Ipsum2");
        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mDescription.add("Lorem Ipsum3");
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mDescription.add("Lorem Ipsum4");
        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");

        mDescription.add("Lorem Ipsum5");
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mDescription.add("Lorem Ipsum6");
        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");

        mDescription.add("Lorem Ipsum7");
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mDescription.add("Lorem Ipsum8");
        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mDescription.add("Lorem Ipsum9");
        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

    }
    private void onScroll(ArrayList<AllCategories> allCategories){
        ArrayList<CategoryChild> categoryChildren1 = new ArrayList<>();
        categoryChildren1.add(new CategoryChild("test111", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        allCategories.add(new AllCategories(categoryChildren1, "loaded"));

    }


    private void setCategoryRecycler(ArrayList<AllCategories> allCategories){
        CategoryRecycler = findViewById(R.id.home_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, mNames, mImageUrls, mCategoryTitles, mDescription, allCategories);
        CategoryRecycler.setAdapter(mainRecyclerAdapter);
        int lastCompletelyVisibleItemPosition;

            CategoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                    if (layoutManager.findLastVisibleItemPosition() <= allCategories.size())
                    onScroll(allCategories);

                }
            });
        }





}