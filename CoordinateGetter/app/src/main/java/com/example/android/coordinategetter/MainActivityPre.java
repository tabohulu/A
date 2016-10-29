package com.example.android.coordinategetter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.google.android.gms.analytics.internal.zzy.A;
import static com.google.android.gms.analytics.internal.zzy.e;
import static com.google.android.gms.analytics.internal.zzy.i;
import static com.google.android.gms.analytics.internal.zzy.s;

public class MainActivityPre extends AppCompatActivity {

    public ArrayList areaName;
    public static final String PREFS_NAME="PREFS_PreMain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        areaName=new ArrayList();


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int size=settings.getInt("size",0);
        for(int i=0;i<size;i++) {
            areaName.add(i, settings.getString("area_"+i, "error"));
        }
        PopulateListView(areaName);

    }

    public void AddArea(View view){
        BuildDialogBox();
        //finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        for(int i=0;i<areaName.size();i++) {
            editor.putString("area_"+i,areaName.get(i).toString() );
        }
        editor.putInt("size",areaName.size());

        // Commit the edits!
        editor.commit();



    }

    public void BuildDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name of area");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userIP=input.getText().toString();
                areaName.add(userIP);
                PopulateListView(areaName);

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

    public void PopulateListView(ArrayList arrayList){
        if(arrayList!=null){
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
            ListView listView =(ListView)findViewById(R.id.area_names);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String phone = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(MainActivityPre.this, phone, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(view.getContext(),MainActivity.class);
                    intent.putExtra("AREA",phone);
                    startActivity(intent);

                }
            });


        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList("AREAS",areaName);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        areaName = savedInstanceState.getStringArrayList("AREAS");
        PopulateListView(areaName);
    }
}
