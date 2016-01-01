package com.birdlabs.mhrd;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.birdlabs.mhrd.fragments.InstitutesFragment;
import com.birdlabs.mhrd.fragments.NewsFeedFragment;
import com.birdlabs.mhrd.fragments.ProfileFragment;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.views.ToolbarView;


public class MainActivity extends TabbedActivity {

    public static final Integer PICK_IMAGE_REQUEST = 1000;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPager();
        setupToolbar();
        startupAccess();

    }

    public void startupAccess() {
        Access access = Access.getInstance(this);
        access.getData(new AccessItem(
                Api.getCurrentUserProfile(),
                Functions.FILENAME_USER_INFO,
                AccessItem.CURRENT_USER, true), null);
        access.getData(new AccessItem(
                Api.getAllTags(),
                Functions.FILENAME_TAG_LIST,
                AccessItem.TAGS, true), null);
    }

    public void setupToolbar() {
        ToolbarView toolbar = new ToolbarView(findViewById(R.id.toolbar_layout));
        toolbar.title.setText(R.string.app_name);
        toolbar.icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        toolbar.icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        toolbar.icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
    }

    public void setupPager() {
        String[] pageTitles = getResources().getStringArray(R.array.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new NewsFeedFragment(), pageTitles[0]);
        pagerAdapter.addFragment(new InstitutesFragment(), pageTitles[1]);
        pagerAdapter.addFragment(new ProfileFragment(), pageTitles[2]);

        viewPager.setAdapter(pagerAdapter);
    }
}
