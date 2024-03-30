package com.example.studentteacherappointment.models;

public class SubjectsModel
{
    private String id;
    private String subject;
    private String teacherId;

    public SubjectsModel(String subject, String teacherId)
    {
        this.subject = subject;
        this.teacherId = teacherId;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getTeacherId()
    {
        return teacherId;
    }
}
