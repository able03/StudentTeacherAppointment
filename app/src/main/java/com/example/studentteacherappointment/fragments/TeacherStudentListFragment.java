package com.example.studentteacherappointment.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TeacherStudentListFragment extends Fragment
{
    private static final int EDIT_REQUEST_CODE = 1;
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
        populateRV();
        setSearchViewListener();
    }

    private void initValues()
    {
        sv_student = getView().findViewById(R.id.svStudentList);
        rv_student = getView().findViewById(R.id.rvStudentList);
    }

    private ArrayList<StudentAdapterModel> setRVData()
    {
        dbHelper = new DBHelper(getContext());
        Cursor appointmentC = dbHelper.readAllAppointmentData();
        ArrayList<StudentAdapterModel> studentTemp =  new ArrayList<>();

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

                    studentTemp.add(new StudentAdapterModel(aptId, studentId, fname, mname, lname, subj,teachName, date, status));
                }
            }
        }
        dbHelper.close();
        return studentTemp;

    }

    private void setSearchViewListener()
    {
        sv_student.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                filteredList(newText);
                return true;
            }
        });
    }

    private void filteredList(String newText)
    {
        ArrayList<StudentAdapterModel> filteredList = new ArrayList<>();
        for(StudentAdapterModel student : students)
        {
            if(student.getStudentName().toLowerCase().contains(newText.toLowerCase())
                    || student.getSubject().toLowerCase().contains(newText.toLowerCase())
                    || student.getStatus().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(student);
            }
        }
        if(filteredList.isEmpty())
        {

        }
        else
        {
            studentAppointmentAdapter.setFilteredStudents(filteredList);
        }
    }

    private void populateRV()
    {
        students = setRVData();
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