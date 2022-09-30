package com.hg.dao;

import com.hg.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("patientMapper")
public interface PatientMapper {
    int add(Patient patient);
    Patient select(String idNumber);
    int update(Patient patient);
    List<Patient> all();
    Patient selectById(int id);
    int delete(int id);
}
