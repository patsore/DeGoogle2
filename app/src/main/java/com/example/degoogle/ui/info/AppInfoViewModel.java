package com.example.degoogle.ui.info;

import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.Objects;

public class AppInfoViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();


}
