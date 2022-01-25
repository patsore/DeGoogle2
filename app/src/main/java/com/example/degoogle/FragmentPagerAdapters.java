package com.example.degoogle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.degoogle.ui.home.HomeFragment;


public class FragmentPagerAdapters extends androidx.fragment.app.FragmentPagerAdapter {


    int tabcount;

    public FragmentPagerAdapters(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0: return new HomeFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
