package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetAppointmentActivity extends AppCompatActivity
{
    private TextInputLayout lo_id_student, lo_id_teacher, lo_name_teacher, lo_date, lo_purpose;
    private TextInputEditText et_id_student, et_id_teacher, et_name_teacher, et_date, et_purpose;
    private TextView tv_subject, tv_status;
    private MaterialButton btn_accept, btn_decline, btn_submit;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        initValues();
        setData();
        setRoleAccess();
    }

    @SuppressLint("CutPasteId")
    private void initValues()
    {
        lo_id_student = findViewById(R.id.loIdStud);
        lo_id_teacher = findViewById(R.id.loIdTeach);
        lo_name_teacher = findViewById(R.id.loTeachNameAP);
        lo_date = findViewById(R.id.loDateAP);
        lo_purpose = findViewById(R.id.loPurposeAP);

        et_id_student = findViewById(R.id.etIdStud);
        et_id_teacher = findViewById(R.id.etIdTeach);
        et_name_teacher = findViewById(R.id.etTeachNameAP);
        et_date = findViewById(R.id.etDateAP);
        et_purpose = findViewById(R.id.etPurposeAP);

        tv_subject = findViewById(R.id.tvSubjectAP);
        tv_status = findViewById(R.id.tvStatusAP);

        btn_accept = findViewById(R.id.btnAccept);
        btn_submit = findViewById(R.id.btnSubmitOrDecline);
        btn_decline = btn_submit;
    }

    private void setDateListener()
    {
        lo_date.setEndIconOnClickListener(date -> {
            Calendar calendar = Calendar.getInstance();
            int year1 = calendar.get(Calendar.YEAR);
            int month1 = calendar.get(Calendar.MONTH);
            int day1 = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(SetAppointmentActivity.this,
                    (view, year, month, dayOfMonth) ->
                    et_date.setText(String.format("%02d", month + 1) + "/" + String.format("%02d", dayOfMonth) + "/" + String.format("%04d", year)),
                    year1, month1, day1);
            datePickerDialog.show();
        });
    }

    private void setSubmitListener()
    {
        btn_submit.setOnClickListener(submit -> {
            addAppointmentProcess();
        });
    }

    private void addAppointmentProcess()
    {
        dbHelper = new DBHelper(this);
        String subj = tv_subject.getText().toString();
        String studId = et_id_student.getText().toString().trim();
        String teachId = et_id_teacher.getText().toString().trim();
        String teachName = et_name_teacher.getText().toString().trim();
        Date date = parseDate(et_date.getText().toString());
        Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT).show();
        String status = tv_status.getText().toString();
        String purpose = et_purpose.getText().toString();


        if(dbHelper.addAppointmentData(subj, teachName, date, status, purpose, studId, teachId))
        {
            Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "add failed ", Toast.LENGTH_SHORT).show();
        }


        dbHelper.close();
    }

    private Date parseDate(String date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            return simpleDateFormat.parse(date);
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }


    private void setRoleAccess()
    {
        if(getRoleExtra().equals("Teacher"))
        {
            btn_accept.setVisibility(View.VISIBLE);
            et_purpose.setEnabled(false);
            et_date.setEnabled(false);
            btn_submit.setText("DECLINE");
            lo_date.setBackground(getResources().getDrawable(R.drawable.bg_light_gray));
            lo_purpose.setBackground(getResources().getDrawable(R.drawable.bg_light_gray));
        }
        else
        {
            btn_accept.setVisibility(View.GONE);
            setDateListener();
            setSubmitListener();
        }
    }


    private void setData()
    {
       if(getRoleExtra().equals("Student"))
       {
           et_id_student.setText(getStudIdExtra());
           et_id_teacher.setText(getTeachIdExtra());
           et_name_teacher.setText(getTeacherNameExtra());
           tv_subject.setText(getSubjExtra());
       }
       else
       {
           Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
       }
    }

    private String getTeachIdExtra()
    {
        return getIntent().getStringExtra("teachId");
    }

    private String getStudIdExtra()
    {
        return getIntent().getStringExtra("studId");
    }

    private String getTeacherNameExtra()
    {
        return getIntent().getStringExtra("teacherName");
    }



    private String getSubjExtra()
    {
        return getIntent().getStringExtra("subject");
    }

    private String getRoleExtra()
    {
        return getIntent().getStringExtra("role");
    }





}