package com.example.android.awaybustrotro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainLoginScreen extends AppCompatActivity {
SessionManager sessionManager;
    EditText usernameEditText;
    EditText passwordEditText;
    String userName;
    String password;
    String storedUsername;
    String storedPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_screen);

        sessionManager= new SessionManager(getApplicationContext());
        usernameEditText = (EditText)findViewById(R.id.user_name_input_main);
        passwordEditText = (EditText)findViewById(R.id.password_input_main);

        HashMap<String, String> user = sessionManager.getUserDetails();

        // name
        storedUsername = user.get(SessionManager.KEY_NAME);

        // password
        storedPassword = user.get(SessionManager.KEY_PASSWORD);
        setInputs();
    }

    public void setInputs(){
            userName=usernameEditText.getText().toString();
            password=passwordEditText.getText().toString();
    }

    public void compareInputs(View view){
        if(usernameEditText.getText().length()>0&passwordEditText.getText().length()>0) {
            if(userName.equals(storedUsername)&password.equals(storedPassword)){
                Intent intent= new Intent(this,Main2Activity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"username/password is wrong",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"input required",Toast.LENGTH_SHORT).show();
        }

    }
}
