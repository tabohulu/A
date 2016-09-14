package com.example.android.awaybustrotro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SessionManager s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s =new SessionManager(getApplicationContext());
    }

    public void loginScreen(View view){
        Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
        startActivity(intent);

    }
}
