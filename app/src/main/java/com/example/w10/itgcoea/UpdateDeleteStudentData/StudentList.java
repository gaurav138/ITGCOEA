package com.example.w10.itgcoea.UpdateDeleteStudentData;

/**
 * Created by W10 on 12/02/2018.
 */

public class StudentList {
    private String name;
    private String username;
    private String password;
    private String email;
    private int user_id;
    private int roll_no;
    private String qualification;
    private String courses;

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String cources) {
        this.courses = cources;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }


}
