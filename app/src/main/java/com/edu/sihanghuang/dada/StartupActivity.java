package com.edu.sihanghuang.dada;

import android.app.ActionBar;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class StartupActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LifestyleFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener, LocationListener {

    private final int SideMenuRowAbout = 1;
    private final int SideMenuRowRate = 2;
    private final int SideMenuRowFeedback = 3;
    private final int SideMenuRowShare = 4;
    private final int SideMenuRowTermsAndPrivay = 5;
    private final int SideMenuRowLogOut = 6;
    private final int LocationManagerUpdateMinTime = 1000;
    private final int LocationManagerUpdateDistance = 1852;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager mViewPager;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private final String[] tabNames = {"Lifestyle", "Events", "Surprise"};

    private LocationManager mLocationManager;
    private String mProvider;
    private Location mLastLocation;
    // Set up

    private void setUpViewPager() {
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
    }

    private void setUpNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void setUpTabs() {
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
        for (int i = 0; i < tabNames.length; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabNames[i])
                            .setTabListener(tabListener));
        }
    }

    private void setUpLocationManager() {
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the locatioin provider -> use default
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        mLastLocation = mLocationManager.getLastKnownLocation(mProvider);

        if (mLastLocation != null) {
            onLocationChanged(mLastLocation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);
        mTitle = getTitle();
        setUpNavigationDrawer();
        setUpViewPager();
        setUpNavigationDrawer();
        setUpTabs();
        setUpLocationManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationManager.requestLocationUpdates(mProvider, LocationManagerUpdateMinTime, LocationManagerUpdateDistance, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    // Listener

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        Log.i("latitude", String.valueOf(lat));
        Log.i("longitude", String.valueOf(lng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                break;
            case LocationProvider.AVAILABLE:
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;
            default:
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
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

    @Override
    public void onEventFragmentInteraction(String id) {
        // Must implement this method for EventFragment
    }

    public static class TabAdapter extends FragmentPagerAdapter {

        private final int pageCount = 2;

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                LifestyleFragment lifestyle = LifestyleFragment.newInstance("a", "b");

                return lifestyle;
            } else {
                EventFragment eventFragment1 = EventFragment.newInstance("a", "b");

                return eventFragment1;
            }
        }

        @Override
        public int getCount() {

            return pageCount;
        }

    }
}
