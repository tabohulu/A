package com.example.android.awaybustrotro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity  {
    String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"};
    String[] currentArray;
    String[] mobileArrayInv = {"Max OS X", "Windows7", "Ubuntu","WebOS","Blackberry","WindowsMobile","IPhone","Android"};
    boolean[] checked={false,false,false,false,false,false,false,false};
    String[] inputs = {"", "", "", "", "", "", "", ""};
    public String passengerStops ;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        selectedItem="Tech -> Kejetia";
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.journey_options, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem= parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(),selectedItem,Toast.LENGTH_SHORT).show();
                ListAdapter adapter = new CustomAdaptNew(parent.getContext(), chooseArray(selectedItem));
                ListView listview = (ListView) findViewById(R.id.stops_list);
                assert listview != null;
                listview.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ListAdapter adapter = new CustomAdaptNew(this, chooseArray(selectedItem));
        //ListAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,mobileArray);

        ListView listview = (ListView) findViewById(R.id.stops_list);
        assert listview != null;
        listview.setAdapter(adapter);
        listview.setItemsCanFocus(true);


        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String phone = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Main2Activity.this, phone, Toast.LENGTH_LONG).show();
                        //  EditText editText = (EditText)findViewById(R.id.custEditText);
                        // CheckBox checkBox =(CheckBox)findViewById(R.id.cusCheckBox) ;
                        //String string = editText.getText().toString();

                    }
                }
        );


    }

    public String inputInfo(String[] stops)
    {
        String info="";
        for(int i=0;i<stops.length;i++){
            info+=stops[i]+"\n";
        }
        return info;
    }

    public void setpassinputs(){
        passengerStops="";
        for(int i=0;i<inputs.length;i++){
            if(!inputs[i].equals("")){
                passengerStops+=inputs[i]+"\n";
            }else {
                passengerStops="";
            }
        }
    }

   public void toSummary(View view)
    {
        Intent intent = new Intent(this, UserInputSummary.class);
        String msg=inputInfo(mobileArray);
        setpassinputs();
        if (!passengerStops.equals("")){
            Bundle extras = new Bundle();
            extras.putString("MSG",msg);
            extras.putString("SDG",passengerStops);
            intent.putExtras(extras);
            startActivity(intent);
        }else {
            Toast.makeText(Main2Activity.this,"Empty inputs available",Toast.LENGTH_SHORT).show();
        }
    }

    public String[] chooseArray(String item){
        switch (item){
            case "Tech -> Kejetia":
                currentArray=mobileArray;
                break;
            case "Kejetia -> Tech":
                currentArray=mobileArrayInv;
                break;
        }
        return currentArray;
    }


    class CustomAdaptNew extends ArrayAdapter<String> {


        public CustomAdaptNew(Context context, String[] mobileArray1) {
            super(context, R.layout.custom_layout, mobileArray1);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View customV = inflater.inflate(R.layout.custom_layout, parent, false);
            ViewHolder holder = (ViewHolder) customV.getTag();
            if (holder == null) {
                holder = new ViewHolder(customV);
                customV.setTag(holder);
            }

            String singlePhone = getItem(position);
            TextView phoneTxtView = (TextView) customV.findViewById(R.id.custTextView);
            phoneTxtView.setText(singlePhone);

            final ViewHolder finalHolder = holder;
            holder.stopCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // status.set(position, true);
                        finalHolder.passengerInput.setVisibility(View.GONE);
                        checked[position]=true;
                        finalHolder.passengerInput.setText("0");
                    } else {
                        // status.set(position, false);
                        finalHolder.passengerInput.setVisibility(View.VISIBLE);
                        checked[position]=false;
                        finalHolder.passengerInput.setText("");

                    }

                }
            });
            holder.stopCheck.setChecked(checked[position]);

            holder.passengerInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    inputs[position]=s.toString();

                }
            });
            holder.passengerInput.setText(inputs[position]);
            return customV;
        }



    }

    class ViewHolder {
        EditText passengerInput = null;
        CheckBox stopCheck = null;
        // TextView phoneTxtView = null;

        ViewHolder(View view) {
            this.passengerInput = (EditText) view.findViewById(R.id.custEditText);
            this.stopCheck = (CheckBox) view.findViewById(R.id.cusCheckBox);
            // this.phoneTxtView = (TextView) view.findViewById(R.id.custEditText);
        }


    }
}
