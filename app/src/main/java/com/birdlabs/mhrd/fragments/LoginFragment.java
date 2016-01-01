package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
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
public class LoginFragment extends Fragment {

    EditText username, password;
    Button loginButton;
    TextView signup;

    Context context;
    LoginActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);

        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.login_button);
        signup = (TextView) rootView.findViewById(R.id.signup_button);

        password.setText(Preferences.getInstance(getActivity()).getPassword());
        username.setText(Preferences.getInstance(getActivity()).getUsername());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.viewPager.setCurrentItem(1);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
                /*Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);*/
            }
        });

        return rootView;
    }

    public void sendData() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username.getText().toString());
        map.put("password", password.getText().toString());

        Preferences.getInstance(getActivity()).saveUsername(username.getText().toString());
        Preferences.getInstance(getActivity()).savePassword(password.getText().toString());

        Access.getInstance(getActivity()).sendData(
                new AccessItem(Api.getLoginLink(), null, AccessItem.LOGIN, false),
                this,
                map
        );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        this.activity = (LoginActivity) activity;
    }
}
