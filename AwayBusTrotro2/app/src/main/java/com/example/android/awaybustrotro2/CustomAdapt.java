package com.example.android.awaybustrotro2;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bohulu on 21/07/2016.
 */

class CustomAdapt extends ArrayAdapter<String> {

    private HashMap<String, String> textValues = new HashMap<>();

    public CustomAdapt(Context context, String[] mobileArray1) {
        super(context, R.layout.custom_layout, mobileArray1);
       /* for (int i = 0; i < mobileArray1.length; i++) {
            status.add(false);
        }*/
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customV = inflater.inflate(R.layout.custom_layout, parent, false);



        String singlePhone = getItem(position);
        TextView phoneTxtView = (TextView) customV.findViewById(R.id.custTextView);
        final EditText passengerInput = (EditText) customV.findViewById(R.id.custEditText);
        passengerInput.addTextChangedListener(new GenericTextWatcher(passengerInput));
        passengerInput.setTag("theFirstEditTextAtPos:"+position);


        CheckBox stopCheck = (CheckBox) customV.findViewById(R.id.cusCheckBox);
        stopCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    // status.set(position, true);
                    passengerInput.setVisibility(View.GONE);
                    passengerInput.setText("0");
                } else {
                    // status.set(position, false);
                    passengerInput.setVisibility(View.VISIBLE);
                    passengerInput.setText("");

                }
            }
        });


        phoneTxtView.setText(singlePhone);
        passengerInput.setHint("1");

        return customV;
    }
    class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            //save the value for the given tag :
            CustomAdapt.this.textValues.put((String) view.getTag(), editable.toString());
        }
    }
    public String getValueFromFirstEditText(int position){
        //here you need to recreate the id for the first editText
        String result = textValues.get("theFirstEditTextAtPos:"+position);
        if(result ==null)
            result = "default value";

        return result;
    }
}
