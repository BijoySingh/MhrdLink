package com.birdlabs.mhrd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.birdlabs.mhrd.LoginActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 12/16/15.
 */
public class RegistrationFragment extends Fragment {

    EditText emailId;
    Button requestButton;
    TextView already, resend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_layout, container, false);

        emailId = (EditText) rootView.findViewById(R.id.email_id);
        requestButton = (Button) rootView.findViewById(R.id.login_button);
        already = (TextView) rootView.findViewById(R.id.have_code);
        resend = (TextView) rootView.findViewById(R.id.request_code);

        emailId.setText(Preferences.getInstance(getActivity()).getEmail());


        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).viewPager.setCurrentItem(2);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(Api.getRegisterLink(), AccessItem.REGISTER);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(Api.getResendCodeLink(), AccessItem.RESEND);
            }
        });

        return rootView;
    }

    public void sendData(String url, Integer type) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", emailId.getText().toString());

        Preferences.getInstance(getActivity()).saveEmail(emailId.getText().toString());

        Access.getInstance(getActivity()).sendData(
                new AccessItem(url, null, type, false),
                this,
                map
        );
    }
}

