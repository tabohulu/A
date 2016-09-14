package com.example.android.awaybustrotro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class SummaryScreen extends AppCompatActivity {
SessionManager sessionManager;
    TextView userName;
    TextView pwd;
    TextView stnName;
    TextView carnumber;
    TextView beginStn;
    TextView finalStn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);
        sessionManager = new SessionManager(getApplicationContext());

        userName=(TextView)findViewById(R.id.user_name_tv);
        pwd=(TextView)findViewById(R.id.password_tv);
        stnName=(TextView)findViewById(R.id.station_name_tv);
        carnumber=(TextView)findViewById(R.id.car_number_tv);
        beginStn=(TextView)findViewById(R.id.start_station_tv);
        finalStn=(TextView)findViewById(R.id.end_station_tv);


        HashMap<String, String> user = sessionManager.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // password
        String password = user.get(SessionManager.KEY_PASSWORD);

        // license plate number
        String carNumber = user.get(SessionManager.KEY_CAR_NUMBER);

        // station name
        String stationName = user.get(SessionManager.KEY_STATION_NAME);

        // start
        String startStn = user.get(SessionManager.KEY_START_STATION);

        // end
        String endStn = user.get(SessionManager.KEY_END_STATION);

        setValues(name,password,carNumber,stationName,startStn,endStn);
    }

    public void setValues(String name,String pwds,String StnName,String carNo,String stnStart,String stnEnd){
        userName.setText(name);
        pwd.setText(pwds);
        stnName.setText(StnName);
        carnumber.setText(carNo);
        beginStn.setText(stnStart);
        finalStn.setText(stnEnd);

    }


    public void backToLoginScreen(View view) {
        finish();

    }
}
