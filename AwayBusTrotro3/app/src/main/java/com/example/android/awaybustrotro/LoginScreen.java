package com.example.android.awaybustrotro;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {

   // AlertDialog alert = new AlertDialog();

    // Session Manager Class
    SessionManager session;

    // Button Logout
    Button btnLogout;

    EditText UserNameInput;
    EditText PasswordInput;
    EditText StationNameInput;
    EditText StartStationInput ;
    EditText EndStationInput ;
    EditText CarNumberInput ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        session = new SessionManager(getApplicationContext());

        UserNameInput = (EditText) findViewById(R.id.user_name_input);
        PasswordInput = (EditText) findViewById(R.id.password_input);
        StationNameInput = (EditText) findViewById(R.id.station_name_input);
        StartStationInput = (EditText) findViewById(R.id.start_station_input);
        EndStationInput = (EditText) findViewById(R.id.end_station_input);
        CarNumberInput = (EditText) findViewById(R.id.car_number_input);


        // Button logout
        btnLogout = (Button) findViewById(R.id.next_button);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        //session.logoutUser();



        // get user data from session
       /* HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        // displaying user data
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));*/


        /**
         * Logout button click event
         * */
    }
        public void summaryScreen(View view) {
            session.createLoginSession(UserNameInput.getText().toString(), PasswordInput.getText().toString(), CarNumberInput.getText().toString(), StationNameInput.getText().toString(), StartStationInput.getText().toString(), EndStationInput.getText().toString());
            Toast.makeText(LoginScreen.this,"Password "+PasswordInput.getText().toString(),Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,SummaryScreen.class);
                startActivity(intent);

    }



}
