package com.example.android.awaybustrotro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserInputSummary extends AppCompatActivity {
    TextView tv;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_summary);
        getSummary();
    }

    public void getSummary() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String msg = extras.getString("MSG");
        String msg2 = extras.getString("SDG");
        tv = (TextView) findViewById(R.id.stops_tv);
        tv.setText(msg);
        tv2 = (TextView) findViewById(R.id.userinput_tv);
        tv2.setText(msg2);
    }

    public void backToInputs(View view) {
        UserInputSummary.this.finish();

    }
}
