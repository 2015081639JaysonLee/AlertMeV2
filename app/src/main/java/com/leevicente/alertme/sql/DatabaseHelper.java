package com.leevicente.alertme.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leevicente.alertme.modal.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "AlertMe.db";

    private  static  final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_FIRSTNAME = "firstName";
    private static final String COLUMN_LASTNAME = "lastName";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_EMER_NAME = "emerName";
    private static final String COLUMN_EMER_CONTACT = "emerContact";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT NOT NULL, " +
            COLUMN_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
            COLUMN_LASTNAME + " TEXT NOT NULL, " +
            COLUMN_CONTACT + " TEXT NOT NULL, " +
            COLUMN_EMER_NAME + " TEXT NOT NULL, " +
            COLUMN_EMER_CONTACT + " TEXT NOT NULL);";

    private  String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);

        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_CONTACT, user.getContact());
        values.put(COLUMN_EMER_NAME, user.getEmerName());
        values.put(COLUMN_EMER_CONTACT, user.getEmerContact());
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<User> getUser(String username){
        String[] columns = {COLUMN_USER_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_USERNAME, COLUMN_PASSWORD,
                COLUMN_CONTACT, COLUMN_EMER_NAME, COLUMN_EMER_CONTACT};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?";

        String[] selectionArgs = {username};

        List<User> userList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT)));
                user.setEmerName(cursor.getString(cursor.getColumnIndex(COLUMN_EMER_NAME)));
                user.setEmerContact(cursor.getString(cursor.getColumnIndex(COLUMN_EMER_CONTACT)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, user.getFirstName());
        values.put(COLUMN_LASTNAME, user.getLastName());
        values.put(COLUMN_CONTACT, user.getContact());
        values.put(COLUMN_EMER_NAME, user.getEmerName());
        values.put(COLUMN_EMER_CONTACT, user.getEmerContact());
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return (cursorCount > 0);
    }

    public boolean checkUser(String username, String password){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return (cursorCount > 0);
    }
}