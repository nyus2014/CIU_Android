package com.edu.sihanghuang.dada;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class StartupActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LifestyleFragment.OnFragmentInteractionListener {

    private final int SideMenuRowAbout = 1;
    private final int SideMenuRowRate = 2;
    private final int SideMenuRowFeedback = 3;
    private final int SideMenuRowShare = 4;
    private final int SideMenuRowTermsAndPrivay = 5;
    private final int SideMenuRowLogOut = 6;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager mViewPager;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Set up tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case SideMenuRowAbout:
                mTitle = getString(R.string.sideMenuAbout);
                break;
            case SideMenuRowRate:
                mTitle = getString(R.string.sideMenuRate);
                break;
            case SideMenuRowFeedback:
                mTitle = getString(R.string.sideMenuFeedback);
                break;
            case SideMenuRowShare:
                mTitle = getString(R.string.sideMenuShare);
            case SideMenuRowTermsAndPrivay:
                mTitle = getString(R.string.sideMenuTermsPrivacy);
            case SideMenuRowLogOut:
                mTitle = getString(R.string.sideMenuLogout);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            Only show items in the action bar relevant to this screen
//            if the drawer is not showing. Otherwise, let the drawer
//            decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.startup, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLifestyleFragmentInteraction(String id) {
        // Must implement this method for LifestyleFragment
    }

    public static class TabAdapter extends FragmentPagerAdapter {

        private final int pageCount = 1;

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            LifestyleFragment lifestyle = LifestyleFragment.newInstance("a", "b");

            return lifestyle;
        }

        @Override
        public int getCount() {

            return pageCount;
        }

    }
}
