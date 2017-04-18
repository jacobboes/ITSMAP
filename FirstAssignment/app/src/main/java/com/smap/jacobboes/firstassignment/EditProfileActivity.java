package com.smap.jacobboes.firstassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.smap.jacobboes.firstassignment.DataKeys.EDIT_ID;
import static com.smap.jacobboes.firstassignment.DataKeys.EDIT_NAME;
import static com.smap.jacobboes.firstassignment.DataKeys.EDIT_STATUS;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_ID;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_NAME;
import static com.smap.jacobboes.firstassignment.DataKeys.PROFILE_STATUS;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editId;
    private RadioButton yesBtn;
    private RadioButton noBtn;
    private boolean androidStatus;

    private Button saveBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeViews();
        readSavedInstanceState(savedInstanceState);
    }

    private void initializeViews() {
        editName = (EditText) findViewById(R.id.editName);
        editId = (EditText) findViewById(R.id.editId);
        yesBtn = (RadioButton) findViewById(R.id.statusYes);
        noBtn = (RadioButton) findViewById(R.id.statusNo);

        yesBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                androidStatus = isChecked;
            }
        });

        cancelBtn = (Button) findViewById(R.id.editCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        saveBtn = (Button) findViewById(R.id.editSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        Intent data = getIntent();
        editName.setText(data.getStringExtra(PROFILE_NAME));
        editId.setText(data.getStringExtra(PROFILE_ID));
        androidStatus = data.getStringExtra(PROFILE_STATUS).equals(getString(R.string.yes));
        resolveRadioButtons();
    }

    private void readSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            editName.setText(savedInstanceState.getString(EDIT_NAME));
            editId.setText(savedInstanceState.getString(EDIT_ID));
            androidStatus = savedInstanceState.getBoolean(EDIT_STATUS);
            resolveRadioButtons();
        }
    }

    private void resolveRadioButtons() {
        if (androidStatus && !yesBtn.isChecked()) {
            yesBtn.toggle();
        } else if (!androidStatus && !noBtn.isChecked()) {
            noBtn.toggle();
        }
    }

    private void cancel() {
        finish();
    }

    private void save() {
        Intent data = new Intent();
        data.putExtra(PROFILE_NAME, editName.getText().toString());
        data.putExtra(PROFILE_ID, editId.getText().toString());
        data.putExtra(PROFILE_STATUS, androidStatus ? getString(R.string.yes) : getString(R.string.no));
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(EDIT_NAME, editName.getText().toString());
        savedInstanceState.putString(EDIT_ID, editId.getText().toString());
        savedInstanceState.putBoolean(EDIT_STATUS, androidStatus);
        super.onSaveInstanceState(savedInstanceState);
    }
}
