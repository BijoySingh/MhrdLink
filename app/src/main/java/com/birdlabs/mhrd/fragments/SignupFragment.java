package com.birdlabs.mhrd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.util.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 12/16/15.
 */
public class SignupFragment extends Fragment {
    EditText emailId, password, username, rePassword, code;
    Button signupButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_layout, container, false);

        emailId = (EditText) rootView.findViewById(R.id.email_id);
        password = (EditText) rootView.findViewById(R.id.password);
        username = (EditText) rootView.findViewById(R.id.username);
        rePassword = (EditText) rootView.findViewById(R.id.re_password);
        code = (EditText) rootView.findViewById(R.id.code);

        emailId.setText(Preferences.getInstance(getActivity()).getEmail());
        username.setText(Preferences.getInstance(getActivity()).getUsername());
        password.setText(Preferences.getInstance(getActivity()).getPassword());
        rePassword.setText(Preferences.getInstance(getActivity()).getPassword());

        signupButton = (Button) rootView.findViewById(R.id.login_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().contentEquals(rePassword.getText().toString())) {
                    sendData();
                } else {
                    Functions.makeToast(getActivity(), "Passwords are not the same");
                }
            }
        });

        return rootView;
    }

    public void sendData() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", emailId.getText().toString());
        map.put("code", code.getText().toString());
        map.put("username", username.getText().toString());
        map.put("password", password.getText().toString());

        Preferences.getInstance(getActivity()).saveEmail(emailId.getText().toString());
        Preferences.getInstance(getActivity()).saveUsername(username.getText().toString());
        Preferences.getInstance(getActivity()).savePassword(password.getText().toString());

        Access.getInstance(getActivity()).sendData(
                new AccessItem(Api.getSignupLink(), null, AccessItem.SIGNUP, false),
                this,
                map
        );
    }

}

