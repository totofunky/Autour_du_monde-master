package com.example.thorin_lenain.autourdumonde;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.Vector;

/**
 * Created by Thorin_LeNain on 15/10/2014.
 */
public class MainActivity extends FragmentActivity{
    public MapsActivity fragmentMap = new MapsActivity();
    public Fragment_list fragment_list = new Fragment_list();
    public List fragments = new Vector();
    private ActionBar actionBar;
    private PagerAdapter mPagerAdapter;
    public static ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_my);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        instantier();

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String editDepart = "paris";//this.getIntent().getStringExtra("DEPART");
            final String editArrivee = "marseille";//this.getIntent().getStringExtra("ARRIVEE");
            new ItineraireTask(this, MapsActivity.mMap, editDepart, editArrivee).execute();
                pager.setCurrentItem(1);
        }*/

    }

    public void instantier() {

        fragments.add(fragment_list);
        fragments.add(fragmentMap);

        this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

        pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(this.mPagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ActionBar.Tab tab1 = actionBar.newTab();
        ActionBar.Tab tab2 = actionBar.newTab();

        tab1.setText("List");
        tab2.setText("Map");
        tab1.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        });
        tab2.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        });

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }
}
