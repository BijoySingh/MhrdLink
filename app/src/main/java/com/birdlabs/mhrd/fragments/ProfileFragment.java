package com.birdlabs.mhrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.birdlabs.mhrd.LoginActivity;
import com.birdlabs.mhrd.MainActivity;
import com.birdlabs.mhrd.R;
import com.birdlabs.mhrd.adapter.CollegeSpinnerAdapter;
import com.birdlabs.mhrd.items.CollegeItem;
import com.birdlabs.mhrd.util.Access;
import com.birdlabs.mhrd.util.AccessItem;
import com.birdlabs.mhrd.util.Api;
import com.birdlabs.mhrd.util.Functions;
import com.birdlabs.mhrd.util.Preferences;
import com.birdlabs.mhrd.views.ProfileView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the fragment for the profile
 * Created by bijoy on 12/16/15.
 */
public class ProfileFragment extends Fragment {

    Context context;
    Preferences preferences;
    ProfileView holder;
    Bitmap bitmap;
    ImageLoader imageLoader;
    TextView signOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_layout, container, false);

        preferences = Preferences.getInstance(context);
        holder = new ProfileView(rootView);
        imageLoader = Functions.loadImageLoader(context);

        signOut = (TextView) rootView.findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.logout();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                getActivity().finish();
            }
        });

        setupFab(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHolder();
    }

    public void setupFab(View root) {
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHolder();
                loadHolder();
            }
        });
    }

    public void loadHolder() {
        holder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        holder.email.setText(preferences.getEmail());
        holder.email_text_view.setText(preferences.getEmail());
        holder.username.setText("@" + preferences.getUsername());
        holder.name.setText(preferences.getName());
        holder.first_name.setText(preferences.getFirstName());
        holder.last_name.setText(preferences.getLastName());

        String logoUrl = Api.getSlashLessBaseLink() + preferences.getUserLogo();
        Log.d(ProfileFragment.class.getSimpleName(), logoUrl);
        if (bitmap != null) {
            holder.profilePicture.setImageBitmap(bitmap);
        } else {
            ImageAware logoImageAware = new ImageViewAware(holder.profilePicture, false);
            imageLoader.displayImage(logoUrl, logoImageAware);
        }

        List<CollegeItem> colleges = getColleges();
        holder.college.setAdapter(new CollegeSpinnerAdapter(context, colleges));
        Integer position = 0;
        for (CollegeItem college : colleges) {
            if (college.id.equals(preferences.getCollegeId())) {
                holder.college.setSelection(position);
                break;
            }
            position++;
        }
    }

    public List<CollegeItem> getColleges() {
        List<CollegeItem> items = new ArrayList<>();
        try {
            String json = Functions.offlineDataReader(context, InstitutesFragment.FILENAME);
            Log.d(InstitutesFragment.class.getSimpleName(), json);

            JSONArray array = new JSONArray(json);
            for (int position = 0; position < array.length(); position++) {
                JSONObject object = array.getJSONObject(position);
                CollegeItem item = new CollegeItem(object);
                items.add(item);
            }
        } catch (JSONException e) {
            Log.e(InstitutesFragment.class.getSimpleName(), e.getMessage(), e);
        }

        return items;
    }

    public void saveHolder() {
        preferences.saveFirstName(holder.first_name.getText().toString());
        preferences.saveLastName(holder.last_name.getText().toString());
        preferences.saveCollegeId(((CollegeItem) holder.college.getSelectedItem()).id);
        Functions.makeToast(context, "Settings Saved");

        Map<String, Object> profileMap = new HashMap<>();
        profileMap.put("first_name", preferences.getFirstName());
        profileMap.put("last_name", preferences.getLastName());
        profileMap.put("college", preferences.getCollegeId());

        Access.getInstance(context).sendData(
                new AccessItem(
                        Api.getUpdateProfileLink(preferences.getUserId()),
                        null, AccessItem.UPDATE_PROFILE, true), this, profileMap);

        if (bitmap != null) {
            Map<String, Object> pictureMap = new HashMap<>();
            pictureMap.put("file", getStringImage(bitmap));

            Access.getInstance(context).sendData(
                    new AccessItem(
                            Api.getUpdatePictureLink(preferences.getUserId()),
                            null, AccessItem.UPDATE_PICTURE, true), this, pictureMap);
        }

    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                MainActivity.PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK
                && data != null) {
            try {
                Bundle extras = data.getExtras();
                bitmap = extras.getParcelable("data");
            } catch (Exception e) {
                Log.e(ProfileFragment.class.getSimpleName(), e.getMessage(), e);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
