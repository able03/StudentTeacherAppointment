package com.example.studentteacherappointment.adapters;

import android.content.Context;
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
import com.example.studentteacherappointment.models.StudentAdapterModel;

import java.util.List;

public class StudentAppointmentAdapter extends RecyclerView.Adapter<StudentAppointmentAdapter.MyViewHolder>
{
    private Context context;
    private List<StudentAdapterModel> students;
    private DBHelper dbHelper;

    public StudentAppointmentAdapter(Context context, List<StudentAdapterModel> students)
    {
        this.context = context;
        this.students = students;
    }

    public void setFilteredStudents(List<StudentAdapterModel> students)
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

        holder.tv_fname.setText(students.get(position).getFname());
        holder.tv_mname.setText(students.get(position).getMname());
        holder.tv_lname.setText(students.get(position).getLname());
        holder.tv_teacher.setText(students.get(position).getTeacher());
        holder.tv_date.setText(String.valueOf(students.get(position).getDate()));
        holder.tv_status.setText(students.get(position).getStatus());
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

        holder.cv_student.setOnClickListener(cvStud -> {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_fname, tv_mname, tv_lname, tv_teacher, tv_date, tv_status, tv_id;
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
