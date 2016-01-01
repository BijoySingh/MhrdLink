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
import android.widget.EditText;
import android.widget.ImageView;

import com.birdlabs.mhrd.ContentActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.adapter.CommentAdapter;
import com.birdlabs.mhrd.items.CommentItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bijoy on 12/16/15.
 */
public class CommentsFragment extends Fragment {

    Context context;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CommentAdapter adapter;
    public List<CommentItem> items = new ArrayList<>();
    EditText newComment;
    ImageView sendCommentButton;
    Boolean disabled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comment_layout, container, false);
        initializeRecyclerView(rootView);
        requestData();
        refreshList();

        newComment = (EditText) rootView.findViewById(R.id.new_comment);
        sendCommentButton = (ImageView) rootView.findViewById(R.id.send_comment);
        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!disabled) {
                    sendComment();
                    disableComment();
                }
            }
        });

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

        adapter = new CommentAdapter(context, this);
        recyclerView.setAdapter(adapter);
    }


    public void requestData() {
        Integer itemId = ((ContentActivity) getActivity()).item.id;
        Access.getInstance(context).getData(new AccessItem(
                Api.getRepliesLink(itemId),
                Functions.FILENAME_COMMENTS + "_" + itemId,
                AccessItem.GET_COMMENTS, true), this);
    }

    public void sendComment() {
        Integer itemId = ((ContentActivity) getActivity()).item.id;
        Map<String, Object> map = new HashMap<>();
        map.put("post", itemId);
        map.put("content", newComment.getText().toString());

        Access.getInstance(context).sendData(new AccessItem(
                Api.getAddRepliesLink(),
                null,
                AccessItem.ADD_COMMENT,
                true), this, map);
    }

    /**
     * Refreshes the list
     */
    public void refreshList() {
        try {
            Integer itemId = ((ContentActivity) getActivity()).item.id;
            String json = Functions.offlineDataReader(context,
                    Functions.FILENAME_COMMENTS + "_" + itemId);
            Log.d(InstitutesFragment.class.getSimpleName(), json);

            JSONArray array = new JSONArray(json);
            items = new ArrayList<>();
            for (int position = 0; position < array.length(); position++) {
                JSONObject object = array.getJSONObject(position);
                CommentItem item = new CommentItem(object);
                items.add(item);
            }

            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            Log.e(InstitutesFragment.class.getSimpleName(), e.getMessage(), e);
        }
        adapter.notifyDataSetChanged();
    }

    public void disableComment() {
        disabled = true;
        sendCommentButton.setColorFilter(context.getResources().getColor(R.color.hint_text));
    }

    public void clearNewComment() {
        newComment.setText("");
        disabled = false;
        sendCommentButton.setColorFilter(context.getResources().getColor(R.color.primary_dark_color));
    }

    public void addComment(CommentItem nitem) {
        for (CommentItem item : items) {
            if (item.id.equals(nitem.id)) {
                return;
            }
        }

        items.add(0, nitem);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }


}
