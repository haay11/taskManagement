package com.example.taskmanagement.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskmanagement.processingstatus.fragment.ProcessingStatusEndFragment;
import com.example.taskmanagement.processingstatus.fragment.ProcessingStatusFragment;


public class ProcessingFragmentAdapter extends FragmentPagerAdapter {


    public ProcessingFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public ProcessingFragmentAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                return ProcessingStatusEndFragment.newInstance();
            case 1:
                return ProcessingStatusFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {

        return 2;
    }
}
