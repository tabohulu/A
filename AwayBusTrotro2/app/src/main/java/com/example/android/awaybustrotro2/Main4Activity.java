package com.example.android.awaybustrotro2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSummary();
    }

    public void getSummary()
    {
        Intent intent = getIntent();
        Bundle extras =intent.getExtras();
        String msg = extras.getString("MSG");
        String msg2 = extras.getString("SDG");
        TextView tv = (TextView) findViewById(R.id.summary_textview);
        tv.setText(msg);
        TextView tv2 = (TextView) findViewById(R.id.summary2_textview);
        tv2.setText(msg2);
    }
}
