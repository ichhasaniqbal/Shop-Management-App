package com.example.shopmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerActivity extends AppCompatActivity {

    private Button btnAddCustomer;
    private ListView lvCustomers;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        dbHelper = new DatabaseHelper(this);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        lvCustomers = findViewById(R.id.lvCustomers);

        // Add Customer Button click handler
        // Add Customer Button Click Listener (Updated)
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        loadCustomers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCustomers();
    }

    private void loadCustomers() {
        Cursor cursor = dbHelper.getAllCustomers();

        String[] fromColumns = {
                DatabaseHelper.COL_CUSTOMER_NAME,
                DatabaseHelper.COL_CUSTOMER_CONTACT
        };

        int[] toViews = {
                android.R.id.text1,
                android.R.id.text2
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                fromColumns,
                toViews,
                0
        );

        lvCustomers.setAdapter(adapter);
    }
}