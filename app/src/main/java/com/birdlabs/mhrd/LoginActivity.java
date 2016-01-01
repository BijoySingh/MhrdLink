package com.birdlabs.mhrd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.birdlabs.mhrd.fragments.LoginFragment;
import com.birdlabs.mhrd.fragments.RegistrationFragment;
import com.birdlabs.mhrd.fragments.SignupFragment;
import com.birdlabs.mhrd.util.Preferences;
import com.birdlabs.mhrd.views.ToolbarView;


public class LoginActivity extends TabbedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Preferences.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setupPager();
        setupToolbar();
    }

    public void setupToolbar() {
        ToolbarView toolbar = new ToolbarView(findViewById(R.id.toolbar_layout));
        toolbar.title.setVisibility(View.GONE);
        toolbar.logo.setVisibility(View.GONE);
        toolbar.icon1.setVisibility(View.GONE);
        toolbar.icon2.setVisibility(View.GONE);
        toolbar.icon3.setVisibility(View.GONE);
    }

    public void setupPager() {
        String[] pageTitles = getResources().getStringArray(R.array.login_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment(), pageTitles[0]);
        pagerAdapter.addFragment(new RegistrationFragment(), pageTitles[1]);
        pagerAdapter.addFragment(new SignupFragment(), pageTitles[2]);

        viewPager.setAdapter(pagerAdapter);
    }

}
