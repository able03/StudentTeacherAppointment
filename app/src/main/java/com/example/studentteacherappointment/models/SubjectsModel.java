package com.example.studentteacherappointment.models;

public class SubjectsModel
{
    private String id;
    private String teacherId;

    public SubjectsModel(String id, String teacherId)
    {
        this.id = id;
        this.teacherId = teacherId;
    }

    public String getId()
    {
        return id;
    }

    public String getTeacherId()
    {
        return teacherId;
    }
}
