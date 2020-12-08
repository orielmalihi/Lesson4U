package com.example.lesson4u;

public class LessonObj {
    private String teacherUID;
    private String subject;
    private String level;
    private int price;
    private String date;
    private String time;
    private boolean isScheduled = false;


    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }



    public LessonObj(){

    }
    public LessonObj(String uid, String sub, String lev, int pr, String date, String time) {
        teacherUID = uid;
        subject = sub;
        level = lev;
        price = pr;
        this.date = date;
        this.time = time;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
