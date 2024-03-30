package com.example.studentteacherappointment.models;

import java.util.Date;

public class StudentAdapterModel
{
    private String appointmentId;
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String subject;
    private String teacher;
    private Date date;
    private String status;

    public StudentAdapterModel(String appointmentId, String id, String fname, String mname, String lname, String subject, String teacher, Date date, String status)
    {
        this.appointmentId = appointmentId;
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
        this.status = status;
    }

    public String getAppointmentId()
    {
        return appointmentId;
    }

    public String getStudentName()
    {
        return fname + " " + mname + " " + lname;
    }


    public String getId()
    {
        return id;
    }

    public String getFname()
    {
        return fname;
    }

    public String getMname()
    {
        return mname;
    }

    public String getLname()
    {
        return lname;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public Date getDate()
    {
        return date;
    }

    public String getStatus()
    {
        return status;
    }
}
