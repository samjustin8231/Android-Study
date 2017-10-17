package com.example.sam.android_study.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam.android_study.R;

public class LoginActivity extends Activity {
    private EditText etNumber;
    private ProgressDialog progressDialog;

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

        //显示ProgressDialog
        progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Please wait...", true, false);

        //新建线程
        new Thread(){

            @Override
            public void run() {
                //需要花时间计算的方法
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //向handler发消息
                handler.sendEmptyMessage(0);
            }}.start();
    }

    /**
     * 用Handler来更新UI
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            //关闭ProgressDialog
            progressDialog.dismiss();

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }};
}
