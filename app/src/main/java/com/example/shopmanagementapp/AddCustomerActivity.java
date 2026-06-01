package com.example.shopmanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText etCustomerName, etCustomerContact, etCustomerAddress;
    private Button btnSaveCustomer, btnCancelCustomer;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        // Database helper initialize
        dbHelper = new DatabaseHelper(this);

        // Bind UI Elements
        etCustomerName = findViewById(R.id.etCustomerName);
        etCustomerContact = findViewById(R.id.etCustomerContact);
        etCustomerAddress = findViewById(R.id.etCustomerAddress);
        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);
        btnCancelCustomer = findViewById(R.id.btnCancelCustomer);

        // Cancel Button - Wapas Customer Directory screen par bhejein
        btnCancelCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Save Customer Button Click Logic (As per SDD Pseudo-code)
        btnSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCustomerName.getText().toString().trim();
                String contact = etCustomerContact.getText().toString().trim();
                String address = etCustomerAddress.getText().toString().trim();

                // Validation for empty required fields
                if (name.isEmpty() || contact.isEmpty()) {
                    Toast.makeText(AddCustomerActivity.this, "Name and Contact Info are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Database Insertion (As per SDD Algorithm)
                boolean isInserted = dbHelper.addCustomer(name, contact, address);

                if (isInserted) {
                    Toast.makeText(AddCustomerActivity.this, "Customer added successfully", Toast.LENGTH_SHORT).show(); // As per SDD pseudo-code return text
                    finish(); // Wapas Customer directory activity par chalein
                } else {
                    Toast.makeText(AddCustomerActivity.this, "Error adding customer", Toast.LENGTH_SHORT).show(); // As per SDD pseudo-code return text
                }
            }
        });
    }
}