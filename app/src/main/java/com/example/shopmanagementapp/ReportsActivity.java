package com.example.shopmanagementapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportsActivity extends AppCompatActivity {

    private ListView lvTransactions;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dbHelper = new DatabaseHelper(this);
        lvTransactions = findViewById(R.id.lvTransactions);

        loadTransactionReports();
    }

    private void loadTransactionReports() {
        Cursor cursor = dbHelper.getAllTransactions();

        String[] fromColumns = {
                "customer_name",
                "product_name"
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

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                // 1. Row 1: Customer Name (Bold Title)
                if (view.getId() == android.R.id.text1) {
                    int custIndex = cursor.getColumnIndexOrThrow("customer_name");
                    String custName = cursor.getString(custIndex);

                    TextView tv = (TextView) view;
                    tv.setText("Customer: " + custName);
                    tv.setTypeface(null, android.graphics.Typeface.BOLD);
                    return true;
                }

                // 2. Sub-Rows Control: Product, Qty, Total, and Date/Time on separate lines
                if (view.getId() == android.R.id.text2) {
                    int prodIndex = cursor.getColumnIndexOrThrow("product_name");
                    int qtyIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANS_QTY);
                    int totalIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANS_TOTAL);
                    int dateIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TRANS_DATE);
                    int priceIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRODUCT_PRICE);

                    String prodName = cursor.getString(prodIndex);
                    int qty = cursor.getInt(qtyIndex);
                    double total = cursor.getDouble(totalIndex);
                    String dateTimeStr = cursor.getString(dateIndex);
                    double unitPrice = cursor.getDouble(priceIndex);

                    TextView tv = (TextView) view;

                    // FIX: Using \n to break each property into its own distinct row/line
                    String detailedInvoice = "Product: " + prodName + " (Price: Rs. " + unitPrice + ")" +
                            "\nQuantity: " + qty +
                            "\nTotal Amount: Rs. " + total +
                            "\nDate & Time: " + dateTimeStr;

                    tv.setText(detailedInvoice);
                    // Line spacing thodi barha dete hain taake rows aapas mein juri hui na lagein
                    tv.setLineSpacing(4, 1f);
                    return true;
                }
                return false;
            }
        });

        lvTransactions.setAdapter(adapter);
    }
}