package com.meCactus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database extends SQLiteOpenHelper{

    private static final String TAG = "DATABASE" ;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "conta3ctsD1.db";
    public static final String TABLE_NAME = "contacts";

    public static final String ID = "_id";
    public static final String TITLE = "_title";
    public static final String TEXT = "_text";
    public static final String STATUS = "_status";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + ID + " integer primary key," + TITLE + " text," + TEXT + " text," + STATUS + " text" + ")");


    }
    public void removeDatabase() {
        SQLiteDatabase.deleteDatabase(new File(DATABASE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void add(String title, String text, Boolean status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TITLE, title);
        contentValues.put(Database.TEXT, text);
        contentValues.put(Database.STATUS, status);

        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void read(List<CardModel> listItems ){
        listItems.clear();

        final SQLiteDatabase database = getWritableDatabase();
        Cursor cursor= database.query(TABLE_NAME, null,null,null,null,null,null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(Database.ID);
            int titleIndex = cursor.getColumnIndex(Database.TITLE);
            int textIndex = cursor.getColumnIndex(Database.TEXT);
            int statusIndex = cursor.getColumnIndex(Database.STATUS);

            do {
                CardModel listItem = new CardModel(cursor.getInt(idIndex), cursor.getString(titleIndex), cursor.getString(textIndex), cursor.getInt(statusIndex));
                listItems.add(listItem);
                Log.d(TAG, "ID = " + cursor.getInt(idIndex) +
                        ", title = " + cursor.getString(titleIndex) +
                        ", text = " + cursor.getString(textIndex) +
                        ", status = " + (Boolean.getBoolean(cursor.getString(statusIndex))));
            } while (cursor.moveToNext());

        } else
            Log.d(TAG, "0 rows");

       Collections.reverse(listItems);


        //Сортировка по ID столбцу
//        Collections.sort(listItems, new Comparator<CardModel>() {
//            @Override
//            public int compare(CardModel a, CardModel b) {
//                if(a.getId() < b.getId())
//                    return 1;
//                else if(a.getId() > b.getId())
//                    return -1;
//                else
//                    return 0;
//            }
//
//
//        });

        cursor.close();
        database.close();
    }

    public  void removeByTitle(String title){
        //удаление элемента с таким-то именем

        SQLiteDatabase database=getWritableDatabase();
        database.delete(TABLE_NAME, TITLE+ "=?", new String[]{title});
        database.close();
    }
    public  void removeById(int id){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(TABLE_NAME, ID+ "=?", new String[]{Integer.toString(id)});
        database.close();
    }
    public  void updateById(int id, String title,String text){

        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TITLE, title);
        contentValues.put(Database.TEXT, text);
        database.update(TABLE_NAME, contentValues,ID + "=?", new String[]{Integer.toString(id)});
        database.close();
    }

    public void swapById(int id1, int id2){

        SQLiteDatabase database=getWritableDatabase();

        CardModel cardModel1 = getCardModelbyId(id1);
        CardModel cardModel2 = getCardModelbyId(id2);

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TITLE, cardModel1.getTitle());
        contentValues.put(Database.TEXT, cardModel1.getText());
        contentValues.put(Database.STATUS, cardModel1.getCheck());

        database.update(TABLE_NAME, contentValues,ID+ "=?", new String[]{Integer.toString(id2)});

        contentValues.clear();
        contentValues.put(Database.TITLE, cardModel2.getTitle());
        contentValues.put(Database.TEXT, cardModel2.getText());
        contentValues.put(Database.STATUS, cardModel2.getCheck());

        database.update(TABLE_NAME, contentValues,ID+ "=?", new String[]{Integer.toString(id1)});

        database.close();
    }

    //Получить id i-го элемента
    public int getIdbyIndex(int index){
        final SQLiteDatabase database = getWritableDatabase();
        Cursor cursor= database.query(TABLE_NAME, null,null,null,null,null,null);

        int tmpIndex = 0;
        if (cursor.moveToFirst() && cursor.getCount() >= index) {

            int idIndex = cursor.getColumnIndex(Database.ID);


           while (tmpIndex < index){
               tmpIndex++;
               cursor.moveToNext();
               Log.d(TAG, "ID = " + cursor.getInt(idIndex));
           }

           return cursor.getInt(idIndex);

        } else {


            Log.d(TAG, "0 rows");
        }
        cursor.close();
        database.close();

        return -1;
    }

    public CardModel getCardModelbyId(int id){

        CardModel cardModel = null;

        Cursor cursor = getReadableDatabase().
                rawQuery("select * from " + TABLE_NAME + " where _id = ?", new String[]{Integer.toString(id)});
        if(cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(Database.ID);
            int titleIndex = cursor.getColumnIndex(Database.TITLE);
            int textIndex = cursor.getColumnIndex(Database.TEXT);
            int statusIndex = cursor.getColumnIndex(Database.STATUS);

            cardModel = new CardModel(cursor.getInt(idIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(textIndex),
                    cursor.getInt(statusIndex));
            cursor.close();

        }

        return cardModel;

    }

    public void clear(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(Database.TABLE_NAME, null, null);
      }


    public void update(List<CardModel> listItems ){

        clear();
        List<CardModel> tmplistItems = new ArrayList<>(listItems);
        Collections.reverse(tmplistItems);

        for(CardModel listItem: tmplistItems){
            add(listItem.getTitle(), listItem.getText(), listItem.getCheck());
        }
        tmplistItems.clear();

    }
}