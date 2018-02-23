package com.example.cjhay.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJhay on 02/23/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    //database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "test";

    //TABLE
    private static final String TABLE_NAME = "person_table";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    //CRUD PERSON
    public void addPerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,person.getName());
        values.put(KEY_EMAIL,person.getEmail());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public int updatePerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,person.getName());
        values.put(KEY_EMAIL,person.getEmail());
        return db.update(TABLE_NAME, values, KEY_ID + " =?", new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID + " =?", new String[]{String.valueOf(person.getId())});
        db.close();
    }

    public Person getPerson(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{KEY_ID,KEY_NAME,KEY_EMAIL}, KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();
        return new Person(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
    }

    public List<Person> getAllPerson()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Person> listPersons = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            do
            {
                Person person = new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setEmail(cursor.getString(2));

                listPersons.add(person);
            }
            while (cursor.moveToNext());
        }
        return listPersons;
    }
}
