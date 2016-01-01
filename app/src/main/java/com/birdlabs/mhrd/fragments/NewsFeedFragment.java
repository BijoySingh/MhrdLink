package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.ComposeActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.adapter.NewsFeedAdapter;
import com.birdlabs.mhrd.items.AuthorItem;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.items.NewsFeedItem;
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
 * the fragment for the college
 * Created by bijoy on 12/16/15.
 */
public class NewsFeedFragment extends Fragment {

    Context context;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsFeedAdapter adapter;
    public List<NewsFeedItem> items = new ArrayList<>();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_fab_layout, container, false);
        initializeRecyclerView(rootView);
        refreshList();
        setupFab(rootView);
        requestData();

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
                requestData();
                refreshList();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary_color);
        swipeRefreshLayout.setEnabled(true);

        adapter = new NewsFeedAdapter(context, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Refreshes the list
     */
    public void refreshList() {
        try {
            String json = Functions.offlineDataReader(context, Functions.FILENAME_POSTS);
            Log.d(InstitutesFragment.class.getSimpleName(), json);

            JSONArray array = new JSONArray(json);
            items = new ArrayList<>();
            for (int position = 0; position < array.length(); position++) {
                JSONObject object = array.getJSONObject(position);
                NewsFeedItem item = new NewsFeedItem(object);
                items.add(item);
            }

            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            Log.e(InstitutesFragment.class.getSimpleName(), e.getMessage(), e);
        }
    }


    public void requestData() {
        Access.getInstance(context).getData(new AccessItem(
                Api.getFilteredPostLink(),
                Functions.FILENAME_POSTS,
                AccessItem.GET_POST, true), this);
    }


    public void setupFab(View root) {
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ComposeActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public void addNewsFeed(NewsFeedItem nitem) {
        Integer position = 0;
        for (NewsFeedItem item : items) {
            if (item.id.equals(nitem.id)) {
                items.set(position, nitem);
                adapter.notifyItemChanged(position);
                return;
            }
            position++;
        }

        items.add(0, nitem);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}
