package com.birdlabs.mhrd.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.util.Functions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.squareup.picasso.Picasso;

/**
 * the view of the news feed
 * Created by bijoy on 12/16/15.
 */
public class CollegeView {

    Context context;
    public TextView name;
    public TextView location;
    public TextView phone;
    public TextView homepage;
    public ImageView logo;
    public ImageView cover;
    private final ImageLoader imageLoader;

    public CollegeView(final View root, Context context) {
        name = (TextView) root.findViewById(R.id.name);
        location = (TextView) root.findViewById(R.id.location);
        phone = (TextView) root.findViewById(R.id.phone);
        homepage = (TextView) root.findViewById(R.id.homepage);
        logo = (ImageView) root.findViewById(R.id.logo);
        cover = (ImageView) root.findViewById(R.id.cover);
        imageLoader = Functions.loadImageLoader(context);
    }

    public void setup(CollegeItem item) {
        name.setText(item.name);
        location.setText(item.location);
        phone.setText(item.phone);
        homepage.setText(item.homepage);

        cover.setImageResource(R.drawable.placeholder);
        logo.setImageResource(R.drawable.face);

        ImageAware logoImageAware = new ImageViewAware(logo, false);
        imageLoader.displayImage(item.logo.file, logoImageAware);

        ImageAware coverImageAware = new ImageViewAware(cover, false);
        imageLoader.displayImage(item.cover.file, coverImageAware);
    }

}

