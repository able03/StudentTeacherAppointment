package com.example.studentteacherappointment.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentteacherappointment.DBHelper;
import com.example.studentteacherappointment.R;
import com.example.studentteacherappointment.activities.SetAppointmentActivity;
import com.example.studentteacherappointment.models.StudentAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class StudentAppointmentAdapter extends RecyclerView.Adapter<StudentAppointmentAdapter.MyViewHolder>
{
    private Context context;



    private ArrayList<StudentAdapterModel> students;
    private DBHelper dbHelper;
    private Activity activity;

    public StudentAppointmentAdapter(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public void setStudents(ArrayList<StudentAdapterModel> students)
    {
        this.students = students;
    }

    public void setFilteredStudents(ArrayList<StudentAdapterModel> students)
    {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentAppointmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout_students, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAppointmentAdapter.MyViewHolder holder, int position)
    {

        String id = students.get(position).getId();
        String fname = students.get(position).getFname();
        String mname = students.get(position).getMname();
        String lname = students.get(position).getLname();
//        String teacherName = String.format("%s %s %s", fname, mname, lname);

        holder.tv_fname.setText(fname);
        holder.tv_mname.setText(mname);
        holder.tv_lname.setText(lname);
        holder.tv_teacher.setText(students.get(position).getTeacher());
        holder.tv_date.setText(String.valueOf(students.get(position).getDate()));
        holder.tv_status.setText(students.get(position).getStatus());
        holder.tv_id.setText(id);
        holder.tv_subject.setText(students.get(position).getSubject());

        String gender = checkGender(id);
        if(gender.equalsIgnoreCase("Female"))
        {
            holder.iv_profile.setImageResource(R.drawable.ic_female);
        }
        else if(gender.equalsIgnoreCase("Male"))
        {
            holder.iv_profile.setImageResource(R.drawable.ic_male);
        }

        holder.cv_student.setOnClickListener(cvStud -> {
            Intent intent = new Intent(context, SetAppointmentActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("role", activity.getIntent().getStringExtra("_role"));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_fname, tv_mname, tv_lname, tv_teacher, tv_date, tv_status, tv_id, tv_subject;
        private ImageView iv_profile;
        private CardView cv_student;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_fname = itemView.findViewById(R.id.tvFNameStudent);
            tv_mname = itemView.findViewById(R.id.tvMNameStudent);
            tv_lname = itemView.findViewById(R.id.tvLNameStudent);
            tv_teacher = itemView.findViewById(R.id.tvTeacherNameStudent);
            tv_date = itemView.findViewById(R.id.tvDateStudent);
            tv_status = itemView.findViewById(R.id.tvStatusStudent);
            tv_id = itemView.findViewById(R.id.tvIDStudent);
            tv_subject = itemView.findViewById(R.id.tvSubjectStudent);

            iv_profile = itemView.findViewById(R.id.ivProfileStudent);

            cv_student = itemView.findViewById(R.id.cvStudent);
        }
    }



    private String checkGender(String id)
    {
        dbHelper = new DBHelper(context);
        Cursor cursor = dbHelper.readData("Student", id);

        String gender = "";
        if(cursor.moveToFirst())
        {
            gender = cursor.getString(4);
        }

        cursor.close();
        return gender;
    }
}
