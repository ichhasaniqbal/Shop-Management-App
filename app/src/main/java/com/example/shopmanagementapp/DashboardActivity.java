package com.example.shopmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnInventory, btnSales, btnCustomers, btnReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // UI Elements Initialization
        tvWelcome = findViewById(R.id.tvWelcome);
        btnInventory = findViewById(R.id.btnInventory);
        btnSales = findViewById(R.id.btnSales);
        btnCustomers = findViewById(R.id.btnCustomers);
        btnReports = findViewById(R.id.btnReports);

        // 1. Inventory Management Click Listener
        if (btnInventory != null) {
            btnInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "Opening Inventory...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, InventoryActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. Sales Management Click Listener
        if (btnSales != null) {
            btnSales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "Opening Sales...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, SalesActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 3. Customer Management Click Listener
        if (btnCustomers != null) {
            btnCustomers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "Opening Customers...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, CustomerActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 4. Reports & Analytics Click Listener
        if (btnReports != null) {
            btnReports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "Opening Reports...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, ReportsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}