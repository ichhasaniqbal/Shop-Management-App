package com.example.shopmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnInventory, btnSales, btnCustomers, btnReports, btnLogout;

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
        btnLogout = findViewById(R.id.btnLogout);

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

        // 5. MASTER FIX: Logout Click Listener (Clears ALL SharedPreferences Files)
        if (btnLogout != null) {
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DashboardActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();

                    try {
                        // 1. App ke shared_prefs folder ka direct path nikalna
                        File sharedPrefsDir = new File(getFilesDir().getParent(), "shared_prefs");
                        if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory()) {
                            String[] list = sharedPrefsDir.list();
                            if (list != null) {
                                for (String item : list) {
                                    // Har file ka naam nikal kar use poori tarah clear karna
                                    String prefName = item.replace(".xml", "");
                                    getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().clear().apply();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 2. Intent to go back to MainActivity (Login Screen)
                    Intent intent = new Intent(DashboardActivity.this, MainActivity.class);

                    // Clear Activity Stack so user cannot press back to return to Dashboard
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish(); // Destroy DashboardActivity
                }
            });
        }
    }
}