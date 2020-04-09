package com.meCactus;


public class SQL{


   // Database database;
    //static SQLiteDatabase database;
   // static ContentValues contentValues;

//
//    public SQL(Database othrtdbHelper) {
//
//        database = othrtdbHelper;
//        database = database.getWritableDatabase();
//        contentValues = new ContentValues();
//    }
//
//    private static boolean toBoolean(String name) {
//
//        return ((name != null) && name.equalsIgnoreCase("true"));
//    }

//    public void add(String text, String status){
//
//        contentValues.put(Database.TEXT, text);
//
//        contentValues.put(Database.STATUS, status);
//
//        database.insert(Database.TABLE_NAME, null, contentValues);
//    }
//    public void read(List<CardModel> listItems ){
//
//
//        Cursor cursor = database.query(Database.TABLE_NAME, null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//
//            int idIndex = cursor.getColumnIndex(Database.ID);
//            int textIndex = cursor.getColumnIndex(Database.TEXT);
//            int statusIndex = cursor.getColumnIndex(Database.STATUS);
//            System.out.println("------------"+idIndex);
//            System.out.println(textIndex);
//            System.out.println(statusIndex);
//
//            do {
//
//                CardModel listItem = new CardModel(cursor.getString(textIndex), toBoolean(cursor.getString(statusIndex)));
//                listItems.add(listItem);
//                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                        ", text = " + cursor.getString(textIndex) +
//                        ", status = " + cursor.getString(statusIndex));
//            } while (cursor.moveToNext());
//        } else
//            Log.d("mLog", "0 rows");
//
//        cursor.close();
//
//    }

    //public void clear(){
   //     database.delete(Database.TABLE_NAME, null, null);
  //  }


//    public void update(List<CardModel> listItems ){
//
//        clear();
//
//        for(CardModel listItem: listItems){
//            Boolean status = listItem.getChecked();
//            add(listItem.getText(),status.toString());
//        }
//    }

    public static final String KEY_ID = "_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_STATUS = "false";




//    public void remove() {
//        database.execSQL("create table  contacts (" + KEY_ID
//                + " integer primary key," + KEY_TEXT + " text," + KEY_STATUS + " text" + ")");
//
//    }



//            case act.Delete:
//                if (id.equalsIgnoreCase("")){
//                    break;
//                }
//                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);
//
//                Log.d("mLog", "deleted rows count = " + delCount);





}