package com.birdlabs.mhrd.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

import me.gujun.android.taggroup.TagGroup;

/**
 * the view of the news feed
 * Created by bijoy on 12/16/15.
 */
public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView content;
    public TextView author_name;
    public TextView timestamp;
    public TagGroup tags;
    public TextView comments;
    public TextView views;
    public ImageView author_picture;
    public TextView mhrd;
    public TextView votes;
    public ImageView bookmark;
    public LinearLayout news_feed_layout;

    public ImageView upvote;
    public ImageView downvote;

    public NewsFeedViewHolder(final View root) {
        super(root);
        news_feed_layout = (LinearLayout) root.findViewById(R.id.news_feed_layout);
        title = (TextView) root.findViewById(R.id.title);
        content = (TextView) root.findViewById(R.id.content);
        timestamp = (TextView) root.findViewById(R.id.timestamp);
        author_name = (TextView) root.findViewById(R.id.author_name);
        comments = (TextView) root.findViewById(R.id.comments);
        views = (TextView) root.findViewById(R.id.views);
        tags = (TagGroup) root.findViewById(R.id.tags);
        author_picture = (ImageView) root.findViewById(R.id.author_picture);
        mhrd = (TextView) root.findViewById(R.id.mhrd);
        votes = (TextView) root.findViewById(R.id.votes);
        bookmark = (ImageView) root.findViewById(R.id.bookmark);
        upvote = (ImageView) root.findViewById(R.id.upvote);
        downvote = (ImageView) root.findViewById(R.id.downvote);
    }
}

