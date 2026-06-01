package com.example.shopmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {

    private TextView tvInventoryTitle;
    private Button btnAddProduct;
    private ListView lvProducts;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Database helper initialize
        dbHelper = new DatabaseHelper(this);

        // Bind UI Elements
        tvInventoryTitle = findViewById(R.id.tvInventoryTitle);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        lvProducts = findViewById(R.id.lvProducts);

        // Add Product Button Click Listener
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        // Products load karein
        loadProductsFromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        Cursor cursor = dbHelper.getAllProducts();

        // Columns to read from database cursor
        String[] fromColumns = {
                DatabaseHelper.COL_PRODUCT_NAME,
                DatabaseHelper.COL_PRODUCT_STOCK // Temp field, binder overwrite kar dega
        };

        // Views to display data into
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

        // Custom formatting logic for price and stock display
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == android.R.id.text2) {
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRODUCT_PRICE));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRODUCT_STOCK));

                    TextView tv = (TextView) view;
                    tv.setText("Price: Rs. " + price + "  |  Stock: " + stock);
                    return true;
                }
                return false;
            }
        });

        lvProducts.setAdapter(adapter);
    }
}