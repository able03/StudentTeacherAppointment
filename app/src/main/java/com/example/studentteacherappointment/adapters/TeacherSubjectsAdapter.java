package com.example.studentteacherappointment.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.activities.SetAppointmentActivity;
import com.example.studentteacherappointment.models.TeacherSubjectModel;

import java.util.ArrayList;

public class TeacherSubjectsAdapter extends RecyclerView.Adapter<TeacherSubjectsAdapter.MyViewHolder>
{
    private Context context;
    private Activity activity;
    private ArrayList<TeacherSubjectModel> teachers;
    private DBHelper dbHelper;

    public TeacherSubjectsAdapter(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public void setTeachers(ArrayList<TeacherSubjectModel> teachers)
    {
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    public void setFilteredTeachers(ArrayList<TeacherSubjectModel> teachers)
    {
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_layout_teacher, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String id = teachers.get(position).getId();
        String subj = teachers.get(position).getSubject();
        String fname = teachers.get(position).getFname();
        String mname = teachers.get(position).getMname();
        String lname = teachers.get(position).getLname();

        String teacherName = String.format("%s %s %s", fname, mname, lname);

        holder.tv_fname.setText(fname);
        holder.tv_mname.setText(mname);
        holder.tv_lname.setText(lname);
        holder.tv_subject.setText(subj);
        holder.tv_id.setText(id);

        String gender = checkGender(id);
        if(gender.equalsIgnoreCase("Female"))
        {
            holder.iv_profile.setImageResource(R.drawable.ic_female);
        }
        else if(gender.equalsIgnoreCase("Male"))
        {
            holder.iv_profile.setImageResource(R.drawable.ic_male);
        }

        String role = activity.getIntent().getStringExtra("_role");
        String logId = activity.getIntent().getStringExtra("_id");

       if(role.equals("Student"))
       {
           holder.cv_teacher.setOnClickListener(cardview -> {
               Intent intent = new Intent(context, SetAppointmentActivity.class);
               Bundle bundle = new Bundle();

               bundle.putString("teachId", id);
               bundle.putString("studId", logId);
               bundle.putString("subject",subj);
               bundle.putString("role", role);
               bundle.putString("teacherName", teacherName);

               intent.putExtras(bundle);
               context.startActivity(intent);
           });
       }
    }

    @Override
    public int getItemCount()
    {
        return teachers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_fname, tv_mname, tv_lname, tv_subject, tv_id;
        private ImageView iv_profile;
        private CardView cv_teacher;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_fname = itemView.findViewById(R.id.tvFNameTeacher);
            tv_mname = itemView.findViewById(R.id.tvMNameTeacher);
            tv_lname = itemView.findViewById(R.id.tvLNameTeacher);

            tv_subject = itemView.findViewById(R.id.tvSubjectTeacher);
            tv_id = itemView.findViewById(R.id.tvIDTeacher);

            iv_profile = itemView.findViewById(R.id.ivProfileTeacher);

            cv_teacher = itemView.findViewById(R.id.cvTeacher);
        }
    }

    private String checkGender(String id)
    {
        dbHelper = new DBHelper(context);
        Cursor cursor = dbHelper.readData("Teacher", id);

        String gender = "";
        if(cursor.moveToFirst())
        {
           gender = cursor.getString(4);
        }

        cursor.close();
        return gender;
    }

}
