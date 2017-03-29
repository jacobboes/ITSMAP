package com.example.jacobboes.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.jacobboes.testing.MainActivity.EXTRA_MESSAGE;

public class DisplayMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(intent.getStringExtra(EXTRA_MESSAGE));
    }
}
