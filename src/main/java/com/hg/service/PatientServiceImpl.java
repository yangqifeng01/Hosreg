package com.hg.service;

import com.hg.dao.PatientMapper;
import com.hg.model.Patient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("patientService")
public class PatientServiceImpl implements PatientService{
    @Resource
    private PatientMapper patientMapper;
    @Override
    public int add(Patient patient) {
        return patientMapper.add(patient);
    }

    @Override
    public Patient select(String idNumber) {
        return patientMapper.select(idNumber);
    }

    @Override
    public int update(Patient patient) {
        return patientMapper.update(patient);
    }

    @Override
    public List<Patient> all() {
        return patientMapper.all();
    }

    @Override
    public Patient selectById(int id) {
        return patientMapper.selectById(id);
    }

    @Override
    public int delete(int id) {
        return patientMapper.delete(id);
    }


}
