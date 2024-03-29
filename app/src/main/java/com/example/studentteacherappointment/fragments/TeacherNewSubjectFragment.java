package com.example.studentteacherappointment.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentteacherappointment.CustomToast;
import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TeacherNewSubjectFragment extends Fragment
{

    private TextInputLayout lo_id, lo_subject;
    private TextInputEditText et_id, et_subject;
    private MaterialButton btn_create;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_teacher_new_subject, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initValues();
        setListener();

    }

    private void initValues()
    {
        lo_id = getView().findViewById(R.id.loIdTeach);
        lo_subject = getView().findViewById(R.id.loSubjectTeach);

        et_id = getView().findViewById(R.id.etIdTeach);
        et_id.setText(getIdExtra());
        et_subject = getView().findViewById(R.id.etSubjectTeach);

        btn_create = getView().findViewById(R.id.btnCreateTeach);

    }

    private void setListener()
    {
        btn_create.setOnClickListener(create -> {
            addSubjectProcess();
        });
    }

    private void addSubjectProcess()
    {
        CustomToast toast = new CustomToast();
        dbHelper = new DBHelper(getContext());
        String subject = et_subject.getText().toString().trim();
        dbHelper.addSubjectData(subject, getIdExtra());
        dbHelper.close();
    }
    /* private void addSubjectProcess()
    {
        CustomToast toast = new CustomToast();

        String subject = et_subject.getText().toString().trim();
        if(dbHelper.addSubjectData(subject, getIdExtra()))
        {
            toast.myToast(getContext(), R.drawable.ic_success, "New Subject Created", "Success");
        }
        else
        {
            toast.myToast(getContext(), R.drawable.ic_failed    , "New Subject Error", "Failed");
        }
    }*/

    private String checkGender()
    {
        dbHelper = new DBHelper(getContext());
        Cursor cursor = dbHelper.readData("Teacher", getIdExtra());

        return  cursor.getString(4);
    }




    private String getIdExtra()
    {
        return getActivity().getIntent().getStringExtra("_id");
    }

}