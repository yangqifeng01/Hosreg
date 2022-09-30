package com.hg.model;

public class Doctor {
    private int id;
    private String doctorName;
    private String password;
    private String workTime;
    private String department;
    private String professionalTitle;
    private String shanChang;
    private String introduction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public String getShanChang() {
        return shanChang;
    }

    public void setShanChang(String shanChang) {
        this.shanChang = shanChang;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", doctorName='" + doctorName + '\'' +
                ", password='" + password + '\'' +
                ", workTime='" + workTime + '\'' +
                ", department='" + department + '\'' +
                ", professionalTitle='" + professionalTitle + '\'' +
                ", shanChang='" + shanChang + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
