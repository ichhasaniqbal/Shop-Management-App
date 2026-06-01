package com.example.shopmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ShopManagement.db";
    private static final int DATABASE_VERSION = 2;

    // 1. Customer Table
    public static final String TABLE_CUSTOMER = "Customer";
    public static final String COL_CUSTOMER_ID = "CustomerID";
    public static final String COL_CUSTOMER_NAME = "Name";
    public static final String COL_CUSTOMER_CONTACT = "ContactInfo";
    public static final String COL_CUSTOMER_ADDRESS = "Address";

    // 2. Product Table
    public static final String TABLE_PRODUCT = "Product";
    public static final String COL_PRODUCT_ID = "ProductID";
    public static final String COL_PRODUCT_NAME = "Name";
    public static final String COL_PRODUCT_DESC = "Description";
    public static final String COL_PRODUCT_PRICE = "Price";
    public static final String COL_PRODUCT_STOCK = "StockQuantity";

    // 3. Admin Table
    public static final String TABLE_ADMIN = "Admin";
    public static final String COL_ADMIN_ID = "AdminID";
    public static final String COL_ADMIN_NAME = "Name";
    public static final String COL_ADMIN_CNIC = "CNIC";
    public static final String COL_ADMIN_SHOP = "ShopName";
    public static final String COL_ADMIN_PASSWORD = "Password";

    // 4. Transaction Table
    public static final String TABLE_TRANSACTION = "Transactions";
    public static final String COL_TRANS_ID = "TransactionID";
    public static final String COL_TRANS_CUST_ID = "CustomerID";
    public static final String COL_TRANS_PROD_ID = "ProductID";
    public static final String COL_TRANS_QTY = "Quantity";
    public static final String COL_TRANS_DATE = "Date";
    public static final String COL_TRANS_TOTAL = "TotalAmount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + " ("
                + COL_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CUSTOMER_NAME + " TEXT NOT NULL, "
                + COL_CUSTOMER_CONTACT + " TEXT NOT NULL, "
                + COL_CUSTOMER_ADDRESS + " TEXT)";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + " ("
                + COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_PRODUCT_NAME + " TEXT NOT NULL, "
                + COL_PRODUCT_DESC + " TEXT, "
                + COL_PRODUCT_PRICE + " REAL NOT NULL, "
                + COL_PRODUCT_STOCK + " INTEGER NOT NULL)";

        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + " ("
                + COL_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ADMIN_NAME + " TEXT NOT NULL, "
                + COL_ADMIN_CNIC + " TEXT NOT NULL UNIQUE, "
                + COL_ADMIN_SHOP + " TEXT NOT NULL, "
                + COL_ADMIN_PASSWORD + " TEXT NOT NULL)";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + " ("
                + COL_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRANS_CUST_ID + " INTEGER NOT NULL, "
                + COL_TRANS_PROD_ID + " INTEGER NOT NULL, "
                + COL_TRANS_QTY + " INTEGER NOT NULL, "
                + COL_TRANS_DATE + " TEXT NOT NULL, "
                + COL_TRANS_TOTAL + " REAL NOT NULL)";

        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }

    // --- CRUD METHODS ---

    public boolean addAdmin(String name, String cnic, String shopName, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ADMIN_NAME, name);
        contentValues.put(COL_ADMIN_CNIC, cnic);
        contentValues.put(COL_ADMIN_SHOP, shopName);
        contentValues.put(COL_ADMIN_PASSWORD, password);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_ADMIN, null, contentValues);
        return result != -1;
    }

    public boolean checkAdminLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE " + COL_ADMIN_NAME + " = ? AND " + COL_ADMIN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public boolean addProduct(String name, String description, double price, int stockQuantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRODUCT_NAME, name);
        contentValues.put(COL_PRODUCT_DESC, description);
        contentValues.put(COL_PRODUCT_PRICE, price);
        contentValues.put(COL_PRODUCT_STOCK, stockQuantity);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_PRODUCT, null, contentValues);
        return result != -1;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_PRODUCT_ID + " AS _id, " + COL_PRODUCT_NAME + ", " + COL_PRODUCT_PRICE + ", " + COL_PRODUCT_STOCK + " FROM " + TABLE_PRODUCT;
        return db.rawQuery(query, null);
    }

    public boolean addCustomer(String name, String contactInfo, String address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CUSTOMER_NAME, name);
        contentValues.put(COL_CUSTOMER_CONTACT, contactInfo);
        contentValues.put(COL_CUSTOMER_ADDRESS, address);
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_CUSTOMER, null, contentValues);
        return result != -1;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_CUSTOMER_ID + " AS _id, " + COL_CUSTOMER_NAME + ", " + COL_CUSTOMER_CONTACT + " FROM " + TABLE_CUSTOMER;
        return db.rawQuery(query, null);
    }

    // Recording a Transaction & Automating Inventory Stock Reduction
    public int recordTransaction(int customerId, int productId, int quantity, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        String prodQuery = "SELECT " + COL_PRODUCT_PRICE + ", " + COL_PRODUCT_STOCK + " FROM " + TABLE_PRODUCT + " WHERE " + COL_PRODUCT_ID + " = ?";
        Cursor cursor = db.rawQuery(prodQuery, new String[]{String.valueOf(productId)});

        if (cursor != null && cursor.moveToFirst()) {
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE));
            int currentStock = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_STOCK));
            cursor.close();

            if (currentStock < quantity) {
                return 0; // Insufficient Stock
            }

            double totalAmount = quantity * price;

            ContentValues cv = new ContentValues();
            cv.put(COL_TRANS_CUST_ID, customerId);
            cv.put(COL_TRANS_PROD_ID, productId); // Fixed syntax line
            cv.put(COL_TRANS_QTY, quantity);
            cv.put(COL_TRANS_DATE, date);
            cv.put(COL_TRANS_TOTAL, totalAmount);

            long transResult = db.insert(TABLE_TRANSACTION, null, cv);

            if (transResult != -1) {
                int updatedStock = currentStock - quantity;
                ContentValues productCv = new ContentValues();
                productCv.put(COL_PRODUCT_STOCK, updatedStock);

                db.update(TABLE_PRODUCT, productCv, COL_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});
                return 1; // Success
            }
        }
        return -1; // General Error
    }

    // Updated JOIN query to include Product Price and prevent layout crash
    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT t." + COL_TRANS_ID + " AS _id, "
                + "c." + COL_CUSTOMER_NAME + " AS customer_name, "
                + "p." + COL_PRODUCT_NAME + " AS product_name, "
                + "p." + COL_PRODUCT_PRICE + ", " // LAAZMI: Yeh column shamil kiya crash fix karne ke liye
                + "t." + COL_TRANS_QTY + ", "
                + "t." + COL_TRANS_TOTAL + ", "
                + "t." + COL_TRANS_DATE
                + " FROM " + TABLE_TRANSACTION + " t "
                + " JOIN " + TABLE_CUSTOMER + " c ON t." + COL_TRANS_CUST_ID + " = c." + COL_CUSTOMER_ID
                + " JOIN " + TABLE_PRODUCT + " p ON t." + COL_TRANS_PROD_ID + " = p." + COL_PRODUCT_ID
                + " ORDER BY t." + COL_TRANS_ID + " DESC";
        return db.rawQuery(query, null);
    }
}