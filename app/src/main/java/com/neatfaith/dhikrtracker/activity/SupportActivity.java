package com.neatfaith.dhikrtracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.neatfaith.dhikrtracker.R;

public class SupportActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        setTitle(R.string.help_and_support);

        mTextView = findViewById(R.id.support_textView);
    }
}
