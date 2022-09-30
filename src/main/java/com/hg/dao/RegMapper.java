package com.hg.dao;

import com.hg.model.Patient;
import com.hg.model.Reg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("regMapper")
public interface RegMapper {
    //挂号
    int add(Reg reg);
    //查询某医生在某天的挂号人数
    List<Reg> allPatientByDocAndTime(Map<String,Object> map);
    //根据病人id查询挂号信息
    List<Reg> selectRegByPatientId(int patientId);
    //缴费
    int payReg(int id);
    //取消挂号
    int cancelReg(Reg reg);
    //医生已诊断
    int updateState(int id);
    //医生查看未诊病人列表
    List<Reg> allPatientByDocAndTimeAndState(Map<String,Object> map);
    //显示全部挂号信息
    List<Reg> all();
}
