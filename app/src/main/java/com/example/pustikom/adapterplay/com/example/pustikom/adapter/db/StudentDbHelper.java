package com.example.pustikom.adapterplay.com.example.pustikom.adapter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

/**
 * Created by UCode on 11/11/2016.
 */

public class StudentDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="college.db";
    public static final int DATABASE_VERSION=1;

    public StudentDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE" + StudentContract.TABLE_NAME + " " +
                StudentContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
                StudentContract.COLUMN_NIM + "TEXT NOT NULL" +
                StudentContract.COLUMN_NAME + "TEXT NOT NULL" +
                StudentContract.COLUMN_GENDER + "INTEGER" +
                StudentContract.COLUMN_MAIL + "TEXT" +
                StudentContract.COLUMN_PHONE + "TEXT";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }

    public void insertStudent (SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_MAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        db.insert(StudentContract.TABLE_NAME, null, values);
    }

    public void updateStudent (SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_MAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        String condition = StudentContract._ID + "= ?";
        String[] conditionArgs = {student.getId() + " "};
        db.update(StudentContract.TABLE_NAME, values, condition, conditionArgs);
    }

    public void deleteStudent (SQLiteDatabase db, Student student)
        {
            String condition = StudentContract._ID + "= ?";
            String[] conditionArgs = {student.getId() + " "};
            getWritableDatabase().delete("student", "_ID=?", args);
        }

}

