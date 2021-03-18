package com.wolfinfinity.sqlitedata;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DB_NAME = "MyDatabase.DB";

    // database version
    private static final int DB_VERSION = 1;

    // Table Name
    private static final String TABLE_USER = "USER";

    // Column Name
    public static final String COL_ID = "id";
    public static final String COL_CREATED_DATE = "create_date";
    public static final String COL_MODIFIED_DATE = "modified_date";
    public static final String COL_FULL_NAME = "full_name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_PASSWORD = "password";

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_USER + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CREATED_DATE + " TEXT NOT NULL, " +
            COL_MODIFIED_DATE + " TEXT NOT NULL, " +
            COL_FULL_NAME + " TEXT NOT NULL, " +
            COL_EMAIL + " TEXT NOT NULL, " +
            COL_PHONE + " TEXT, " +
            COL_PASSWORD + " TEXT NOT NULL);";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertUser(String createDate, String modifiedDate, String fullName, String email, String phone, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CREATED_DATE, createDate);
        values.put(COL_MODIFIED_DATE, modifiedDate);
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_PASSWORD, password);
        database.insert(TABLE_USER, null, values);
        database.close();
        return true;
    }

    public boolean isValueExist(String value){
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + " = ?";
        String[] whereArgs = {value};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, whereArgs);

        int count = cursor.getCount();
        cursor.close();

        return count >= 1;
    }

    public boolean userLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?", new String[]{email,password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }
}
