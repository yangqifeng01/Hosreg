package com.hg.dao;

import com.hg.model.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("doctorMapper")
public interface DoctorMapper {
    List<Doctor> list();
    Doctor selectById(int id);
    List<Doctor> selectByName(String doctorName);
    List<Doctor> search(String str);
    int update(Doctor doctor);
    int add(Doctor doctor);
    int delete(int id);
}
