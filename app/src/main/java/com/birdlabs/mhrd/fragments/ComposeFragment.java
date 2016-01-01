package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.birdlabs.mhrd.ComposeActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.adapter.TagSpinnerAdapter;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.items.TagItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.views.ComposeView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bijoy on 12/16/15.
 */
public class ComposeFragment extends Fragment {
    Context context;
    ComposeActivity activity;
    ComposeView holder;
    List<Integer> tagIds = new ArrayList<>();
    Boolean update;
    NewsFeedItem item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.compose_layout, container, false);

        setupHolder(rootView);
        setupHolder(activity.item);
        setupFab(rootView);

        return rootView;
    }

    public void setupHolder(NewsFeedItem item) {
        this.item = item;
        if (item != null) {
            update = true;
            holder.title.setText(item.title);
            holder.anonymous.setChecked(item.anonymous);
            holder.content.setText(item.content);
            for (TagItem tag : getAllTags()) {
                for (String old_tag : item.tags) {
                    if (old_tag.contentEquals(tag.tag)) {
                        addTagItem(tag);
                    }
                }
            }
        } else {
            update = false;
        }
    }

    public void addTagItem(TagItem item) {
        if (item.id != null) {
            if (!tagIds.contains(item.id)) {
                String[] current_tags = holder.tagGroup.getTags();
                List<String> new_tags = new ArrayList<>();
                for (String tag : current_tags) {
                    new_tags.add(tag);
                }
                new_tags.add(item.tag);
                holder.tagGroup.setTags(new_tags);
                tagIds.add(item.id);
            }
            holder.tags.setSelection(0);
        }
    }

    public void setupHolder(View root) {
        holder = new ComposeView(context, root);
        final List<TagItem> tags = getAllTags();
        holder.tags.setAdapter(new TagSpinnerAdapter(context, tags));
        holder.tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                TagItem item = tags.get(position);
                addTagItem(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String s) {
                for (TagItem item : tags) {
                    if (item.tag.equals(s)) {
                        tagIds.remove(item.id);
                        String[] current_tags = holder.tagGroup.getTags();
                        List<String> new_tags = new ArrayList<>();
                        for (String tag : current_tags) {
                            if (!tag.equals(s)) {
                                new_tags.add(tag);
                            }
                        }
                        holder.tagGroup.setTags(new_tags);
                    }
                }
            }
        });
    }

    public void submit() {
        String title = holder.title.getText().toString();
        String content = holder.content.getText().toString();
        Boolean anonymous = holder.anonymous.isChecked();

        Integer[] tags = new Integer[tagIds.size()];
        Integer position = 0;
        for (Integer tag : tagIds) {
            tags[position] = tag;
            position++;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("tags", tags);
        map.put("anonymous", anonymous);
        map.put("visibility", 0);

        if (update) {
            Access.getInstance(context)
                    .sendData(new AccessItem(
                                    Api.getPostLink(item.id),
                                    null,
                                    AccessItem.ADD_POST,
                                    true).setMethod(Request.Method.PUT),
                            this, map);
        } else {
            Access.getInstance(context)
                    .sendData(new AccessItem(
                            Api.getSendPostLink(),
                            null,
                            AccessItem.ADD_POST,
                            true
                    ), this, map);
        }

    }

    public List<TagItem> getAllTags() {
        String tagStr = Functions.offlineDataReader(context, Functions.FILENAME_TAG_LIST);
        List<TagItem> tags = new ArrayList<>();
        tags.add(TagItem.getDefault());
        try {
            JSONArray array = new JSONArray(tagStr);
            for (int position = 0; position < array.length(); position++) {
                tags.add(new TagItem(array.getJSONObject(position)));
            }
        } catch (JSONException e) {
            Log.e(ComposeFragment.class.getSimpleName(), e.getMessage(), e);
        }
        return tags;
    }

    public void setupFab(View root) {
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        this.activity = (ComposeActivity) activity;
    }

}
