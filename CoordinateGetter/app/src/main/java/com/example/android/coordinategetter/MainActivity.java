package com.example.android.coordinategetter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.android.coordinategetter.MainActivityPre.PREFS_NAME;
import static com.google.android.gms.analytics.internal.zzy.a;
import static com.google.android.gms.analytics.internal.zzy.f;
import static com.google.android.gms.analytics.internal.zzy.i;
import static com.google.android.gms.analytics.internal.zzy.s;
import static com.google.android.gms.common.api.Status.st;

public class MainActivity extends AppCompatActivity
implements GoogleApiClient.ConnectionCallbacks ,
GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    public String userIP;
    public String filename;
    public String text;
    public TextView titleInfo;
    public Button checkLocationButton;
    public ArrayList<String> stopNames;
    public ArrayList<Double> longitudes;
    public ArrayList<Double> latitudes;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    protected Boolean mRequestingLocationUpdates;
    public FileOutputStream outputStream;
    public String PREFS_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        titleInfo=(TextView)findViewById(R.id.info_textview);
        checkLocationButton=(Button)findViewById(R.id.preview_location_btn) ;
        userIP=null;
        stopNames=new ArrayList<String>();
        longitudes=new ArrayList<Double>();
        latitudes=new ArrayList<Double>();
        mRequestingLocationUpdates=false;
        filename=intent.getStringExtra("AREA")+".txt";
        PREFS_NAME=intent.getStringExtra("AREA");
        //Toast.makeText(this,filename,Toast.LENGTH_LONG).show();
        text="";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int size=settings.getInt("size2",0);
        for(int i=0;i<size;i++) {
            stopNames.add(i, settings.getString("stops_"+i, "error"));
            longitudes.add(i,  Double.parseDouble(settings.getString("longitudes_"+i,  "0")));
            latitudes.add(i,  Double.parseDouble(settings.getString("latitudes_"+i,  "0")));
        }
        if(stopNames!=null){
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stopNames);
            ListView listView =(ListView)findViewById(R.id.bus_stop_names);
            listView.setAdapter(adapter);
            checkLocationButton.setEnabled(true);}








        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSION_ACCESS_FINE_LOCATION);
        }
        InitialiseClient();


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            stopNames=savedInstanceState.getStringArrayList("LIST");
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stopNames);
            ListView listView =(ListView)findViewById(R.id.bus_stop_names);
            listView.setAdapter(adapter);

    }

    public void findFile(){

    }

    public void AddLocation(View view) {
        BuildDialogBox();
        PopulateListView();
        //GetCoordinates();

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        for (int i = 0; i < stopNames.size(); i++) {
            editor.putString("stops_" + i, stopNames.get(i));
            editor.putString("longitudes_" + i, longitudes.get(i).toString());
            editor.putString("latitudes_" + i, latitudes.get(i).toString());
        }
        editor.putInt("size2",stopNames.size());

        // Commit the edits!
        editor.commit();
    }

    public void PreviewLocation(View view){
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);}



            for(int i=0;i<stopNames.size();i++){
                text = CreateEmailText(stopNames,longitudes,latitudes);
            }
        String stops =GetStopString(stopNames);
        String lats =GetCoordinateValues(latitudes);
        String longs =GetCoordinateValues(longitudes);
        Intent intent=new Intent(this,CoordinatesSummaryScreen.class);
        Bundle extras = new Bundle();
        extras.putString("STOPS",stops);
        extras.putString("LONGS",longs);
        extras.putString("LATS",lats);
        extras.putString("TEXT",text);
        extras.putString("SUBJECT",PREFS_NAME);
        intent.putExtras(extras);
        startActivity(intent);

    }

    public String GetStopString(ArrayList<String> arrayList){
        String string="";
        for(int i=0;i<arrayList.size();i++){
             string+=arrayList.get(i) +"\n";
        }
        return string;
    }

    public String GetCoordinateValues(ArrayList<Double> arrayList){
        String cord="";
        for(int i=0;i<arrayList.size();i++){
            cord+=Double.toString(arrayList.get(i)) +"\n";
        }
        return cord;
    }

    public String CreateEmailText(ArrayList<String> arrayList,ArrayList<Double> arrayList1,ArrayList<Double> arrayList2){
        String string="BusStops \t\t Longitudes \t\t Latitudes \n";
        for(int i=0;i<arrayList.size();i++){
            string+=arrayList.get(i) + "\t "+Double.toString(arrayList1.get(i)) + "\t "+Double.toString(arrayList2.get(i))+"\n";
        }
        return string;
    }



    public void BuildDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name of bus stop");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userIP=input.getText().toString();
                stopNames.add(userIP);
                handleNewLocation(mCurrentLocation);
                titleInfo.setText(userIP+" added to list of Bus Stops");
                if (userIP!=null) {
                    checkLocationButton.setEnabled(true);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void PopulateListView(){
        if(stopNames!=null){
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stopNames);
            ListView listView =(ListView)findViewById(R.id.bus_stop_names);
            listView.setAdapter(adapter);
            GetCoordinates();

        }

    }



    public void GetCoordinates(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (!mRequestingLocationUpdates) {
                mRequestingLocationUpdates=true;
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } //else {
              // handleNewLocation(mCurrentLocation);
            //mRequestingLocationUpdates=false;
            //}
            Log.i(TAG, "Location services connected.");
        }
    }

    public void handleNewLocation(Location location){
        if(location!=null) {
            longitudes.add(location.getLongitude());
            latitudes.add(location.getLatitude());
            Toast.makeText(this,"Coordinates added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Location empty",Toast.LENGTH_LONG).show();
        }
        /*LatLng latLng = new LatLng(currentLatitude,currentLongitude);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here now");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.d(TAG,location.toString());
        Toast.makeText(this,location.toString(),Toast.LENGTH_LONG).show();*/
    }

    public void InitialiseClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        LocationRequestCreate();

    }
    public  void LocationRequestCreate(){
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10*1000)
                .setFastestInterval(1*1000);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstaceStates) {
        savedInstaceStates.putStringArrayList("LIST",stopNames);
        super.onSaveInstanceState(savedInstaceStates);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this,"Location services connected",Toast.LENGTH_LONG).show();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (mCurrentLocation == null) {
               mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
            Log.i(TAG, "Location services connected.");
        }
        if (mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
            }catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }
        }else {
            Log.i(TAG,"Location services failed with code " +connectionResult.getErrorCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation=location;
        Toast.makeText(this, "location Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(nonRoot);
    }
}
