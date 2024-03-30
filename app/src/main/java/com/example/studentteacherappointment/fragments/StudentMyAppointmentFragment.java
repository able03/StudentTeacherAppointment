package com.example.studentteacherappointment.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.adapters.StudentAppointmentAdapter;
import com.example.studentteacherappointment.models.StudentAdapterModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentMyAppointmentFragment extends Fragment
{
    private ImageView iv_profile;
    private TextView tv_fname, tv_id;
    private SearchView sv_appointments;
    private RecyclerView rv_appointments;
    private DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_student_my_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        setHero();
        populateRV();

    }

    private void initValues()
    {
        iv_profile = getView().findViewById(R.id.ivProfileStudMA);

        tv_fname = getView().findViewById(R.id.tvFNameStudMA);
        tv_id = getView().findViewById(R.id.tvIdStudMA);

        sv_appointments = getView().findViewById(R.id.svMyAppointmentStud);

        rv_appointments = getView().findViewById(R.id.rvMyAppointmentStud);
    }

    private void setHero()
    {
        dbHelper = new DBHelper(getContext());
        Cursor cursor = dbHelper.readData("Student", getIdExtra());
        if(cursor.moveToFirst())
        {
            String fname = cursor.getString(1);
            String gender = cursor.getString(4);

            tv_fname.setText(fname);
            tv_id.setText(getIdExtra());

            if(gender.equals("Female"))
            {
                iv_profile.setImageResource(R.drawable.ic_female);
            }
            else
            {
                iv_profile.setImageResource(R.drawable.ic_male);
            }
            dbHelper.close();
        }
    }


    private void populateRV()
    {
        ArrayList<StudentAdapterModel> students = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        Cursor appointmentC = dbHelper.readAllAppointmentData();

        while(appointmentC.moveToNext())
        {
            String myId = appointmentC.getString(6);
           if(myId.equals(getIdExtra()))
           {
               Cursor studC = dbHelper.readData("Student", myId);
               if(studC.moveToFirst())
               {
                   String fname = studC.getString(1);
                   String mname = studC.getString(2);
                   String lname = studC.getString(3);

                   String subj = appointmentC.getString(1);
                   String teachName = appointmentC.getString(2);

                   Date date = parseDate(appointmentC.getString(3));

                   String status = appointmentC.getString(4);

                   students.add(new StudentAdapterModel(myId, fname, mname, lname, subj,teachName, date, status));
               }
           }
        }


        StudentAppointmentAdapter studentAppointmentAdapter = new StudentAppointmentAdapter(getContext(), getActivity());
        studentAppointmentAdapter.setStudents(students);
        rv_appointments.setAdapter(studentAppointmentAdapter);
        rv_appointments.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private Date parseDate(String strDate)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }



    private String getIdExtra()
    {
        return getActivity().getIntent().getStringExtra("_id");
    }


}