package com.hg.service;

import com.hg.model.Doctor;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    List<Doctor> list();
    Doctor selectById(int id);
    List<Doctor> selectByName(String doctorName);
    List<Doctor> search(String str);
    //更新医生信息
    int update(Doctor doctor);
    //添加医生
    int add(Doctor doctor);
    //删除医生
    int delete(int id);
}
