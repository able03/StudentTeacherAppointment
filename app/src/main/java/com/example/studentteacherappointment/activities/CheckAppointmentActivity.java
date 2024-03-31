package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.adapters.StudentAppointmentAdapter;
import com.example.studentteacherappointment.fragments.TeacherStudentListFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CheckAppointmentActivity extends AppCompatActivity
{

    private TextInputLayout lo_id_student, lo_id_teacher, lo_name_teacher, lo_date, lo_purpose;
    private TextInputEditText et_id_student, et_id_teacher, et_name_teacher, et_date, et_purpose;
    private TextView tv_subject, tv_status;
    private MaterialButton btn_accept, btn_decline;
    private DBHelper dbHelper;
    private StudentAppointmentAdapter adapter;
    private TeacherStudentListFragment studList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_appointment);
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

        adapter = new StudentAppointmentAdapter(this, this);
    }

    private void setData()
    {
        et_id_teacher.setText(getTeachIdExtra());
        et_date.setText(getIntent().getStringExtra("date"));
        et_id_student.setText(getIntent().getStringExtra("id"));
        et_name_teacher.setText(getIntent().getStringExtra("teachName"));

        String sts = getStatusExtra();
//        if(!sts.equals("Pending"))
//        {
//            btn_accept.setVisibility(View.GONE);
//            btn_decline.setVisibility(View.GONE);
//        }

        if(sts.equals("Accepted"))
        {
            tv_status.setTextColor(Color.GREEN);
        }
        else if(sts.equals("Declined"))
        {
            tv_status.setTextColor(Color.RED);
        }
        else if(sts.equals("Pending"))
        {
            tv_status.setTextColor(Color.BLACK);
        }
        
        tv_subject.setText(getIntent().getStringExtra("subject"));
        tv_status.setText(sts);

        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.readAllAppointmentData();
        while(cursor.moveToNext())
        {
            if(cursor.getString(0).equals(getAptItemId()))
            {
                et_purpose.setText(cursor.getString(5));
            }
        }
        dbHelper.close();

    }
    
    private void setListeners()
    {
        dbHelper = new DBHelper(this);
        studList = new TeacherStudentListFragment();

        btn_accept.setOnClickListener(accept -> {
            dbHelper.updateAptStatus("Accepted", getAptItemId());
            adapter.updateAdapter(getItemPosition());
            finish();

        });

        btn_decline.setOnClickListener(decline -> {
            dbHelper.updateAptStatus("Declined", getAptItemId());
            adapter.updateAdapter(getItemPosition());
            finish();
        });

        dbHelper.close();

    }

    private String getStatusExtra()
    {
        return getIntent().getStringExtra("status");
    }

    private String getAptItemId()
    {
        return getIntent().getStringExtra("aptId");
    }

    private String getTeachIdExtra()
    {
        return getIntent().getStringExtra("teachId");
    }

    private int getItemPosition()
    {
        return getIntent().getIntExtra("itemPos", 0);
    }


}