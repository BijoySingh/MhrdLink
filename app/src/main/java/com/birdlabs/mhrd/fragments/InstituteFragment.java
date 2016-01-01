package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.CollegeActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.views.CollegeView;

/**
 * the fragment for the newsfeed.
 * Created by bijoy on 12/16/15.
 */
public class InstituteFragment extends Fragment {

    Context context;
    CollegeActivity activity;
    CollegeView holder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.institute_layout, container, false);

        holder = new CollegeView(rootView, context);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        this.activity = (CollegeActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        holder.setup(activity.item);
    }
}
