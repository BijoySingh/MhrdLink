package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.adapter.CollegeAdapter;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * the fragment for the newsfeed.
 * Created by bijoy on 12/16/15.
 */
public class InstitutesFragment extends Fragment {

    Context context;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CollegeAdapter adapter;

    public List<CollegeItem> items;

    public static final String FILENAME = "college_list";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_layout, container, false);
        items = new ArrayList<>();

        initializeRecyclerView(rootView);
        refreshList();
        return rootView;
    }

    /**
     * Initialises the recycler view
     *
     * @param rootView the root view of the fragment
     */
    public void initializeRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
                requestData();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary_color);
        swipeRefreshLayout.setEnabled(true);

        adapter = new CollegeAdapter(context, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Refreshes the list
     */
    public void refreshList() {
        try {
            String json = Functions.offlineDataReader(context, FILENAME);
            Log.d(InstitutesFragment.class.getSimpleName(), json);

            JSONArray array = new JSONArray(json);
            items = new ArrayList<>();
            for (int position = 0; position < array.length(); position++) {
                JSONObject object = array.getJSONObject(position);
                CollegeItem item = new CollegeItem(object);
                items.add(item);
            }

            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            Log.e(InstitutesFragment.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public void requestData() {
        Access.getInstance(context).getData(
                new AccessItem(Api.getCollegeLink(),
                        FILENAME, AccessItem.COLLEGE_LIST, true), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}
