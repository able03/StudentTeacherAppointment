package com.example.studentteacherappointment.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.studentteacherappointment.adapters.TeacherSubjectsAdapter;
import com.example.studentteacherappointment.models.SubjectsModel;
import com.example.studentteacherappointment.models.TeacherSubjectModel;

import java.util.ArrayList;

public class TeacherSubjectsFragment extends Fragment
{
    private ImageView iv_profile;
    private TextView tv_fname, tv_id;
    private DBHelper dbHelper;
    private RecyclerView rv_subjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_subjects, container, false);
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
        iv_profile = getView().findViewById(R.id.ivProfileTeacherMA);

        tv_fname = getView().findViewById(R.id.tvFNameTeacherMA);
        tv_id = getView().findViewById(R.id.tvIdTeacherMA);

        rv_subjects = getView().findViewById(R.id.rvSubjectsTeacher);
    }

    private void setHero()
    {
        dbHelper = new DBHelper(getContext());
        Cursor cursor = dbHelper.readData("Teacher", getIdExtra());
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
        dbHelper = new DBHelper(getContext());
        ArrayList<TeacherSubjectModel> teachers = new ArrayList<>();
        TeacherSubjectsAdapter adapter = new TeacherSubjectsAdapter(getContext(), getActivity());

        Cursor subjC = dbHelper.readAllSubjectData();
        while (subjC.moveToNext())
        {
            String subj = subjC.getString(1);
            String teachId = subjC.getString(2);

            if(teachId.equals(getIdExtra()))
            {
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
            }
            adapter.setTeachers(teachers);

            rv_subjects.setAdapter(adapter);
            rv_subjects.setLayoutManager(new LinearLayoutManager(getContext()));
            dbHelper.close();
        }
    }

    private String getIdExtra()
    {
        return getActivity().getIntent().getStringExtra("_id");
    }


}