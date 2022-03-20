package com.example.degoogle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.adapter.MainRecyclerAdapter;
import com.example.degoogle.databinding.FragmentHomeBinding;
import com.example.degoogle.interfaces.FragmentChange;
import com.example.degoogle.model.CategoryChild;
import com.example.degoogle.ui.info.AppInfoFragment;
import com.example.degoogle.ui.info.JavaAppInfoFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Map;


public class HomeFragment extends Fragment implements FragmentChange {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObserver();
        initListeners();
    }


    public void fragmentChange(String appId) {
        Bundle args = new Bundle();
        args.putString("key", appId);
        BottomSheetDialogFragment appInfoFragment = new AppInfoFragment();
        appInfoFragment.setArguments(args);
        appInfoFragment.show(getParentFragmentManager(), "test");
//        HomeFragmentDirections.NavToAppInfo action = HomeFragmentDirections.navToAppInfo();
//        action.setId(appId);
//        findNavController(this).navigate(action);
//        Log.d(TAG, "fragmentChange: SUCCESS");
//        findNavController(this).addOnDestinationChangedListener((navController, navDestination, bundle) -> {
//            ((NavBar) requireActivity()).hideNavBar(navDestination);
//        });
    }

    //future-proofing
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void setCategoryRecycler(Map<String, ArrayList<CategoryChild>> categories) {
        binding.homeList.setAdapter(new MainRecyclerAdapter(this, requireContext(), categories));
    }

    private void onScroll() {
        //if I'm going to be implementing pagination
    }

}