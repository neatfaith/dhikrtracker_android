package com.neatfaith.dhikrtracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;

public class AboutActivity extends AppCompatActivity {

    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle(R.string.about);

        mTextView = findViewById(R.id.about_textView);

    }
}
