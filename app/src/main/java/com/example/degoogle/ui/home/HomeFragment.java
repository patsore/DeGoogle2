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
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.R;
import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.databinding.FragmentHomeBinding;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment implements FragmentChange {
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mCategoryTitles = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
    ArrayList<CategoryChild> categoryChildren;
    ArrayList<AllCategories> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int count = 0;
    int stateRestoredCalledTimes = 0;
    int categoryId;
    ArrayList<CategoryChild> messaging = new ArrayList<>();
    String title;
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
        getEverything();

        getSome();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: stateRESTROED");
        stateRestoredCalledTimes++;
    }

    public void fragmentChange(int appId) {
        int id = appId;
        HomeFragmentDirections.NavToAppInfo action = HomeFragmentDirections.navToAppInfo();
        action.setId(id);
        findNavController(this).navigate(action);
        Log.d(TAG, "fragmentChange: SUCCESS");


        findNavController(this).addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.app_info) {
                requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
//
                Log.d(TAG, "onDestinationChanged: success " + id);
            } else {


                requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
//
                Log.d(TAG, "onDestinationChanged: failed " + id);
            }
        });

    }


    private void initListeners() {
        binding.homeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    count++;
                    onScroll();
                    //
                }
            }
        });

    }

    private void initObserver() {
        homeViewModel.getAllCategories().observe(getViewLifecycleOwner(), this::setCategoryRecycler);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    //DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback
    //DivCallback
    private void getEverything() {
        //NavGraph
        ArrayList<CategoryChild> categoryChildren = new ArrayList<>();
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.imgur.com/ZcLLrkY.jpg"));
        ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
        allCategoriesMain.add(new AllCategories(categoryChildren, "test"));


        setCategoryRecycler(allCategoriesMain);
    }

    private void setCategoryRecycler(ArrayList<AllCategories> allCategories) {
        allCategoriesMain.addAll(allCategories);

        binding.homeList.setAdapter(new MainRecyclerAdapter(this, requireContext(), mNames, mImageUrls, mDescription, allCategoriesMain));
    }


    //method to update list when scrolling
    private void onScroll() {



        firebaseIntegration();
        addData(list, messaging);
    }


    //adds data to recyclerview
    public void addData(ArrayList<AllCategories> list, ArrayList<CategoryChild> children) {
//        list.clear();
        //I need to check if my downloaded category title corresponds with the categories title, and only load that item into it in case it does

//        list.add(new AllCategories(children, "loaded " + count));
        count ++;

        allCategoriesMain.addAll(list);
        Objects.requireNonNull(binding.homeList.getAdapter()).notifyItemChanged(allCategoriesMain.size());
    }



    private void firebaseIntegration() {

        db.collection("messaging")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.getLong("category") != null){
                                long categoryId1 = document.getLong("category");
                                categoryId = Math.toIntExact(categoryId1);
                                Log.d(TAG, "onComplete: " + categoryId1);
                                if (categoryId == categoryId1){
                                    list.add(0 ,new AllCategories(messaging, "loaded" + categoryId));
                                    list.add(1 ,new AllCategories(messaging, "loaded" + categoryId));
                                    list.add(2 ,new AllCategories(messaging, "loaded" + categoryId));
                                    list.add(3 ,new AllCategories(messaging, "loaded" + categoryId));
                                    list.get(categoryId).getCategoryChildren().add(new CategoryChild(document.getString("description"), document.getString("name"), document.getString("icon")));


                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: getting firebase failed");
                
                
            }
        });


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