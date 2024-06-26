package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.adapters.StudentAppointmentAdapter;
import com.example.studentteacherappointment.fragments.TeacherSubjectsFragment;
import com.example.studentteacherappointment.fragments.TeacherNewSubjectFragment;
import com.example.studentteacherappointment.fragments.TeacherStudentListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherMainActivity extends AppCompatActivity
{

    private FrameLayout fl_teacher;
    private BottomNavigationView bnv_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        initValues();
        replaceFragment(new TeacherSubjectsFragment());
        setMenuListener();
    }

    private void initValues()
    {
        fl_teacher = findViewById(R.id.flTeacher);
        bnv_teacher = findViewById(R.id.bnvTeacher);
    }

    private void setMenuListener()
    {
        bnv_teacher.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.menuSubjectsTeacher)
            {
                replaceFragment(new TeacherSubjectsFragment());
            }
            else if(id == R.id.menuStudentsTeacher)
            {
                replaceFragment(new TeacherStudentListFragment());
            }
            else if(id == R.id.menuNewSubjectTeacher)
            {
                replaceFragment(new TeacherNewSubjectFragment());
            }
            else if(id == R.id.menuLogoutTeacher)
            {
                finish();
            }
            return true;
        });

    }


    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flTeacher, fragment);
        fragmentTransaction.commit();
    }

}