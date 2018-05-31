package com.zeus.ali.matchday.SlidingTab;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zeus.ali.matchday.News.NewsTab;
import com.zeus.ali.matchday.Fixture.FixtureTab;
import com.zeus.ali.matchday.WebTab;

/**
 * Created by Ali on 1/4/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FixtureTab();
            case 1:
                return new NewsTab();
            case 2:
                return new WebTab();
        }

        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
