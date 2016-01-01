package com.birdlabs.mhrd.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

/**
 * the view of the news feed
 * Created by bijoy on 12/16/15.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView content;
    public TextView author_name;
    public TextView timestamp;
    public ImageView author_picture;
    public LinearLayout layout;

    public CommentViewHolder(final View root) {
        super(root);
        content = (TextView) root.findViewById(R.id.content);
        author_name = (TextView) root.findViewById(R.id.author_name);
        timestamp = (TextView) root.findViewById(R.id.timestamp);
        layout = (LinearLayout) root.findViewById(R.id.layout);
        author_picture = (ImageView) root.findViewById(R.id.author_picture);
    }
}

