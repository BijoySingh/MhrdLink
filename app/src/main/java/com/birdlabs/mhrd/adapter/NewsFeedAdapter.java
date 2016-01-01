package com.birdlabs.mhrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.ContentActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.fragments.NewsFeedFragment;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.items.TimestampItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.views.NewsFeedViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.HashMap;

/**
 * adapter news feed
 * Created by bijoy on 12/16/15.
 */
public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedViewHolder> {

    private Context context;
    private NewsFeedFragment fragment;
    final ImageLoader imageLoader;

    public NewsFeedAdapter(Context context, NewsFeedFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.imageLoader = Functions.loadImageLoader(context);
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feed_item, parent, false);
        return new NewsFeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder holder, final int position) {

        final NewsFeedItem data = fragment.items.get(position);
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
        holder.votes.setText("" + (data.upvote - data.downvote));

        if (!data.anonymous && data.author.picture != null) {
            ImageAware logoImageAware = new ImageViewAware(holder.author_picture, false);
            imageLoader.displayImage(data.author.picture.getLink(), logoImageAware);
        } else {
            holder.author_picture.setImageResource(R.drawable.face);
        }

        holder.news_feed_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(ContentActivity.NEWS_FEED_ITEM, data);
                context.startActivity(intent);
            }
        });

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link;
                Integer method;
                if (data.user_vote == 1) {
                    link = Api.getRemoveVoteLink(data.id);
                    method = AccessItem.REMOVE_VOTE_POST_LIST;
                } else {
                    link = Api.getUpvoteLink(data.id);
                    method = AccessItem.UPVOTE_POST_LIST;
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
                    method = AccessItem.REMOVE_VOTE_POST_LIST;
                } else {
                    link = Api.getDownvoteLink(data.id);
                    method = AccessItem.DOWNVOTE_POST_LIST;
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

    @Override
    public int getItemCount() {
        return fragment.items.size();
    }

}

