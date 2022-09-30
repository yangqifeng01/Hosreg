package com.hg.model;

import org.springframework.asm.SpringAsmInfo;

public class Patient {
    private int id;
    private String IdNumber;
    private String password;
    private String patientName;
    private String balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", IdNumber='" + IdNumber + '\'' +
                ", password='" + password + '\'' +
                ", patientName='" + patientName + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
