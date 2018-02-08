package com.example.android.organizesifynewtry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tahir on 19.11.2017.
 */

class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TODOTASKS";

    // Labels table name
    private static final String TABLE_TASKS = "tasks";

    // Labels Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";



    public DatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        onCreate(db);
    }



    public void insertTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task);


        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getReadableDatabase();


        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE " + KEY_NAME + "=\"" + task + "\";");

    }


    public List<String> getAllTasks(){
        List<String> tasks = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                tasks.add(cursor.getString(1));

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tasks;
    }


}
