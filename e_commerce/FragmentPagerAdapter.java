package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    public FragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new CameraFragment();

            case 1:
                return new VoiceFragment();

            case 2:
                return new TextFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Camera";

            case 1:
                return "Voice";

            case 2:
                return "Text";
        }

        return super.getPageTitle(position);
    }
}
