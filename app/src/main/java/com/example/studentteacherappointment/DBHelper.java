package com.example.studentteacherappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "appointment.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_STUDENT = "studentTbl",
            COLUMN_ID_STUDENT = "studentId",
            COLUMN_FIRST_NAME_STUDENT = "firstName",
            COLUMN_MIDDLE_NAME_STUDENT = "middleName",
            COLUMN_LAST_NAME_STUDENT = "lastName",
            COLUMN_GENDER_STUDENT = "gender",
            COLUMN_USERNAME_STUDENT = "username",
            COLUMN_PASSWORD_STUDENT = "password";


    private static final String TABLE_NAME_TEACHER = "teacherTbl",
            COLUMN_ID_TEACHER = "studentId",
            COLUMN_FIRST_NAME_TEACHER = "firstName",
            COLUMN_MIDDLE_NAME_TEACHER = "middleName",
            COLUMN_LAST_NAME_TEACHER = "lastName",
            COLUMN_GENDER_TEACHER = "gender",
            COLUMN_USERNAME_TEACHER = "username",
            COLUMN_PASSWORD_TEACHER = "password";

    private static final String student = "Student", teacher = "Teacher";

    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
       String studentQuery = "CREATE TABLE " + TABLE_NAME_STUDENT + " (" +
               COLUMN_ID_STUDENT + " VARCHAR(50) PRIMARY KEY , " +
               COLUMN_FIRST_NAME_STUDENT + " VARCHAR(50), " +
               COLUMN_MIDDLE_NAME_STUDENT + " VARCHAR(50), " +
               COLUMN_LAST_NAME_STUDENT + " VARCHAR(50), " +
               COLUMN_GENDER_STUDENT + " VARCHAR(50), " +
               COLUMN_USERNAME_STUDENT + " VARCHAR(50), " +
               COLUMN_PASSWORD_STUDENT + " VARCHAR(50))";
       db.execSQL(studentQuery);

        String teacherQuery = "CREATE TABLE " + TABLE_NAME_TEACHER + " (" +
                COLUMN_ID_TEACHER + " VARCHAR(50) PRIMARY KEY, " +
                COLUMN_FIRST_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_MIDDLE_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_LAST_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_GENDER_TEACHER + " VARCHAR(50), " +
                COLUMN_USERNAME_TEACHER + " VARCHAR(50), " +
                COLUMN_PASSWORD_TEACHER + " VARCHAR(50))";
        db.execSQL(teacherQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEACHER);
        onCreate(db);
    }

    public boolean addData(String role, String fname, String mname, String lname, String gender,
                        String username, String password)
    {
        String id = generateId(role);
        boolean isAdded = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(role.equals(student))
        {
            values.put(COLUMN_ID_STUDENT, id);
            values.put(COLUMN_FIRST_NAME_STUDENT, fname);
            values.put(COLUMN_MIDDLE_NAME_STUDENT, mname);
            values.put(COLUMN_LAST_NAME_STUDENT, lname);
            values.put(COLUMN_GENDER_STUDENT, gender);
            values.put(COLUMN_USERNAME_STUDENT, username);
            values.put(COLUMN_PASSWORD_STUDENT, password);
            long i = db.insert(TABLE_NAME_STUDENT, null, values);
            if(i != -1)
            {
                isAdded = true;
            }
        }
        else if(role.equals(teacher))
        {
            values.put(COLUMN_ID_TEACHER, id);
            values.put(COLUMN_FIRST_NAME_TEACHER, fname);
            values.put(COLUMN_MIDDLE_NAME_TEACHER, mname);
            values.put(COLUMN_LAST_NAME_TEACHER, lname);
            values.put(COLUMN_GENDER_TEACHER, gender);
            values.put(COLUMN_USERNAME_TEACHER, username);
            values.put(COLUMN_PASSWORD_TEACHER, password);
            long i = db.insert(TABLE_NAME_TEACHER, null, values);
            if(i != -1)
            {
                isAdded = true;
            }
        }

        return isAdded;
    }

    private String generateId(String role)
    {
       SQLiteDatabase db = this.getReadableDatabase();
       String id = "";
        if(role.equals(student))
        {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_STUDENT + " WHERE " + COLUMN_ID_STUDENT + " LIKE 'STI01%'", null);
            cursor.moveToFirst();
            long count = cursor.getInt(0);
            cursor.close();
            id = "STI01" + (count+1);
        }
        else if(role.equals(teacher))
        {
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_TEACHER + " WHERE " + COLUMN_ID_TEACHER + " LIKE 'ROS01%'", null);
            cursor.moveToFirst();
            long count = cursor.getInt(0);
            cursor.close();
            id = "ROS01" + (count+1);
        }
        return id;
    }


    public Cursor checkLogin(String role, String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(role.equals(student))
        {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_STUDENT + " WHERE username = '"+username+"' AND password = '"+password+"'", null);
        }
        else if(role.equals(teacher))
        {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TEACHER + " WHERE username = '"+username+"' AND password = '"+password+"'", null);
        }
        return cursor;
    }



}
