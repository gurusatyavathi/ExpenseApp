package com.example.team3_expenseapp.utilities;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "expense_manager.db";
    public static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TRANSACTIONS = "transactions";

    // Common column names
    public static final String KEY_ID = "id";

    // Users table column names
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    // Transactions table column names
    public static String KEY_TYPE = "type";
    public static String KEY_CATEGORY = "category";
    public static String KEY_AMOUNT = "amount";
    public static String KEY_NOTE = "note";
    public static String KEY_DATE = "date";
    public static String KEY_DELETE = "is_deleted";
    public static String KEY_USER_ID = "userId";

    // Create table statements
    // user table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT NOT NULL," +
            KEY_EMAIL + " TEXT NOT NULL UNIQUE," +
            KEY_PASSWORD + " TEXT NOT NULL)";

    // transaction table
    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TYPE + " TEXT NOT NULL," +
            KEY_CATEGORY + " TEXT NOT NULL," +
            KEY_AMOUNT + " INTEGER NOT NULL," +
            KEY_NOTE + " TEXT," +
            KEY_DATE + " TEXT NOT NULL," +
            KEY_DELETE + " INTEGER DEFAULT 0," +
            KEY_USER_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

        // Create new tables
        onCreate(db);
}
}