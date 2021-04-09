package com.example.login_practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseStore extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "practice.db";
    public static final int DATABASE_VERSION = 10;

    public static final String TABLE_NAME = "login";
    public static final String TABLE_LOGIN = "loginusers";

    public static final String COLUMN_LOGIN_STATE = "userstate";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


    Context context;

    public DatabaseStore(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " TEXT," +
                COLUMN_SURNAME + " TEXT," +
                COLUMN_PHONE + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_LOGIN + "(" +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_LOGIN_STATE + " TEXT )");

        ContentValues userLogin = new ContentValues();
        userLogin.put(COLUMN_LOGIN_STATE, "no");

        db.insert(TABLE_LOGIN, null, userLogin);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        onCreate(db);
    }




}
