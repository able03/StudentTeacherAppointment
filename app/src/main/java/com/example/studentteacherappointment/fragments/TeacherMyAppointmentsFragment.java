package com.example.studentteacherappointment.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentteacherappointment.R;

public class TeacherMyAppointmentsFragment extends Fragment
{

    private TextView tv_id_teacher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_my_appointments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initValues();

        String id = getActivity().getIntent().getStringExtra("_id");
        tv_id_teacher.setText(id);
    }

    private void initValues()
    {
        tv_id_teacher = getView().findViewById(R.id.tvMyAppointmentTeacher);
    }
}