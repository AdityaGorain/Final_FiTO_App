package com.example.fito.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fito.ui.dashboard.Chart_MonthlyFragment;
import com.example.fito.ui.dashboard.Chart_WeeklyFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    int pos=0;
    private final String[] tabTitles = new String[] { "Weekly", "Monthly"};

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new Chart_WeeklyFragment(pos);  break;
            case 1: fragment = new Chart_MonthlyFragment(pos);   break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public void getCardId(int pos){this.pos = pos;}
}
