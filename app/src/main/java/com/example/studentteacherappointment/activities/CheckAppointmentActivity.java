package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Currency;

public class CheckAppointmentActivity extends AppCompatActivity
{

    private TextInputLayout lo_id_student, lo_id_teacher, lo_name_teacher, lo_date, lo_purpose;
    private TextInputEditText et_id_student, et_id_teacher, et_name_teacher, et_date, et_purpose;
    private TextView tv_subject, tv_status;
    private MaterialButton btn_accept, btn_decline;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_decline);
        initValues();
        setData();
        setListeners();

    }
    private void initValues()
    {
        lo_id_student = findViewById(R.id.loIdStudCheck);
        lo_id_teacher = findViewById(R.id.loIdTeachCheck);
        lo_name_teacher = findViewById(R.id.loTeachNameCheck);
        lo_date = findViewById(R.id.loDateCheck);
        lo_purpose = findViewById(R.id.loPurposeCheck);

        et_id_student = findViewById(R.id.etIdStudCheck);
        et_id_teacher = findViewById(R.id.etIdTeachCheck);
        et_name_teacher = findViewById(R.id.etTeachNameCheck);
        et_date = findViewById(R.id.etDateCheck);
        et_purpose = findViewById(R.id.etPurposeCheck);

        tv_subject = findViewById(R.id.tvSubjectCheck);
        tv_status = findViewById(R.id.tvStatusCheck);

        btn_accept = findViewById(R.id.btnAcceptCheck);
        btn_decline = findViewById(R.id.btnDeclineCheck);
    }

    private void setData()
    {
        et_id_teacher.setText(getTeachIdExtra());
        et_date.setText(getIntent().getStringExtra("date"));
        et_id_student.setText(getIntent().getStringExtra("id"));
        et_name_teacher.setText(getIntent().getStringExtra("teachName"));

        tv_subject.setText(getIntent().getStringExtra("subject"));
        tv_status.setText(getIntent().getStringExtra("status"));

        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.readAllAppointmentData();
        cursor.moveToFirst();
        String purpose = cursor.getString(5);
        et_purpose.setText(purpose);
    }
    
    private void setListeners()
    {

    }

    private String getTeachIdExtra()
    {
        return getIntent().getStringExtra("teachId");
    }


}