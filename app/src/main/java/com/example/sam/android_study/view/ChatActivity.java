package com.example.sam.android_study.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sam.android_study.R;

public class ChatActivity extends AppCompatActivity {
    private TextView tvTitle;

    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    public void backAction(View view) {
        finish();
    }
}
