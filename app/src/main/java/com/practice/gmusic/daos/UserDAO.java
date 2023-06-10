package com.practice.gmusic.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.gmusic.entities.User;
import com.practice.gmusic.others.DBHelper;

import java.io.Serializable;

public class UserDAO{

    DBHelper dbHelper;
    SQLiteDatabase database;

    public UserDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public User checkLogin(String mail, String pass){
        String SQL = "SELECT * FROM User WHERE mail=? AND pass=?";
        database = dbHelper.getReadableDatabase();
        User user = new User();
        Cursor cursor = database.rawQuery(SQL,new String[]{mail,pass});
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                user.setId(cursor.getInt(0));
                user.setMail(cursor.getString(1));
                user.setPass(cursor.getString(2));
                user.setName(cursor.getString(3));
                return user;
            }
        }
        return null;
    }

    public long insert(User user){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mail",user.getMail());
        values.put("pass",user.getPass());
        values.put("name",user.getName());
        return database.insert("User",null,values);
    }

    public long update(User user){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mail",user.getMail());
        values.put("pass",user.getPass());
        values.put("name",user.getName());
        return database.update("User",values,"uid=?",
                new String[]{String.valueOf(user.getId())});
    }
}

