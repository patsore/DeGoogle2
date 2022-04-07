package com.example.degoogle.ui.updates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.degoogle.databinding.FragmentUpdatesBinding;

import kotlin.reflect.KFunction;

public class UpdatesFragment extends Fragment {

    private UpdatesViewModel updatesViewModel;
    private FragmentUpdatesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        updatesViewModel =
                new ViewModelProvider(this).get(UpdatesViewModel.class);

        binding = FragmentUpdatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
