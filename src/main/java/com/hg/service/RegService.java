package com.hg.service;

import com.hg.model.Reg;

import java.util.List;
import java.util.Map;

public interface RegService {
    //查询某位医生在某天有多少病人
    List<Reg> allPatientByDocAndTime(Map<String,Object> map);
    //挂号
    int add(Reg reg);
    //根据病人id查询病人的全部挂号信息
    List<Reg> selectRegByPatientId(int patientId);
    //缴费
    int payReg(int id);
    //取消挂号
    int cancelReg(Reg reg);
    //医生已诊断
    int updateState(int id);
    //医生查看未诊病人列表
    List<Reg> allPatientByDocAndTimeAndState(Map<String,Object> map);
    List<Reg> all();
}
