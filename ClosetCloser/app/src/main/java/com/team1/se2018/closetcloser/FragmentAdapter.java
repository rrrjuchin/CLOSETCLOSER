package com.team1.se2018.closetcloser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public FragmentAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                Fragment1 Fragment1 = new Fragment1();
                return Fragment1;
            case 1:
                Fragment2 Fragment2 = new Fragment2();
                return Fragment2;
            case 2:
                Fragment3 Fragment3 = new Fragment3();
                return Fragment3;
            /**case 4:
                Fragment4 Fragment4 = new Fragment4();**/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}

