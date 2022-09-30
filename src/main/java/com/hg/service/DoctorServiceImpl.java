package com.hg.service;

import com.hg.dao.DoctorMapper;
import com.hg.model.Doctor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("doctorService")
public class DoctorServiceImpl implements DoctorService{
    @Resource
    private DoctorMapper doctorMapper;
    @Override
    public List<Doctor> list() {
        return doctorMapper.list();
    }

    @Override
    public Doctor selectById(int id) {
        return doctorMapper.selectById(id);
    }

    @Override
    public List<Doctor> selectByName(String doctorName) {
        return doctorMapper.selectByName(doctorName);
    }

    @Override
    public List<Doctor> search(String str) {
        return doctorMapper.search(str);
    }

    @Override
    public int update(Doctor doctor) {
        return doctorMapper.update(doctor);
    }

    @Override
    public int add(Doctor doctor) {
        return doctorMapper.add(doctor);
    }

    @Override
    public int delete(int id) {
        return doctorMapper.delete(id);
    }


}
