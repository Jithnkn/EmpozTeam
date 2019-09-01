package com.example.training.empoz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.training.empoz.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread=new Thread(){
            @Override
            public void run() {

                try {
                    sleep(1 * 3000);
                    Intent intent=new Intent(getApplicationContext(),EmployeeLogin.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e)
                {

                }
            }
        };
        thread.start();
    }

}
