package com.birdlabs.mhrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.CollegeActivity;
import com.birdlabs.mhrd.ContentActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.fragments.InstituteFragment;
import com.birdlabs.mhrd.fragments.InstitutesFragment;
import com.birdlabs.mhrd.fragments.NewsFeedFragment;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.items.NewsFeedItem;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.views.CollegeViewHolder;
import com.birdlabs.mhrd.views.NewsFeedViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.squareup.picasso.Picasso;

/**
 * adapter news feed
 * Created by bijoy on 12/16/15.
 */
public class CollegeAdapter extends RecyclerView.Adapter<CollegeViewHolder> {

    private Context context;
    private InstitutesFragment fragment;
    private final ImageLoader imageLoader;

    public CollegeAdapter(Context context, InstitutesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        imageLoader = Functions.loadImageLoader(context);
    }

    @Override
    public CollegeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.institute_item, parent, false);
        return new CollegeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CollegeViewHolder holder, final int position) {

        final CollegeItem data = fragment.items.get(position);
        holder.name.setText(data.name);
        holder.phone.setText(data.phone);
        holder.location.setText(data.location);
        holder.logo.setImageResource(R.drawable.placeholder);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CollegeActivity.class);
                intent.putExtra(CollegeActivity.INSTITUTE_FRAGMENT, data);
                context.startActivity(intent);
            }
        });

        ImageAware logoImageAware = new ImageViewAware(holder.logo, false);
        imageLoader.displayImage(data.logo.getLink(), logoImageAware);

    }

    @Override
    public int getItemCount() {
        return fragment.items.size();
    }

}

