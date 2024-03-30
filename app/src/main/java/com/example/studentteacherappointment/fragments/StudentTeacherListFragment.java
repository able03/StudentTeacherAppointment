package com.example.studentteacherappointment.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;

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
    }

    private void initValues()
    {
        dbHelper = new DBHelper(getContext());

        sv_teacher_list = getView().findViewById(R.id.svTeacherList);

        rv_teacher_list = getView().findViewById(R.id.rvTeacherList);
    }

    private void populateRV()
    {

    }

}