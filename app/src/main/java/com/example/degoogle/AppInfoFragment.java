package com.example.degoogle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AppInfoFragment extends Fragment {

    private static final String TAG = "adasd";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentAppInfoBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppInfoBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppInfoFragmentArgs id = AppInfoFragmentArgs.fromBundle(getArguments());
        int appId = id.getId();
        firebaseIntegration(appId);
        Log.d(TAG, "onViewCreated: " + appId);

    }

    private void firebaseIntegration(int id){
        db.collection("messaging")
                .document(String.valueOf(id + 1))
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "firebaseIntegration1: " + Objects.requireNonNull(task.getResult()));
                    assert document != null;
                    binding.appName.setText(document.getString("name"));
                    Glide.with(requireContext()).asBitmap().load(document.getString("icon")).into(binding.appIcon);
                    binding.appDescription.setText(document.getString("description"));

                }).addOnFailureListener(e -> {

        });



    }




}