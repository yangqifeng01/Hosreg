package com.hg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.model.Doctor;
import com.hg.model.Patient;
import com.hg.model.Reg;
import com.hg.service.RegService;
import com.hg.util.MyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.print.Doc;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Controller("regController")
public class RegController {
    @Resource
    private RegService regService;
    //挂号
    @RequestMapping(value = "/p/reg",method = RequestMethod.POST)
    @ResponseBody
    public String reg(HttpSession session) throws ParseException {
        //用来标识当天是否在该医生下挂号
        int flag = 1;
        Doctor doctor = (Doctor) session.getAttribute("doctorDetail");
        Patient patient = (Patient) session.getAttribute("patient1");
        System.out.println("doctor=>"+doctor);
        System.out.println("patient1=>"+patient);

        //获取当前时间字符串
        SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        if(doctor != null && patient!=null){
            //获取当前时间
            Date date = new Date();
            System.out.println(date);
            //用来放置医生的id和当前时间
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("doctorId",doctor.getId());
            map.put("date",simpleFormatter1.format(date));
            //获取病人挂号医生当天已经挂号的病人，用来计算病人的序号
            List<Reg> regList = regService.allPatientByDocAndTime(map);
            for (Reg reg : regList) {
                System.out.println(reg);
                if (reg.getPatientId() == patient.getId()) {
                    //当天已在该医生下挂号，不可挂好
                    flag = 0;
                    break;
                }
            }
            //当天未在该医生下挂号,可已挂号
            if(flag == 1){
                int seqNum = regList.size() + 1;
                System.out.println(seqNum);
                //将挂号信息放入数据库中
                Reg reg = new Reg();
                reg.setDoctorId(doctor.getId());
                reg.setPatientId(patient.getId());
                reg.setSeqNum(seqNum);
                //普通挂号
                reg.setDate(simpleFormatter1.format(date));
                reg.setType(0);
                System.out.println(reg);
                if (regService.add(reg)>0){
                    return "true";
                }
            }
        }
        return "false";
    }

    //预约挂号
    @RequestMapping(value = "/p/bookingReg",method = RequestMethod.POST)
    @ResponseBody
    public String bookingReg(HttpSession session) throws ParseException {
        //用来标识当天是否在该医生下挂号
        int flag = 1;
        Doctor doctor = (Doctor) session.getAttribute("doctorDetail");
        Patient patient = (Patient) session.getAttribute("patient1");
        System.out.println("doctor=>"+doctor);
        System.out.println("patient1=>"+patient);

        //获取当前时间字符串
        SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        //获取当天星期几
        SimpleDateFormat simpleFormatter2 = new SimpleDateFormat("EEEE");
        if(doctor != null && patient!=null){
            //获取当前时间
            Date date = new Date();
            System.out.println(date);
            //用来获得今天是星期几
            String weekDay = simpleFormatter2.format(date);
            //用来计算预约挂号时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //判断当天的星期与医生工作时间的星期的大小（因为如果医生工作的星期小于当天的星期，就需要预约到下一周了）;
            if(MyUtil.weekNumber(weekDay) < MyUtil.weekNumber(doctor.getWorkTime())){
                calendar.add(Calendar.DATE,MyUtil.weekNumber(doctor.getWorkTime())-MyUtil.weekNumber(weekDay));
            }else {
                calendar.add(Calendar.DATE,7);
            }
            date = calendar.getTime();
            System.out.println(date);

            //用来放置医生的id和预约挂号时间
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("doctorId",doctor.getId());
            map.put("date",simpleFormatter1.format(date));
            //获取病人挂号医生当天已经挂号的病人，用来计算病人的序号
            List<Reg> regList = regService.allPatientByDocAndTime(map);
            for (Reg reg : regList) {
                System.out.println(reg);
                if (reg.getPatientId() == patient.getId()) {
                    //当天已在该医生下挂号，不可挂好
                    flag = 0;
                    break;
                }
            }
            //当天未在该医生下挂号,可已挂号
            if(flag == 1){
                int seqNum = regList.size() + 1;
                System.out.println(seqNum);
                //将挂号信息放入数据库中
                Reg reg = new Reg();
                reg.setDoctorId(doctor.getId());
                reg.setPatientId(patient.getId());
                reg.setSeqNum(seqNum);
                reg.setDate(simpleFormatter1.format(date));
                reg.setType(1);
                System.out.println(reg);
                if (regService.add(reg)>0){
                    return "true";
                }
            }
        }
        return "false";
    }

    //具体病人的挂号列表
    @RequestMapping(value = "/p/listRegByPatientId")
    @ResponseBody
    public String listRegByPatientId(HttpSession session) throws JsonProcessingException {
        Patient patient = (Patient) session.getAttribute("patient1");
        if(patient != null){
            List<Reg> regList = regService.selectRegByPatientId(patient.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(regList);
        }
        return "null";
    }

    //支付挂号费
    @RequestMapping(value = "/p/payReg",method = RequestMethod.POST)
    @ResponseBody
    public String payReg(String id){
        if(regService.payReg(Integer.parseInt(id))>0){
            return "true";
        }
        return "false";
    }

    //取消挂号，有未缴费，已缴费，已诊，未诊
    @RequestMapping(value = "/cancelReg",method = RequestMethod.POST)
    @ResponseBody
    public String cancelReg(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Reg reg = objectMapper.readValue(json,Reg.class);
        if(regService.cancelReg(reg)>0){
            return "true";
        }
        System.out.println(reg);
        return "false";
    }

    //医生查看当天病人列表
    @RequestMapping(value = "/d/listRegByDoctorId",method = RequestMethod.POST)
    @ResponseBody
    public String listRegByDoctorId(HttpSession session) throws JsonProcessingException {
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        System.out.println(doctor);
        if(doctor != null){
            //获取当前时间字符串
            SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前时间
            Date date = new Date();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("doctorId",doctor.getId());
            map.put("date",simpleFormatter1.format(date));

            System.out.println(map);
            List<Reg> regList = regService.allPatientByDocAndTimeAndState(map);
            if(regList.size() > 0){
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(regList);
            }
        }
        return "null";
    }

    //医生点击就诊
    @RequestMapping(value = "/d/updateState",method = RequestMethod.POST)
    @ResponseBody
    public String updateState(String json) throws JsonProcessingException {
        //获取当前时间字符串
        SimpleDateFormat simpleFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date();
        //判断是否在工作日就诊
        ObjectMapper objectMapper = new ObjectMapper();
        Reg reg = objectMapper.readValue(json,Reg.class);
        System.out.println(reg);
        if(simpleFormatter1.format(date).equals(reg.getDate())){
            if(regService.updateState(reg.getId())>0){
                return "true";
            }
        }
        return "false";
    }

    //挂号信息列表
    @RequestMapping(value = "/regList",method = RequestMethod.POST)
    @ResponseBody
    public String regList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Reg> regList = regService.all();
        if(regList.size()>0){
            for (Reg reg : regList) {
                System.out.println(reg);
            }
            return objectMapper.writeValueAsString(regList);
        }
        return "null";
    }


}
