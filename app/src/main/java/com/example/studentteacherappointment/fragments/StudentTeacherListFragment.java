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
import com.example.studentteacherappointment.adapters.TeacherSubjectsAdapter;
import com.example.studentteacherappointment.models.TeacherSubjectModel;

import java.util.ArrayList;

public class StudentTeacherListFragment extends Fragment
{

    private DBHelper dbHelper;
    private SearchView sv_teacher_list;
    private RecyclerView rv_teacher_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_student_teacher_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        populateRV();
    }

    private void initValues()
    {
        dbHelper = new DBHelper(getContext());

        sv_teacher_list = getView().findViewById(R.id.svTeacherList);

        rv_teacher_list = getView().findViewById(R.id.rvTeacherList);
    }

    private void populateRV()
    {
        dbHelper = new DBHelper(getContext());
        ArrayList<TeacherSubjectModel> teachers = new ArrayList<>();
        TeacherSubjectsAdapter adapter = new TeacherSubjectsAdapter(getContext(), getActivity());

        Cursor subjC = dbHelper.readAllSubjectData();
        while (subjC.moveToNext())
        {
            String subj = subjC.getString(1);
            String teachId = subjC.getString(2);

            Cursor teachC = dbHelper.readData("Teacher", teachId);
            if(teachC.moveToFirst())
            {
                String fname = teachC.getString(1);
                String mname = teachC.getString(2);
                String lname = teachC.getString(3);

                teachers.add(new TeacherSubjectModel(teachId, fname, mname, lname, subj));
            }
            else
            {
                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
            }
            adapter.setTeachers(teachers);

            rv_teacher_list.setAdapter(adapter);
            rv_teacher_list.setLayoutManager(new LinearLayoutManager(getContext()));
            dbHelper.close();
        }
    }

}