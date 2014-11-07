package com.example.thorin_lenain.autourdumonde;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Thorin_LeNain on 15/10/2014.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private final List fragments;

    public MyPagerAdapter(FragmentManager fm, List fragments){
        super(fm);
        this.fragments =fragments;
    }

    public Fragment getItem(int position){
        return (Fragment)this.fragments.get(position);
    }
    public int getCount(){
        return this.fragments.size();
    }
}
