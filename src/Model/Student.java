package Model;

import java.sql.Date;

public class Student {
    private int idstudent;
    private String studentname;
    private String gmail;
    private Date dob;
    private String address;
    private String gender;

    public Student() {
    }

    public Student(int idstudent, String studentname, String gmail, Date dob, String address, String gender) {
        this.idstudent = idstudent;
        this.studentname = studentname;
        this.gmail = gmail;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(int idstudent) {
        this.idstudent = idstudent;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}