package com.example.studentteacherappointment.models;

public class TeacherSubjectModel
{
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String subject;

    public TeacherSubjectModel(String id, String fname, String mname, String lname, String subject)
    {
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.subject = subject;
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

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getTeacherName()
    {
        return fname + " " + mname + " " + lname;
    }

}
