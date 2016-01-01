package com.birdlabs.mhrd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.fragments.CommentsFragment;
import com.birdlabs.mhrd.items.CommentItem;
import com.birdlabs.mhrd.items.TimestampItem;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.views.CommentViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * adapter news feed
 * Created by bijoy on 12/16/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private Context context;
    private CommentsFragment fragment;
    final ImageLoader imageLoader;

    public CommentAdapter(Context context, CommentsFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.imageLoader = Functions.loadImageLoader(context);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, final int position) {

        final CommentItem data = fragment.items.get(position);
        holder.content.setText(data.content);

        TimestampItem item = new TimestampItem(context, data.timestamp);
        holder.timestamp.setText(item.time + "\n" + item.date);
        holder.author_name.setText(data.author.getName());

        if (data.author != null && data.author.picture != null) {
            ImageAware logoImageAware = new ImageViewAware(holder.author_picture, false);
            imageLoader.displayImage(data.author.picture.getLink(), logoImageAware);
        } else {
            holder.author_picture.setImageResource(R.drawable.face);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment.items.size();
    }

}

