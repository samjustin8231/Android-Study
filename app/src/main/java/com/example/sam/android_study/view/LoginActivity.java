package com.example.sam.android_study.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam.android_study.R;

public class LoginActivity extends Activity {
    private EditText etNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNumber = (EditText) findViewById(R.id.etNumber);
    }

    public void loginAction(View view) {
        String number = etNumber.getText().toString().trim();
        if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity.number = number;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
