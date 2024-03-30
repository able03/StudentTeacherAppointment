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
import android.widget.Toast;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.adapters.StudentAppointmentAdapter;
import com.example.studentteacherappointment.models.StudentAdapterModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TeacherStudentListFragment extends Fragment
{
    private SearchView sv_student;
    private RecyclerView rv_student;
    private DBHelper dbHelper;
    private StudentAppointmentAdapter studentAppointmentAdapter;
    private ArrayList<StudentAdapterModel> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_teacher_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        setRVData();
        populateRV();
    }

    private void initValues()
    {
        sv_student = getView().findViewById(R.id.svStudentList);
        rv_student = getView().findViewById(R.id.rvStudentList);
        students = new ArrayList<>();
    }

    private void setRVData()
    {
        dbHelper = new DBHelper(getContext());
        Cursor appointmentC = dbHelper.readAllAppointmentData();

        while(appointmentC.moveToNext())
        {
            String studId = appointmentC.getString(6);
            String myId = appointmentC.getString(7);
            if(myId.equals(getIdExtra()))
            {
                Cursor studC = dbHelper.readData("Student", studId);
                if(studC.moveToFirst())
                {
                    String aptId = appointmentC.getString(0);
                    String studentId = studC.getString(0);
                    String fname = studC.getString(1);
                    String mname = studC.getString(2);
                    String lname = studC.getString(3);

                    String subj = appointmentC.getString(1);
                    String teachName = appointmentC.getString(2);

                    Date date = parseDate(appointmentC.getString(3));

                    String status = appointmentC.getString(4);

                    students.add(new StudentAdapterModel(aptId, studentId, fname, mname, lname, subj,teachName, date, status));
                }
            }
        }

    }


    private void populateRV()
    {

        studentAppointmentAdapter = new StudentAppointmentAdapter(getContext(), getActivity());
        studentAppointmentAdapter.setStudents(students);
        rv_student.setAdapter(studentAppointmentAdapter);
        rv_student.setLayoutManager(new LinearLayoutManager(getContext()));
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