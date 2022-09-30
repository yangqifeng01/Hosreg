package com.hg.service;

import com.hg.model.Patient;

import java.util.List;

public interface PatientService {
    //增加病人信息
    int add(Patient patient);
    //查找病人信息
    Patient select(String idNumber);
    //修改病人信息
    int update(Patient patient);
    //显示全部病人
    List<Patient> all();
    //根据id查找病人信息
    Patient selectById(int id);
    //删除病人
    int delete(int id);
}
