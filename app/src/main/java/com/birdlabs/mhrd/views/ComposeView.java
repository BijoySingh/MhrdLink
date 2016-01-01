package com.birdlabs.mhrd.views;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bijoy on 12/18/15.
 */
public class ComposeView {

    public Context context;
    public EditText title;
    public EditText content;
    public TagGroup tagGroup;
    public Spinner tags;
    public Switch anonymous;

    public ComposeView(Context context, View root) {
        this.context = context;
        title = (EditText) root.findViewById(R.id.title);
        content = (EditText) root.findViewById(R.id.content);
        tagGroup = (TagGroup) root.findViewById(R.id.tag_list);
        tags = (Spinner) root.findViewById(R.id.tags);
        anonymous = (Switch) root.findViewById(R.id.anonymous);
    }
}
