package com.smap.jacobboes.firstassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_ID;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_NAME;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_PICTURE;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_STATUS;

public class ViewProfileActivity extends AppCompatActivity {
    private static final String CAMERA_DATA = "data";

    private TextView nameField;
    private TextView idField;
    private TextView androidStatus;
    private ImageView profilePicture;
    private Bitmap profileImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        readSavedInstanceState(savedInstanceState);
    }

    private void initializeViews() {
        nameField = (TextView) findViewById(R.id.name);
        idField = (TextView) findViewById(R.id.id);
        androidStatus = (TextView) findViewById(R.id.androidStatus);

        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });
    }

    private void readSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            nameField.setText(savedInstanceState.getString(PROFILE_NAME));
            idField.setText(savedInstanceState.getString(PROFILE_ID));
            androidStatus.setText(savedInstanceState.getString(PROFILE_STATUS));
            profileImageBitmap = savedInstanceState.getParcelable(PROFILE_PICTURE);
            if (profileImageBitmap != null) {
                profilePicture.setImageBitmap(profileImageBitmap);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(PROFILE_NAME, nameField.getText().toString());
        savedInstanceState.putString(PROFILE_ID, idField.getText().toString());
        savedInstanceState.putString(PROFILE_STATUS, androidStatus.getText().toString());
        savedInstanceState.putParcelable(PROFILE_PICTURE, profileImageBitmap);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Requests.EDIT_PROFILE && resultCode == RESULT_OK) {
            nameField.setText(data.getStringExtra(PROFILE_NAME));
            idField.setText(data.getStringExtra(PROFILE_ID));
            androidStatus.setText(data.getStringExtra(PROFILE_STATUS));
        } else if (requestCode == Requests.IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            profileImageBitmap = (Bitmap) extras.get(CAMERA_DATA);
            profilePicture.setImageBitmap(profileImageBitmap);
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Requests.IMAGE_CAPTURE);
        }
    }

    private void editProfile() {
        Intent editProfile = new Intent(this, EditProfileActivity.class);
        editProfile.putExtra(PROFILE_NAME, nameField.getText().toString());
        editProfile.putExtra(PROFILE_ID, idField.getText().toString());
        editProfile.putExtra(PROFILE_STATUS, androidStatus.getText().toString());

        startActivityForResult(editProfile, Requests.EDIT_PROFILE);
    }
}
