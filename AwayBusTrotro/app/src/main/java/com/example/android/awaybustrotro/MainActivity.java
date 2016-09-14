package com.example.android.awaybustrotro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   /* private String getStart(){
        EditText getStartView = (EditText) findViewById(R.id.Start_EditText);
       String Start= getStartView.getText().toString();
        return Start;
    }

    private String getEnd() {
        EditText getEndView = (EditText) findViewById(R.id.End_EditText);
        String End = getEndView.getText().toString();
        return End;
    }

    private void setText(String bb){
        TextView testText = (TextView) findViewById(R.id.gggText);
        testText.setText(bb);

    }

    private void nextButton(){

        String start=getStart();
        String end=getEnd();
        String msg ="start is "+start+ " end is "+end;
        setText(msg);
    }*/

}

