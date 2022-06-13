package com.hou.hcsandroidwork;

/**
 * Created by 86186 on 2022/6/6.
 */

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hcsWork.db";

    private static final String USERS = "users";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO Auto-generated method stub
        String sql = "create table users("+
                "user_id integer primary key,"+
                "user_name text,"+
                "user_password text,"+
                "user_phone text)";
        sqLiteDatabase.execSQL(sql);
    }
    //插入用户
    public void insert_user(User user){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        //Log.d("文件位置",sqLiteDatabase.getPath());
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUser_name());
        values.put("user_password", user.getUser_password());
        values.put("user_phone", user.getUser_phone());
        sqLiteDatabase.insert("users", null, values);
        sqLiteDatabase.close();
    }
    public int update_user(User user){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String user_name= user.getUser_name();
        ContentValues values = new ContentValues();
        values.put("user_password", user.getUser_password());
        values.put("user_phone", user.getUser_phone());
        int result=sqLiteDatabase.update("users",values,"user_name=?",new String[]{user_name});
        sqLiteDatabase.close();
        return result;

    }

    @SuppressLint("Range")
    public Cursor query_user(String user_name){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("users",null,
                "user_name LIKE ?", new String[]{"%" + user_name + "%"},
                null, null, null, null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}