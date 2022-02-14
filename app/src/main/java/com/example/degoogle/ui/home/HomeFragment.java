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
import androidx.navigation.NavController;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
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
        stateRestoredCalledTimes ++;
    }

    public void fragmentChange() {
        findNavController(this).navigate(R.id.nav_to_app_info);
        Log.d(TAG, "fragmentChange: SUCCESS");
        findNavController(this).addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.app_info) {
                requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
//
                Log.d(TAG, "onDestinationChanged: success");
            } else {


                requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
//
                Log.d(TAG, "onDestinationChanged: failed");
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
        homeViewModel.getAllCategories().observe(getViewLifecycleOwner(), allCategories -> setCategoryRecycler(allCategories));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    //DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback//DivCallback
    //DivCallback
    private void getEverything() {
        ArrayList<CategoryChild> categoryChildren = new ArrayList<>();
        //TODO FIRESTORE IMPLEMENTATION

        //NavGraph
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.redd.it/qn7f9oqu7o501.jpg"));
        categoryChildren.add(new CategoryChild("test", "test", "https://i.imgur.com/ZcLLrkY.jpg"));


        ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
        allCategoriesMain.add(new AllCategories(categoryChildren, "test1"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test2"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test3"));
        allCategoriesMain.add(new AllCategories(categoryChildren, "test4"));

        setCategoryRecycler(allCategoriesMain);
    }

    private void setCategoryRecycler(ArrayList<AllCategories> allCategories) {
        allCategoriesMain.addAll(allCategories);

        binding.homeList.setAdapter(new MainRecyclerAdapter(this, requireContext(), mNames, mImageUrls, mCategoryTitles, mDescription, allCategoriesMain));
    }

    private void onScroll() {
        ArrayList<CategoryChild> categoryChildren1 = new ArrayList<>();
        categoryChildren1.add(new CategoryChild("test01", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test11", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test21", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test31", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        addData(list, categoryChildren1);
    }

    public void addData(ArrayList<AllCategories> list, ArrayList<CategoryChild> children) {
        list.clear();
        list.add(new AllCategories(children, "loaded " + count));
        count++;
        list.add(new AllCategories(children, "loaded " + count));
        allCategoriesMain.addAll(list);
        Objects.requireNonNull(binding.homeList.getAdapter()).notifyItemChanged(allCategoriesMain.size());
    }

    private void firebaseIntegration(){
    db.collection("categories")
            .document("messaging")
            .collection("apps")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document :
                                Objects.requireNonNull(task.getResult())) {
                            Log.d(TAG, "firebase complete");
                            CategoryChild children = new CategoryChild();
                            children = document.toObject(CategoryChild.class);
                            Log.d(TAG, "onComplete: " + children.getmNames());
                        }
                    } else {
                        Log.d(TAG, "onComplete: failed");

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
        }
    });

//        list.add(new AllCategories(db.collection("categories").document("Messaging Apps"), "Messaging Apps"));

    }
    private void getSome(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            //Background work here
            firebaseIntegration();


            handler.post(() -> {
                // pass to main thread
                Log.d(TAG, "getSome: handler:post");
//                    mText.setValue(buffer.toString())
            });
        });
    }

}