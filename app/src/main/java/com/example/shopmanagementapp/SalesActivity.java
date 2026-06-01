package com.example.shopmanagementapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity {

    private Spinner spCustomers, spProducts;
    private EditText etSalesQty;
    private Button btnRecordSale, btnCancelSale;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        dbHelper = new DatabaseHelper(this);

        spCustomers = findViewById(R.id.spCustomers);
        spProducts = findViewById(R.id.spProducts);
        etSalesQty = findViewById(R.id.etSalesQty);
        btnRecordSale = findViewById(R.id.btnRecordSale);
        btnCancelSale = findViewById(R.id.btnCancelSale);

        populateSpinners();

        btnCancelSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRecordSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtyStr = etSalesQty.getText().toString().trim();

                if (qtyStr.isEmpty()) {
                    Toast.makeText(SalesActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(qtyStr);
                if (quantity <= 0) {
                    Toast.makeText(SalesActivity.this, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor custCursor = (Cursor) spCustomers.getSelectedItem();
                Cursor prodCursor = (Cursor) spProducts.getSelectedItem();

                if (custCursor == null || prodCursor == null) {
                    Toast.makeText(SalesActivity.this, "Make sure you have added a Product and Customer first!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Database column se actual dynamic hidden IDs uthane ka tarika
                int customerId = custCursor.getInt(custCursor.getColumnIndexOrThrow("_id"));
                int productId = prodCursor.getInt(prodCursor.getColumnIndexOrThrow("_id"));

                // FIX: .toUpperCase() laga diya taake AM/PM hamesha capital letters mein aaye
                String currentDate = new SimpleDateFormat("yyyy-MM-dd  |  hh:mm a", Locale.getDefault()).format(new Date()).toUpperCase();

                // Record transaction call
                int status = dbHelper.recordTransaction(customerId, productId, quantity, currentDate);

                if (status == 1) {
                    Toast.makeText(SalesActivity.this, "Transaction recorded successfully", Toast.LENGTH_SHORT).show(); // As per SDD pseudo-code
                    finish();
                } else if (status == 0) {
                    Toast.makeText(SalesActivity.this, "Insufficient Inventory for this product!", Toast.LENGTH_LONG).show(); // As per SRS Alternative Flow
                } else {
                    // Agar ab bhi fail ho to exact IDs toast mein show hon taake humen pata chale backend par kya ja raha hai
                    Toast.makeText(SalesActivity.this, "DB Error! Sent CustID: " + customerId + " | ProdID: " + productId, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void populateSpinners() {
        Cursor resCustomers = dbHelper.getAllCustomers();
        SimpleCursorAdapter custAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                resCustomers,
                new String[]{DatabaseHelper.COL_CUSTOMER_NAME},
                new int[]{android.R.id.text1},
                0
        );
        custAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomers.setAdapter(custAdapter);

        Cursor resProducts = dbHelper.getAllProducts();
        SimpleCursorAdapter prodAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                resProducts,
                new String[]{DatabaseHelper.COL_PRODUCT_NAME},
                new int[]{android.R.id.text1},
                0
        );
        prodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducts.setAdapter(prodAdapter);
    }
}