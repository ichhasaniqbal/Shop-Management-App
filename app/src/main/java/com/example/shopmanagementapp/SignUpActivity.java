package com.example.shopmanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etSignUpName, etSignUpCnic, etSignUpShopName, etSignUpPassword;
    private Button btnSignUpSubmit, btnSignUpCancel;
    private DatabaseHelper dbHelper; // DatabaseHelper ka instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Database initialized
        dbHelper = new DatabaseHelper(this);

        etSignUpName = findViewById(R.id.etSignUpName);
        etSignUpCnic = findViewById(R.id.etSignUpCnic);
        etSignUpShopName = findViewById(R.id.etSignUpShopName);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        btnSignUpCancel = findViewById(R.id.btnSignUpCancel);

        btnSignUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etSignUpName.getText().toString().trim();
                String cnic = etSignUpCnic.getText().toString().trim();
                String shopName = etSignUpShopName.getText().toString().trim();
                String password = etSignUpPassword.getText().toString().trim();

                // Validation
                if (name.isEmpty() || cnic.isEmpty() || shopName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Database mein Admin data insert karein
                    boolean isInserted = dbHelper.addAdmin(name, cnic, shopName, password);

                    if (isInserted) {
                        Toast.makeText(SignUpActivity.this, "Admin Registered Successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Wapas login screen par bhejen
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed! CNIC might already exist.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}