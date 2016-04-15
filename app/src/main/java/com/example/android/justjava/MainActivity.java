package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkBox_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.checkBox_chocolate);
        boolean hasChocolate = chocolate.isChecked();
        EditText nombre = (EditText) findViewById(R.id.edit_text_nombre);
        String nom = nombre.getText().toString();
        displayQuantity(quantity);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String PriceMessage = createOrderSumary(price, hasWhippedCream, hasChocolate, nom);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Orden de Just Java para: "+nom);
        intent.putExtra(Intent.EXTRA_TEXT, PriceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * //@param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean crema, boolean chocolate) {
        int price = 5;
        if(crema){
            price = price + 1;
        }
        if(chocolate){
            price = price + 2;
        }
        return price*quantity;
    }

    public void increment(View view){
        if(quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        }else {
            Toast.makeText(getApplicationContext(),"No puede pedir mas de 100 tazas de cafe",Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view){
        if(quantity >= 1) {
            quantity--;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(getApplicationContext(), "No se puede decrementar", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int cantidad) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + cantidad);
    }


    private String createOrderSumary(int price, boolean cream, boolean choco, String nom){
        String message = "Name: " + nom +
                "\nTiene Crema: "+ cream +
                "\nTiene chocolate: "+ choco +
                "\nCantidad: "+ quantity +
                "\nTotal: $"+ price +
                "\nGracias!";
        return message;
    }
}
