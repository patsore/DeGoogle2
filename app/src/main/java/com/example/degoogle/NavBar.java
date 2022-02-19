package com.example.degoogle;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.degoogle.databinding.ActivityNavBarBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class NavBar extends AppCompatActivity {

    private ActivityNavBarBinding binding;
    private String TAG;


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


    }

    public void hideNavBar(NavDestination destination) {

           if (destination.getId() == R.id.app_info) {
                binding.navView.setVisibility(View.GONE);
            } else {
                binding.navView.setVisibility(View.VISIBLE);
            }


    }


    //УСТАНОВКА ПРИЛОЖЕНИЯ
    //TODO Retrofit Library for application installation
    //Animation should be done as last
    //
    // onscroll detect how many left ask for more
    //
    // make it one giant index.json
    //Разделение слоя Data и UI
    //Писать Data слой полностью отдельно
}