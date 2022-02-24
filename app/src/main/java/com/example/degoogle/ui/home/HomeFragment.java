package com.example.degoogle.ui.home;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.NavBar;
import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.databinding.FragmentHomeBinding;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment implements FragmentChange {
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    ArrayList<AllCategories> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String,ArrayList<CategoryChild>> allCategoriesMain = new HashMap<>();

    Map<Integer,ArrayList<CategoryChild>> apps = new HashMap<>();
    String category = "";
    ArrayList<CategoryChild> messaging = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObserver();
        initListeners();
        getSome();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void fragmentChange(int appId) {
        HomeFragmentDirections.NavToAppInfo action = HomeFragmentDirections.navToAppInfo();
        action.setId(appId);
        findNavController(this).navigate(action);
        Log.d(TAG, "fragmentChange: SUCCESS");
        findNavController(this).addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            ((NavBar) requireActivity()).hideNavBar(navDestination);
        });


    }


    private void initListeners() {
        binding.homeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {

                    onScroll();
                }
            }
        });

    }

    private void initObserver() {
        homeViewModel.getFirebaseData().observe(getViewLifecycleOwner(), this::setCategoryRecycler);

        Log.d(TAG, "initObserver: observerserserserserse" + homeViewModel.getFirebaseData());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    //DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback
    //DivCallback

    private void setCategoryRecycler(Map<String,ArrayList<CategoryChild>> categories) {

        binding.homeList.setAdapter(new MainRecyclerAdapter(this, requireContext(), mNames, mImageUrls, mDescription, categories));
    }


    //method to update list when scrolling
    private void onScroll() {



//        addData();

    }


    //adds data to recyclerview
    public void addData() {
        //I need to check if my downloaded category title corresponds with the categories title, and only load that item into it in case it does


//        list.add(new AllCategories(categories.get(category), category));
//        allCategoriesMain.addAll(list);
        apps.clear();
        Objects.requireNonNull(binding.homeList.getAdapter()).notifyItemChanged(allCategoriesMain.size());
    }



//
////        list.add(new AllCategories(db.collection("categories").document("Messaging Apps"), "Messaging Apps"));
//
//    }

    private void getSome(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            //Background work here
//TODO collection name == id

            handler.post(() -> {
                // pass to main thread
                Log.d(TAG, "getSome: handler:post");
//                    mText.setValue(buffer.toString())
            });
        });
    }
}