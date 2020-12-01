package com.example.lesson4u;

public class LessonObj {
    private String teacherUID;
    private String subject;
    private String level;
    private String price;

    public LessonObj(String uid, String sub, String lev, String pr){
        teacherUID = uid;
        subject = sub;
        level = lev;
        price = pr;
    }


    public String getTeacherUID() {
        return teacherUID;
    }

    public void setTeacherUID(String teacherUID) {
        this.teacherUID = teacherUID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
