package com.birdlabs.mhrd.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

/**
 * The toolbar view
 * Created by bijoy on 12/16/15.
 */
public class ToolbarView {
    public View rootView;
    public ImageView logo, icon1, icon2, icon3;
    public TextView title;

    public ToolbarView(View view) {
        rootView = view;
        title = (TextView) view.findViewById(R.id.toolbar_title);
        logo = (ImageView) view.findViewById(R.id.toolbar_logo);
        icon1 = (ImageView) view.findViewById(R.id.icon1);
        icon2 = (ImageView) view.findViewById(R.id.icon2);
        icon3 = (ImageView) view.findViewById(R.id.icon3);
    }
}
