package com.example.pustikom.adapterplay.com.example.pustikom.adapter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

/**
 * Created by UCode on 11/11/2016.
 */

public class TeacherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="college.db";
    public static final int DATABASE_VERSION=1;

    public StudentDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE" + TeacherContract.TABLE_NAME + " " +
                TeacherContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
                TeacherContract.COLUMN_NIP + "TEXT NOT NULL" +
                TeacherContract.COLUMN_NAME + "TEXT NOT NULL" +
                TeacherContract.COLUMN_MAIL + "TEXT" +
                TeacherContract.COLUMN_PHONE + "TEXT";
        db.exe
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }

    public void insertStudent (SQLiteDatabase db, Teacher teacher){
        ContentValues values = new ContentValues();
        values.put(TeacherContract.COLUMN_NIP, teacher.getNip());
        values.put(TeacherContract.COLUMN_NAME, teacher.getName());
        values.put(TeacherContract.COLUMN_MAIL, teacher.getMail());
        values.put(TeacherContract.COLUMN_PHONE, teacher.getPhone());
        db.insert(TeacherContract.TABLE_NAME, null, values);
    }

    public void updateStudent (SQLiteDatabase db, Teacher teacher){
        ContentValues values = new ContentValues();
        values.put(TeacherContract.COLUMN_NIP, teacher.getNip());
        values.put(TeacherContract.COLUMN_NAME, teacher.getName());
        values.put(TeacherContract.COLUMN_MAIL, teacher.getMail());
        values.put(TeacherContract.COLUMN_PHONE, teacher.getPhone());
        String condition = StudentContract._ID + "= ?";
        String[] conditionArgs = {teacher.getId() + " "};
        db.update(TeacherContract.TABLE_NAME, values, condition, conditionArgs);
    }

    public void deleteTeacher (SQLiteDatabase db, Student student){

    }

}

