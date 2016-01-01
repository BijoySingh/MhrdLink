package com.birdlabs.mhrd.views;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.birdlabs.mhrd.R;

/**
 * Created by bijoy on 12/17/15.
 */
public class ProfileView {
    public ImageView profilePicture;
    public TextView name;
    public TextView email;
    public TextView username;
    public EditText first_name;
    public EditText last_name;
    public TextView email_text_view;
    public Spinner college;

    public ProfileView(View root) {
        this.profilePicture = (ImageView) root.findViewById(R.id.logo);
        this.name = (TextView) root.findViewById(R.id.name);
        this.email = (TextView) root.findViewById(R.id.email_id);
        this.username = (TextView) root.findViewById(R.id.username);
        this.first_name = (EditText) root.findViewById(R.id.first_name);
        this.last_name = (EditText) root.findViewById(R.id.last_name);
        this.email_text_view = (TextView) root.findViewById(R.id.email_tv);
        this.college = (Spinner) root.findViewById(R.id.college_spinner);
    }
}
