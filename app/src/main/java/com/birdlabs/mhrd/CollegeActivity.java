package com.birdlabs.mhrd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.birdlabs.mhrd.fragments.InstituteFragment;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.views.ToolbarView;

import java.util.ArrayList;
import java.util.List;


public class CollegeActivity extends TabbedActivity {

    public static final String INSTITUTE_FRAGMENT = "INSTITUTE_FRAGMENT";
    public CollegeItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = (CollegeItem) getIntent().getExtras().getSerializable(INSTITUTE_FRAGMENT);

        setupPager();
        setupToolbar();

    }

    public void setupToolbar() {
        ToolbarView toolbar = new ToolbarView(findViewById(R.id.toolbar_layout));
        toolbar.title.setText(R.string.app_name);
        toolbar.icon1.setVisibility(View.GONE);
        toolbar.icon2.setVisibility(View.GONE);
        toolbar.icon3.setVisibility(View.GONE);
    }

    public void setupPager() {
        String[] pageTitles = getResources().getStringArray(R.array.content_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new InstituteFragment(), pageTitles[0]);

        viewPager.setAdapter(pagerAdapter);
    }

}
