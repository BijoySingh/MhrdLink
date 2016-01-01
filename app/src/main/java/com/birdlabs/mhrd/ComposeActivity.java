package com.birdlabs.mhrd;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.birdlabs.mhrd.fragments.ComposeFragment;
import com.birdlabs.mhrd.fragments.InstituteFragment;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.views.ToolbarView;


public class ComposeActivity extends TabbedActivity {

    public static final String COMPOSE_ACTIVITY = "COMPOSE_ACTIVITY";
    public NewsFeedItem item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            item = (NewsFeedItem) getIntent().getSerializableExtra(COMPOSE_ACTIVITY);
        } catch (Exception e) {
            item = null;
            Log.e(ComposeActivity.class.getSimpleName(), e.getMessage(), e);
        }

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
        pagerAdapter.addFragment(new ComposeFragment(), pageTitles[0]);

        viewPager.setAdapter(pagerAdapter);
    }

}
