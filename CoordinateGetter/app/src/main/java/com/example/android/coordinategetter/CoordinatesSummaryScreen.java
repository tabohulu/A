package com.example.android.coordinategetter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CoordinatesSummaryScreen extends AppCompatActivity {
    public TextView Stops;
    public TextView Longs;
    public TextView Lats;


    public String stops;
    public String longs;
    public String lats;
    public String mailText;
    public String mailSubject;

    public ArrayList<String> stopNames;
    public ArrayList<String> longitudes;
    public ArrayList<String> latitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates_summary_screen);
        Stops=(TextView)findViewById(R.id.names_tv) ;
        Longs=(TextView)findViewById(R.id.longs_tv) ;
        Lats=(TextView)findViewById(R.id.lats_tv) ;
        getSummary();
    }

    public void getSummary()
    {
        Intent intent = getIntent();
        Bundle extras =intent.getExtras();
        stops = extras.getString("STOPS");
        longs = extras.getString("LONGS");
        lats = extras.getString("LATS");
        mailText=extras.getString("TEXT");
        mailSubject=extras.getString("SUBJECT");
        Stops.setText(stops);
        Longs.setText(longs);
        Lats.setText(lats);
    }

    public void SendMail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL , new String[] {"bohulukwame@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT ,  "path coordinates for "+mailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT ,  mailText);//fix this

        try{
            startActivity(Intent.createChooser(emailIntent,"Send mail..."));
            finish();
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(CoordinatesSummaryScreen.this,"no mail client found",Toast.LENGTH_LONG).show();
        }
    }

    public String CreateEmailText(ArrayList<String> arrayList,ArrayList<String> arrayList1,ArrayList<String> arrayList2){
        String string="BusStops \t\t Longitudes \t\t Latitudes \n";
        for(int i=0;i<arrayList.size();i++){
            string+=arrayList.get(i) + "\t "+arrayList1.get(i) + "\t "+arrayList2.get(i)+"\n";
        }
        return string;
    }

    public void GoBack(View view){
        finish();

    }
}
