package com.birdlabs.mhrd.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bijoy on 12/16/15.
 */
public class NewsFeedView {
    public TextView title;
    public TextView content;
    public TextView author_name;
    public TagGroup tags;
    public TextView timestamp;
    public ImageView author_picture;
    public LinearLayout news_feed_layout;
    public TextView comments;
    public TextView views;
    public TextView votes;
    public TextView mhrd;
    public ImageView bookmark;

    public LinearLayout modify_content;
    public ImageView edit;
    public ImageView delete;

    public ImageView upvote;
    public ImageView downvote;

    public NewsFeedView(final View root) {
        news_feed_layout = (LinearLayout) root.findViewById(R.id.news_feed_layout);
        title = (TextView) root.findViewById(R.id.title);
        content = (TextView) root.findViewById(R.id.content);
        timestamp = (TextView) root.findViewById(R.id.timestamp);
        author_name = (TextView) root.findViewById(R.id.author_name);
        tags = (TagGroup) root.findViewById(R.id.tags);
        author_picture = (ImageView) root.findViewById(R.id.author_picture);
        comments = (TextView) root.findViewById(R.id.comments);
        views = (TextView) root.findViewById(R.id.views);
        mhrd = (TextView) root.findViewById(R.id.mhrd);
        votes = (TextView) root.findViewById(R.id.votes);
        bookmark = (ImageView) root.findViewById(R.id.bookmark);
        upvote = (ImageView) root.findViewById(R.id.upvote);
        downvote = (ImageView) root.findViewById(R.id.downvote);
        edit = (ImageView) root.findViewById(R.id.edit);
        delete = (ImageView) root.findViewById(R.id.delete);
        modify_content = (LinearLayout) root.findViewById(R.id.modify_content);

    }
}
