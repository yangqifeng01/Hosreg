package com.hg.service;

import com.hg.dao.RegMapper;
import com.hg.model.Reg;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("regService")
public class RegServiceImpl implements RegService{
    @Resource
    private RegMapper regMapper;
    @Override
    public List<Reg> allPatientByDocAndTime(Map<String, Object> map) {
        return regMapper.allPatientByDocAndTime(map);
    }

    @Override
    public int add(Reg reg) {
        return regMapper.add(reg);
    }

    @Override
    public List<Reg> selectRegByPatientId(int patientId) {
        return regMapper.selectRegByPatientId(patientId);
    }

    @Override
    public int payReg(int id) {
        return regMapper.payReg(id);
    }

    @Override
    public int cancelReg(Reg reg) {
        return regMapper.cancelReg(reg);
    }

    @Override
    public int updateState(int id) {
        return regMapper.updateState(id);
    }

    @Override
    public List<Reg> allPatientByDocAndTimeAndState(Map<String, Object> map) {
        return regMapper.allPatientByDocAndTimeAndState(map);
    }

    @Override
    public List<Reg> all() {
        return regMapper.all();
    }


}
