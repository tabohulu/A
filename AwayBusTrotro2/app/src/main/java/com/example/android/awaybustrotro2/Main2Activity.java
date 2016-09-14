package com.example.android.awaybustrotro2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getFilename();
    }

    public void backButton(View view){
        finish();

    }

    public void next2Button(View view){
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);

    }

    public void getFilename()
    {
        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.MSG);
        TextView tv = (TextView) findViewById(R.id.file_text);
        tv.setText(msg);
    }
}
