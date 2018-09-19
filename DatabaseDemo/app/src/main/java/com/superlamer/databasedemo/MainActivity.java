package com.superlamer.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3 ))");
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");
            sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Rado', 34)");
            sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Tomy', 4)");
            sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Victor', 1)");
            sqLiteDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Patricia', 21)");

//            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE age < 18", null);

//            sqLiteDatabase.execSQL("DELETE FROM newUsers WHERE name = 'Victor' LIMIT 1");
//            sqLiteDatabase.execSQL("UPDATE newUsers SET age = 2 WHERE name = 'Rado'");
            sqLiteDatabase.execSQL("DELETE FROM newUsers WHERE id = 15");

            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM newUsers WHERE name = 'Victor'", null);
            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");
            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();

            while (c.moveToNext()) {
                Log.i("name", c.getString(nameIndex));
                Log.i("age", String.valueOf(c.getInt(ageIndex)));
                Log.i("id", String.valueOf(c.getInt(idIndex)));
            }

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("events", MODE_PRIVATE, null);
//            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");
//            sqLiteDatabase.execSQL("INSERT INTO events (name, year) VALUES ('Bulgaria estblished', 681)");
//            sqLiteDatabase.execSQL("INSERT INTO events (name, year) VALUES ('Bulgaria liberations', 1878)");
//
//            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM events", null);
//            int nameIndex = c.getColumnIndex("name");
//            int yearIndex = c.getColumnIndex("year");
//
//            while (c != null) {
//
//                Log.i("Event", c.getString(nameIndex));
//                Log.i("Year", String.valueOf(c.getInt(yearIndex)));
//
//
//                c.moveToNext();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
