package com.birdlabs.mhrd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.birdlabs.mhrd.fragments.CommentsFragment;
import com.birdlabs.mhrd.fragments.ContentFragment;
import com.birdlabs.mhrd.fragments.InstituteFragment;
import com.birdlabs.mhrd.fragments.NewsFeedFragment;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.views.ToolbarView;

import java.util.ArrayList;
import java.util.List;


public class ContentActivity extends TabbedActivity {

    public static final String NEWS_FEED_ITEM = "NEWS_FEED_ITEM";
    public NewsFeedItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = (NewsFeedItem) getIntent().getExtras().getSerializable(NEWS_FEED_ITEM);

        setupPager();
        setupToolbar();

    }

    public void setupToolbar() {
        ToolbarView toolbar = new ToolbarView(findViewById(R.id.toolbar_layout));
        toolbar.title.setText(R.string.app_name);
        toolbar.icon1.setVisibility(View.GONE);
        toolbar.icon2.setImageResource(R.drawable.ic_receipt_white_48dp);
        toolbar.icon3.setImageResource(R.drawable.ic_comment_white_48dp);

        toolbar.icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        toolbar.icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    public void setupPager() {
        String[] pageTitles = getResources().getStringArray(R.array.content_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ContentFragment(), pageTitles[0]);
        pagerAdapter.addFragment(new CommentsFragment(), pageTitles[1]);

        viewPager.setAdapter(pagerAdapter);
    }

}
