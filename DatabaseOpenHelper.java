package com.example.bsachs_courseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;


public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final private static String CREATE_CMD =
            "CREATE TABLE colors (" + "_ID" +
                    " TEXT NOT NULL PRIMARY KEY, "
                    + "Red" + " INTEGER, " +
                    "Green" + " INTEGER, " +
                    "Blue" + " INTEGER, " +
                    "Name" + "TEXT NOT NULL )";
    final public static String DATABASE_NAME = "list_db";
    final private static Integer VERSION = 2;
    final private Context context;
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + "_ID";
    public DatabaseOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CMD);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS list");
        onCreate(db);
    }
    void deleteDatabase()
    {
        context.deleteDatabase(DATABASE_NAME);
    }
}