package com.example.shopmanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductDesc, etProductPrice, etProductStock;
    private Button btnSaveProduct, btnCancelProduct;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Database helper initialize
        dbHelper = new DatabaseHelper(this);

        // Bind UI Elements
        etProductName = findViewById(R.id.etProductName);
        etProductDesc = findViewById(R.id.etProductDesc);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductStock = findViewById(R.id.etProductStock);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnCancelProduct = findViewById(R.id.btnCancelProduct);

        // Cancel Button - Wapas Inventory screen par bhejein
        btnCancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Save Product Button Click Logic (As per SDD Pseudo-code)
        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etProductName.getText().toString().trim();
                String desc = etProductDesc.getText().toString().trim();
                String priceStr = etProductPrice.getText().toString().trim();
                String stockStr = etProductStock.getText().toString().trim();

                // 1. Validation for empty fields (As per SRS Alternative Flow)
                if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double price = Double.parseDouble(priceStr);
                int stock = Integer.parseInt(stockStr);

                // 2. Business Rule Validation: Price must be positive (As per SRS BR-2)
                if (price <= 0) {
                    Toast.makeText(AddProductActivity.this, "Price must be a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. Database Insertion (As per SDD Algorithm)
                boolean isInserted = dbHelper.addProduct(name, desc, price, stock);

                if (isInserted) {
                    Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Wapas inventory par chalein
                } else {
                    Toast.makeText(AddProductActivity.this, "Error adding product! Name might already exist.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}