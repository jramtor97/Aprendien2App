package com.example.pc.aprendien2app.Tabs;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import android.support.v4.app.Fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                return new InicioFragment();

            case 1:
                return new InicioFragment();

            case 2:
                return new InicioFragment();

            case 3:
                return new InicioFragment();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}