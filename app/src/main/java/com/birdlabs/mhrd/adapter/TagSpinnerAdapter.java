package com.birdlabs.mhrd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.items.TagItem;
import com.birdlabs.mhrd.util.Functions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;

/**
 * Created by bijoy on 12/17/15.
 */
public class TagSpinnerAdapter extends ArrayAdapter<TagItem> {

    Context context;
    List<TagItem> tags;

    public TagSpinnerAdapter(Context context,
                             List<TagItem> tags) {
        super(context,
                R.layout.tag_spinner_item,
                R.id.title, tags);
        this.context = context;
        this.tags = tags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    public View getCustomView(int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View root = inflater.inflate(R.layout.tag_spinner_item, parent, false);
        TextView name = (TextView) root.findViewById(R.id.name);
        TagItem data = tags.get(position);
        name.setText(data.tag);
        return root;
    }
}
