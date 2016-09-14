package com.example.android.awaybustrotro2;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/*
* problems
* unwanted checkbokes checked
* entered values duplicate
* */
public class TestAdapt extends ListActivity {
    private TextView selcs;
   //private CheckBox checkBox;
    //private EditText editText;

    private static final String[] items = {"aa", "bb", "cc", "bb", "cc", "bb", "cc", "bb", "cc","aa"};
   private ArrayList<String> stops = new ArrayList<String>();
    private ArrayList<String> stopsTemp = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapt);
        setListAdapter(new editTextAdapter());
        selcs = (TextView) findViewById(R.id.sel);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // super.onListItemClick(l, v, position, id);
        selcs.setText(items[position]);
    }

    class editTextAdapter extends ArrayAdapter<String> {
        editTextAdapter() {
            super(TestAdapt.this, R.layout.custom_layout, R.id.custTextView, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View customView = super.getView(position, convertView, parent);
            for (int i=0;i<items.length;i++){
                stops.add(i,"");}
            for (int i=0;i<items.length;i++){
                stopsTemp.add(i,"");}
              final CheckBox checkBox =(CheckBox)customView.findViewById(R.id.cusCheckBox);
            final EditText editText =(EditText)customView.findViewById(R.id.custEditText);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // status.set(position, true);
                        editText.setVisibility(View.GONE);
                        editText.setText("0");
                        Toast.makeText(TestAdapt.this,"Not stopping at "+items[position],Toast.LENGTH_SHORT).show();
                    } else {
                        // status.set(position, false);
                        editText.setVisibility(View.VISIBLE);
                        editText.setText("");
                        Toast.makeText(TestAdapt.this,"Stopping at "+items[position],Toast.LENGTH_SHORT).show();

                    }

                }
            });
            editText.requestFocus();
           // editText.setText(stopsTemp.get(position));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    stops.add(position,s.toString());

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                stops.add(position,s.toString());
                    //Toast.makeText(TestAdapt.this,stops.get(position)+" people Stopping at "+items[position],Toast.LENGTH_SHORT).show();
                }
            });

            return customView;
        }
    }
}
