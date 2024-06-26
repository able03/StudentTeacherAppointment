package com.example.studentteacherappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studentteacherappointment.models.SubjectsModel;
import com.example.studentteacherappointment.models.TeacherSubjectModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            COLUMN_ID_TEACHER = "teacherId",
            COLUMN_FIRST_NAME_TEACHER = "firstName",
            COLUMN_MIDDLE_NAME_TEACHER = "middleName",
            COLUMN_LAST_NAME_TEACHER = "lastName",
            COLUMN_GENDER_TEACHER = "gender",
            COLUMN_USERNAME_TEACHER = "username",
            COLUMN_PASSWORD_TEACHER = "password";

    private static final String TABLE_NAME_SUBJECT_TEACHER = "teacherSubjectsTbl",
            COLUMN_ID_AP = "_id",
            COLUMN_SUBJECT_AP = "subject",
            COLUMN_ID_TEACHER_AP = "teacherIdAP";
    private static final String TABLE_STUDENT_APPOINTMENT = "studentAppointmentTbl",
            COLUMN_ID_APPOINTMENT = "id",
            COLUMN_SUBJECT_APPOINTMENT = "subject",
            COLUMN_TEACHER_APPOINTMENT = "teacherName",
            COLUMN_DATE_APPOINTMENT = "date",
            COLUMN_STATUS_APPOINTMENT = "status",
            COLUMN_PURPOSE_APPOINTMENT = "purpose",
            COLUMN_ID_STUDENT_APPOINTMENT = "studentId",
            COLUMN_ID_TEACHER_APPOINTMENT = "appointmentId";

    private static final String student = "Student", teacher = "Teacher";
    private Context context;

    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
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
               COLUMN_PASSWORD_STUDENT + " VARCHAR(50));";
       db.execSQL(studentQuery);

        String teacherQuery = "CREATE TABLE " + TABLE_NAME_TEACHER + " (" +
                COLUMN_ID_TEACHER + " VARCHAR(50) PRIMARY KEY, " +
                COLUMN_FIRST_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_MIDDLE_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_LAST_NAME_TEACHER + " VARCHAR(50), " +
                COLUMN_GENDER_TEACHER + " VARCHAR(50), " +
                COLUMN_USERNAME_TEACHER + " VARCHAR(50), " +
                COLUMN_PASSWORD_TEACHER + " VARCHAR(50));";
        db.execSQL(teacherQuery);

        String teachSubjQuery = "CREATE TABLE "+TABLE_NAME_SUBJECT_TEACHER+" ("
                +COLUMN_ID_AP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_SUBJECT_AP + " VARCHAR(50), "
                + COLUMN_ID_TEACHER_AP + " VARCHAR(50), "
                +"FOREIGN KEY ("+COLUMN_ID_TEACHER_AP+") REFERENCES "+TABLE_NAME_TEACHER+" ("+COLUMN_ID_TEACHER+"))";
        db.execSQL(teachSubjQuery);

        String studAppointmentQuery = "CREATE TABLE " + TABLE_STUDENT_APPOINTMENT + " ("
                +COLUMN_ID_APPOINTMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_SUBJECT_APPOINTMENT + " VARCHAR(50), "
                +COLUMN_TEACHER_APPOINTMENT + " VARCHAR(50), "
                +COLUMN_DATE_APPOINTMENT + " DATE, "
                +COLUMN_STATUS_APPOINTMENT + " VARCHAR(50), "
                +COLUMN_PURPOSE_APPOINTMENT + " VARCHAR(255), "
                +COLUMN_ID_STUDENT_APPOINTMENT + " VARCHAR(50), "
                +COLUMN_ID_TEACHER_APPOINTMENT + " VARCHAR(50), "
                +"FOREIGN KEY ("+COLUMN_ID_STUDENT_APPOINTMENT+") REFERENCES "+TABLE_NAME_STUDENT+" ("+COLUMN_ID_STUDENT+"), "
                +"FOREIGN KEY ("+COLUMN_ID_TEACHER_APPOINTMENT+") REFERENCES "+TABLE_NAME_TEACHER+" ("+COLUMN_ID_TEACHER+"))";
        db.execSQL(studAppointmentQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBJECT_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_APPOINTMENT);
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
            cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME_STUDENT+ " WHERE username = '"+username+"' AND password = '"+password+"'", null);
        }
        else if(role.equals(teacher))
        {
            cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME_TEACHER+ " WHERE username = '"+username+"' AND password = '"+password+"'", null);
        }
        return cursor;
    }

    public Cursor readData(String role, String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(role.equals(student))
        {
            cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME_STUDENT+ " WHERE "+COLUMN_ID_STUDENT+" = '"+id+"'", null);
        }
        else if(role.equals(teacher))
        {
            cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME_TEACHER+ " WHERE "+COLUMN_ID_TEACHER+" = '"+id+"'", null);
        }

        return cursor;
    }

    public Cursor readAllTeacherData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME_TEACHER, null);
    }

    public void addSubjectData(String subject, String teacherId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_AP, subject);
        values.put(COLUMN_ID_TEACHER_AP, teacherId);

        long i = db.insert(TABLE_NAME_SUBJECT_TEACHER, null, values);
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + TABLE_NAME_SUBJECT_TEACHER, null);
        cursor.moveToFirst();
        if(i != -1)
        {
            do
            {
                Toast.makeText(context, cursor.getString(1) + "" + cursor.getString(2), Toast.LENGTH_SHORT).show();
            }
            while(cursor.moveToNext());
        }
        else
        {
            Toast.makeText(context, "no data found" + cursor.getString(1), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readSubjectData(String teacherId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM '"+TABLE_NAME_SUBJECT_TEACHER+"' WHERE '"+COLUMN_ID_TEACHER_AP+"' = '"+teacherId+"'", null);
    }

    public Cursor readAllSubjectData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +TABLE_NAME_SUBJECT_TEACHER, null);
    }

    public boolean addAppointmentData(String subject, String teacher, Date date, String status,
                                      String purpose, String studentId, String teacherId)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_APPOINTMENT, subject);
        values.put(COLUMN_TEACHER_APPOINTMENT, teacher);
        values.put(COLUMN_DATE_APPOINTMENT, simpleDateFormat.format(date));
        values.put(COLUMN_STATUS_APPOINTMENT, status);
        values.put(COLUMN_PURPOSE_APPOINTMENT, purpose);
        values.put(COLUMN_ID_STUDENT_APPOINTMENT, studentId);
        values.put(COLUMN_ID_TEACHER_APPOINTMENT, teacherId);

        long i = db.insert(TABLE_STUDENT_APPOINTMENT, null, values);
        if(i != -1)
        {
            return true;
        }
        return false;
    }

    public Cursor readAllAppointmentData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM '"+TABLE_STUDENT_APPOINTMENT+"'", null);
    }

    public Cursor readStudData(String studId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM '"+TABLE_NAME_STUDENT+"' WHERE '"+COLUMN_ID_STUDENT+"' = '"+studId+"'", null);
    }
    public Cursor readStudAptData(String id)
    {
       SQLiteDatabase db = this.getReadableDatabase();
       return db.rawQuery("SELECT * FROM '"+TABLE_STUDENT_APPOINTMENT+"' WHERE '"+COLUMN_ID_STUDENT_APPOINTMENT+"' = '"+id+"'", null);
    }

    public Cursor readAppointmentData(String aptId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM '"+TABLE_STUDENT_APPOINTMENT+"' WHERE '"+COLUMN_ID_APPOINTMENT+"' = '"+aptId+"'", null);
    }

    public void updateAptStatus(String newStatus, String aptId)
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_APPOINTMENT, newStatus);

        String selection =  COLUMN_ID_APPOINTMENT + " = ?";
        String[] selectionArgs = {aptId};

        long i = db.update(TABLE_STUDENT_APPOINTMENT, values, selection, selectionArgs);
        if(i > 0)
        {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }




}
