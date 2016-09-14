package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int quantity = 2;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        boolean hasWhippedCream = whipCreamChk();
        boolean hasChocolate = chocoChk();

        EditText addName =(EditText) findViewById(R.id.Name_EditText);
        String custName=addName.getText().toString();


        //   display(quantity);
        int price = calculatePrice(hasChocolate,hasWhippedCream);
        String priceMessage = createOrderSummary(price,hasWhippedCream,hasChocolate,custName);
        String subj ="Just Java Order for "+ custName;

        composeEmail(priceMessage,subj);

    }

    /**
     * Calculates the price of the order.
     * <p/>
     * quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean whip_c,boolean choco) {
        int basePrice =5;

        if (whip_c==true){
                    basePrice=basePrice+1;
        }

        if (choco==true){
            basePrice=basePrice+2;
        }

        int price = quantity * basePrice;

        return price;
    }

    public boolean whipCreamChk(){
        CheckBox whipCreamCheck = (CheckBox)findViewById(R.id.whipCream_Check);
        boolean hasWhippedCream = whipCreamCheck.isChecked();
        return hasWhippedCream;
    }

    public boolean chocoChk(){
        CheckBox chocolateCheck = (CheckBox)findViewById(R.id.chocolate_Check);
        boolean hasChocolate = chocolateCheck.isChecked();
        return hasChocolate;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        if (quantity > 0) {
            displayQuantity(quantity);

        } else {
            quantity = 0;
            displayQuantity(quantity);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private String createOrderSummary(int price, boolean hwc,boolean hc, String name) {
        String Summary = "Name: " + name  + "\nhas Whipped Cream?: "+ hwc +"\nhas Chocolate?: "+ hc+"\nQuantity: " + quantity + "\nTotal: $" + price +  "\nThank You!";
        return Summary;
    }


    public void composeEmail(String text, String subj){
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.putExtra(Intent.EXTRA_SUBJECT,subj);
        if(intent.resolveActivity(getPackageManager())!=null
                ){
            startActivity(intent);
        }

    }
}