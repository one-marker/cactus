package com.rival;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rival.GroceryContract.GroceryEntry;

import static com.rival.SQL.act.*;
import static com.rival.SQL.act.Clear;
import static com.rival.SQL.act.Read;


public class SQL{


    DBHelper dbHelper;
    static SQLiteDatabase database;
    static ContentValues contentValues;

    enum act {Add, Read, Clear, Delete};

    public SQL(DBHelper othrtdbHelper) {

        dbHelper = othrtdbHelper;
        database = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    public void action(act action)
    {
            switch (action) {

            case Add:
                contentValues.put(DBHelper.KEY_TEXT, "5");
                contentValues.put(DBHelper.KEY_STATUS, "true");

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                break;

            case Read:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_STATUS);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();
                break;

            case Clear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
        }



//            case act.Delete:
//                if (id.equalsIgnoreCase("")){
//                    break;
//                }
//                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);
//
//                Log.d("mLog", "deleted rows count = " + delCount);


        }


}