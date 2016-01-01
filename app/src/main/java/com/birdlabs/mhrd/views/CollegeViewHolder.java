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
public class CollegeViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView location;
    public TextView phone;
    public ImageView logo;
    public LinearLayout layout;

    public CollegeViewHolder(final View root) {
        super(root);
        layout = (LinearLayout) root.findViewById(R.id.layout);
        name = (TextView) root.findViewById(R.id.name);
        location = (TextView) root.findViewById(R.id.location);
        phone = (TextView) root.findViewById(R.id.phone);
        logo = (ImageView) root.findViewById(R.id.logo);
    }
}

