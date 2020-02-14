package com.example.taskmanagement.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskmanagement.businessorder.fragment.ReceiveFragment;
import com.example.taskmanagement.businessorder.fragment.SendFragment;

public class FragmentAdapter extends FragmentPagerAdapter {


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();


        switch (position) {
            case 0:
                return ReceiveFragment.newInstance();
            case 1:
                return SendFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
