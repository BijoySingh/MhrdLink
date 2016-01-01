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
import com.birdlabs.mhrd.util.Functions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;

/**
 * Created by bijoy on 12/17/15.
 */
public class CollegeSpinnerAdapter extends ArrayAdapter<CollegeItem> {

    Context context;
    List<CollegeItem> colleges;
    final ImageLoader imageLoader;

    public CollegeSpinnerAdapter(Context context,
                                 List<CollegeItem> colleges) {
        super(context, R.layout.institute_spinner_item, R.id.title, colleges);
        this.context = context;
        this.colleges = colleges;
        this.imageLoader = Functions.loadImageLoader(context);
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

        View root = inflater.inflate(R.layout.institute_spinner_item, parent, false);

        LinearLayout layout = (LinearLayout) root.findViewById(R.id.layout);
        TextView name = (TextView) root.findViewById(R.id.name);
        TextView location = (TextView) root.findViewById(R.id.location);
        ImageView logo = (ImageView) root.findViewById(R.id.logo);

        CollegeItem data = colleges.get(position);
        name.setText(data.name);
        location.setText(data.location);

        ImageAware logoImageAware = new ImageViewAware(logo, false);
        imageLoader.displayImage(data.logo.getLink(), logoImageAware);

        return root;
    }
}
