package com.example.studentteacherappointment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.fragments.StudentMyAppointmentFragment;
import com.example.studentteacherappointment.fragments.StudentSetAppointmentFragment;
import com.example.studentteacherappointment.fragments.StudentTeacherListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentMainActivity extends AppCompatActivity
{
    private FrameLayout fl_student;
    private BottomNavigationView bnv_student;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        initValues();
        replaceFragment(new StudentMyAppointmentFragment());
        setMenuListener();
    }

    private void initValues()
    {
        fl_student = findViewById(R.id.flStudent);
        bnv_student = findViewById(R.id.bnvStudent);
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flStudent, fragment);
        fragmentTransaction.commit();
    }

    private void setMenuListener()
    {
        bnv_student.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.menuMyAppointmentStudent)
            {
                replaceFragment(new StudentMyAppointmentFragment());
            }
            else if(id == R.id.menuTeacherList)
            {
                replaceFragment(new StudentTeacherListFragment());
            }
            else if(id == R.id.menuSetAppointmentStudent)
            {
                replaceFragment(new StudentSetAppointmentFragment());
            }
            else if(id == R.id.menuLogoutStudent)
            {
                finish();
            }
            return true;

        });
    }


}