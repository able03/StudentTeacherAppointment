package com.example.studentteacherappointment.fragments;

import android.database.Cursor;
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
import com.example.studentteacherappointment.models.TeacherAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectsFragment extends Fragment
{
    private ImageView iv_profile;
    private TextView tv_fname, tv_id;
    private DBHelper dbHelper;
    private ArrayList<TeacherAdapterModel> teachers;
    private ArrayList<SubjectsModel> subjects;
    private TeacherSubjectsAdapter adapter;
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
        getSubjectData();
//        getAllData();
//        populateRecyclerView();

    }

    private void initValues()
    {
        iv_profile = getView().findViewById(R.id.ivProfileTeacherMA);

        tv_fname = getView().findViewById(R.id.tvFNameTeacherMA);
        tv_id = getView().findViewById(R.id.tvIdTeacherMA);

        dbHelper = new DBHelper(getContext());

        teachers = new ArrayList<>();
        subjects = new ArrayList<>();

        rv_subjects = getView().findViewById(R.id.rvSubjectsTeacher);
    }

    private void setHero()
    {
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

        }
    }

   /* private void populateRecyclerView()
    {
        ArrayList<TeacherAdapterModel> teachers = new ArrayList<>();
        Cursor subjectCursor = dbHelper.readSubjectData(getIdExtra());

        Cursor cursor1 = null;

        if(subjectCursor.moveToFirst())
        {
            cursor1 = dbHelper.readData("Teacher", subjectCursor.getString(2));

            if(cursor1.moveToFirst())
            {
                while(cursor1.moveToNext())
                {
//                    String subj = subjectCursor.getString(1);
                    String fname = cursor1.getString(1);
                    String mname = cursor1.getString(2);
                    String lname = cursor1.getString(3);

                    int count = 0;
                    count++;
                    Toast.makeText(getContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();

                    teachers.add(new TeacherAdapterModel(getIdExtra(), fname, mname, lname, "asdasd"));
                }
            }
        }

        teacherSubjectsAdapter = new TeacherSubjectsAdapter(getContext());
        teacherSubjectsAdapter.setTeachers(teachers);

        rv_subjects.setAdapter(teacherSubjectsAdapter);
        rv_subjects.setLayoutManager(new LinearLayoutManager(getContext()));
    }
*/
    private void getSubjectData()
    {
        Cursor cursor = dbHelper.readSubjectData(getIdExtra());
        if(cursor.moveToFirst())
        {
            do{
                String id = cursor.getString(1);
                String teacherId = cursor.getString(2);

                subjects.add(new SubjectsModel(id, teacherId));
                Toast.makeText(getContext(), teacherId, Toast.LENGTH_SHORT).show();
            }while(cursor.moveToNext());
        }
        else
        {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private String getIdExtra()
    {
        return getActivity().getIntent().getStringExtra("_id");
    }


}