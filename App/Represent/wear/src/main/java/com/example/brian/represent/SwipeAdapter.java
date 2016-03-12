package com.example.brian.represent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Brian on 3/1/16.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {

    private String[] str;
    public SwipeAdapter(FragmentManager fm, String[] str) {
        super(fm);
        this.str = str;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("info", str);
        bundle.putInt("count",position+1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
