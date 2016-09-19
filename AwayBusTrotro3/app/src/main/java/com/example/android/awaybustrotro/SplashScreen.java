package com.example.android.awaybustrotro;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager=new SessionManager(getApplicationContext());
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    SplashScreen.this.finish();
                   // sessionManager.checkInstantiated();
                    sessionManager.checkLogin();
                }
            }
        };
        timer.start();
    }
}
