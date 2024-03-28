package com.example.studentteacherappointment.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.studentteacherappointment.models.TeacherAdapterModel;

import java.util.List;

public class TeacherAppointmentAdapter extends RecyclerView.Adapter<TeacherAppointmentAdapter.MyViewHolder>
{
    private Context context;
    private List<TeacherAdapterModel> teachers;
    private DBHelper dbHelper;

    public TeacherAppointmentAdapter(Context context, List<TeacherAdapterModel> teachers)
    {
        this.context = context;
        this.teachers = teachers;
    }

    public void setFilteredTeachers(List<TeacherAdapterModel> teachers)
    {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout_teacher, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String id = teachers.get(position).getId();

        holder.tv_fname.setText(teachers.get(position).getFname());
        holder.tv_mname.setText(teachers.get(position).getMname());
        holder.tv_lname.setText(teachers.get(position).getLname());
        holder.tv_subject.setText(teachers.get(position).getSubject());
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

        holder.cv_teacher.setOnClickListener(cardview -> {
            Intent intent = new Intent(context, SetAppointmentActivity.class);
            context.startActivity(intent);
        });
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
