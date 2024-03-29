package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SetAppointmentActivity extends AppCompatActivity
{
    private TextInputLayout lo_id_student, lo_id_teacher, lo_name_teacher, lo_date, lo_purpose;
    private TextInputEditText et_id_student, et_id_teacher, et_name_teacher, et_date, et_purpose;
    private TextView tv_subject, tv_status;
    private MaterialButton btn_accept, btn_decline, btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);
        initValues();
        setData();
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
        btn_decline = findViewById(R.id.btnSubmitOrDecline);
        btn_submit = findViewById(R.id.btnSubmitOrDecline);
    }

    private void setData()
    {

    }

}