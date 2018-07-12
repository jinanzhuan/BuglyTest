package com.ljn.buglytest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ljn.buglytest.fragment.AboutMeFragment;
import com.ljn.buglytest.fragment.HomeFragment;
import com.ljn.buglytest.fragment.MessageFragment;


/**
 * Created by shuwei on 2018/6/11.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
            return new HomeFragment();
            case 1 :
            return new MessageFragment();
            case 2 :
            return new AboutMeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
