package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.birdlabs.mhrd.ComposeActivity;
import com.birdlabs.mhrd.ContentActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.items.TimestampItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.util.Preferences;
import com.birdlabs.mhrd.views.NewsFeedView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.HashMap;

/**
 * Created by bijoy on 12/16/15.
 */
public class ContentFragment extends Fragment {
    Context context;
    ContentActivity activity;
    ImageLoader imageLoader;
    NewsFeedView holder;
    ContentFragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_feed_layout, container, false);

        final NewsFeedItem data = activity.item;
        holder = new NewsFeedView(rootView);
        imageLoader = Functions.loadImageLoader(context);
        setupHolder(data);

        fragment = this;
        setupFab(rootView);

        return rootView;
    }

    public void setupHolder(final NewsFeedItem data) {
        holder.title.setText(data.title);
        holder.content.setText(data.content);

        if (!data.anonymous) {
            holder.author_name.setText(data.author.getName());
        } else {
            holder.author_name.setText("Anonymous");
        }

        holder.tags.setTags(data.tags);

        TimestampItem timestampItem = new TimestampItem(context, data.created);
        holder.timestamp.setText(timestampItem.time + ", " + timestampItem.date);

        if (!data.anonymous && data.author.picture != null) {
            ImageAware logoImageAware = new ImageViewAware(holder.author_picture, false);
            imageLoader.displayImage(data.author.picture.getLink(), logoImageAware);
        }

        holder.votes.setText("" + (data.upvote - data.downvote));

        if (data.author != null
                && data.author.id.equals(Preferences.getInstance(context).getUserId())) {
            holder.modify_content.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Post?")
                            .setMessage("Would you like to delete post?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Access.getInstance(context).sendData(
                                            new AccessItem(
                                                    Api.getPostLink(data.id),
                                                    null,
                                                    AccessItem.DELETE_POST,
                                                    true).setMethod(Request.Method.DELETE),
                                            null, new HashMap<String, Object>());
                                    activity.finish();
                                }
                            })
                            .show();
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra(ComposeActivity.COMPOSE_ACTIVITY, data);
                    context.startActivity(intent);
                    getActivity().finish();
                }
            });

        } else {
            holder.modify_content.setVisibility(View.GONE);
        }

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link;
                Integer method;
                if (data.user_vote == 1) {
                    link = Api.getRemoveVoteLink(data.id);
                    method = AccessItem.REMOVE_VOTE_POST;
                } else {
                    link = Api.getUpvoteLink(data.id);
                    method = AccessItem.UPVOTE_POST;
                }

                Access.getInstance(context)
                        .sendData(
                                new AccessItem(link, null, method, true),
                                fragment,
                                new HashMap<String, Object>());
            }
        });

        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link;
                Integer method;
                if (data.user_vote == -1) {
                    link = Api.getRemoveVoteLink(data.id);
                    method = AccessItem.REMOVE_VOTE_POST;
                } else {
                    link = Api.getDownvoteLink(data.id);
                    method = AccessItem.DOWNVOTE_POST;
                }

                Access.getInstance(context)
                        .sendData(
                                new AccessItem(link, null, method, true),
                                fragment,
                                new HashMap<String, Object>());
            }
        });

        if (data.user_vote == 1) {
            holder.upvote.setColorFilter(context.getResources().getColor(R.color.primary_color));
            holder.downvote.setColorFilter(context.getResources().getColor(R.color.upvotes));
            holder.votes.setTextColor(context.getResources().getColor(R.color.primary_color));
        } else if (data.user_vote == -1) {
            holder.upvote.setColorFilter(context.getResources().getColor(R.color.upvotes));
            holder.downvote.setColorFilter(context.getResources().getColor(R.color.primary_color));
            holder.votes.setTextColor(context.getResources().getColor(R.color.primary_color));
        } else {
            holder.upvote.setColorFilter(context.getResources().getColor(R.color.upvotes));
            holder.downvote.setColorFilter(context.getResources().getColor(R.color.upvotes));
            holder.votes.setTextColor(context.getResources().getColor(R.color.upvotes));
        }

    }

    public void addNewsFeed(NewsFeedItem nitem) {
        setupHolder(nitem);
    }

    public void setupFab(View root) {
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.viewPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        this.activity = (ContentActivity) activity;
    }

}
