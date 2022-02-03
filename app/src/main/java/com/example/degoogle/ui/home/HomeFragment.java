package com.example.degoogle.ui.home;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.R;
import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.databinding.FragmentHomeBinding;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements FragmentChange {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mCategoryTitles = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    ArrayList<AllCategories> allCategoriesMain = new ArrayList<>();
    ArrayList<CategoryChild> categoryChildren;
    ArrayList<AllCategories> list = new ArrayList<>();
    Activity activity = getActivity();
    NavController navController = null;


    int count = 0;
    FragmentChange fragmentChanger;



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
        getEverything();
        initObserver();
        initListeners();
        setCategoryRecycler(list);

//TODO change emulator start settings

    }








        public void fragmentChange() {
//            if (activity != null){

//                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_navigation_updates);
//                navController.navigate(R.id.action_navigation_home_to_navigation_updates);


//            }

//
//            Toast toast = Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT);
//            Log.d(TAG, "fragmentChange: SUCCESS0");
//            toast.show()
            String TAG = "HomeFragment";
            Log.d(TAG, "fragmentChange: SUCCESS");



        }



    private void initListeners() {
        binding.homeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    count++;
                    onScroll();
                    int pos = (Objects.requireNonNull(binding.homeList.getAdapter()).getItemCount()!=0 &&binding.homeList.getAdapter().getItemCount()-3>0)?
                            binding.homeList.getAdapter().getItemCount()-3:1;
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
        //

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

        binding.homeList.setAdapter(new MainRecyclerAdapter(requireContext(), mNames, mImageUrls, mCategoryTitles, mDescription, allCategoriesMain));
    }

    private void onScroll(){
        ArrayList<CategoryChild> categoryChildren1 = new ArrayList<>();
        categoryChildren1.add(new CategoryChild("test01", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test11", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test21", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test31", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        categoryChildren1.add(new CategoryChild("test", "testsssss", "https://i.imgur.com/ZcLLrkY.jpg"));
        addData(list, categoryChildren1);
        }

        public void addData(ArrayList<AllCategories> list, ArrayList<CategoryChild> children){
            list.clear();
            list.add(new AllCategories(children, "loaded " + count));
            count ++;
            list.add(new AllCategories(children, "loaded " + count));
            allCategoriesMain.addAll(list);
            Objects.requireNonNull(binding.homeList.getAdapter()).notifyItemChanged(allCategoriesMain.size());
        }




}