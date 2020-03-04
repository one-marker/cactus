package com.rival;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SQL{


    DBHelper dbHelper;
    static SQLiteDatabase database;
    static ContentValues contentValues;


    public SQL(DBHelper othrtdbHelper) {

        dbHelper = othrtdbHelper;
        database = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    private static boolean toBoolean(String name) {
        return ((name != null) && name.equalsIgnoreCase("true"));
    }

    public void add(String text, String status){

        contentValues.put(DBHelper.KEY_TEXT, text);

        contentValues.put(DBHelper.KEY_STATUS, status);

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
    }
    public List<CardModel> read(){

        List<CardModel> listItems = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int statusIndex = cursor.getColumnIndex(DBHelper.KEY_STATUS);

            do {

                CardModel listItem = new CardModel(cursor.getString(textIndex),toBoolean(cursor.getString(statusIndex)));
                listItems.add(listItem);
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", text = " + cursor.getString(textIndex) +
                        ", status = " + cursor.getString(statusIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");


        cursor.close();

        return listItems;

    }

    public void clear(){
        database.delete(DBHelper.TABLE_CONTACTS, null, null);
    }


    public void update(List<CardModel> listItems ){

        clear();

        for(CardModel listItem: listItems){
            Boolean status = listItem.getChecked();
            add(listItem.getText(),status.toString());
        }
    }



//            case act.Delete:
//                if (id.equalsIgnoreCase("")){
//                    break;
//                }
//                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);
//
//                Log.d("mLog", "deleted rows count = " + delCount);





}