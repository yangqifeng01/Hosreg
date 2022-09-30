package com.hg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.model.Doctor;
import com.hg.model.IDVerifyMessage;
import com.hg.model.Patient;
import com.hg.service.PatientService;
import com.hg.util.MyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PatientController {
    @Resource
    private PatientService patientService;

    //病人注册账号
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public String register(Patient patient){
        System.out.println("==========================");
        System.out.println("register"+patient);
        System.out.println("==========================");
        String msg = "false";
        IDVerifyMessage idVerifyMessage = MyUtil.validateIdCard(patient.getIdNumber(),patient.getPatientName());
        System.out.println("idVerifyMessage: "+idVerifyMessage);
        if(idVerifyMessage.getStatus() == 400){
            msg = "false";
        }
        if(idVerifyMessage.getStatus() == 200){
            if(patientService.add(patient)>0){
                msg = "true";
            }
        }
        System.out.println(patient);
        return msg;
    }

    //患者登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(Patient patient, HttpSession session){
        String msg = "false";
        Patient patient1 = patientService.select(patient.getIdNumber());
        if(patient1 != null){
            if(patient1.getPassword().equals(patient.getPassword())){
                session.setAttribute("patient1",patient1);
                msg = "true";
            }
        }
        return msg;
    }

    //判断患者是否登录
    @RequestMapping(value = "/isLogin",method = RequestMethod.POST)
    @ResponseBody
    public String isLogin(HttpSession session){
        Patient patient = (Patient) session.getAttribute("patient1");
        if(patient != null){
            ObjectMapper objectMapper = new ObjectMapper();
            //余额问题
            Patient patient1 = patientService.selectById(patient.getId());
            session.setAttribute("patient1",patient1);
            try {
                return objectMapper.writeValueAsString(patient1);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "false";
    }

    //退出登录
    @RequestMapping("/p/exitLogin")
    public void exitLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        request.getSession().removeAttribute("patient1");
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    //当天挂号
    @RequestMapping(value = "/p/hosReg",method = RequestMethod.POST)
    @ResponseBody
    public String hosReg(HttpSession session){
        Patient patient = (Patient) session.getAttribute("patient1");
        Doctor doctor = (Doctor) session.getAttribute("doctorDetail");
        if(patient != null && doctor != null){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            String s = simpleDateFormat.format(date);
            if(s.equals(doctor.getWorkTime())){
                return "true";
            }
        }
        return "false";
    }

    //修改个人信息
    @RequestMapping(value = "/pUpdate",method = RequestMethod.POST)
    @ResponseBody
    public String update(String json,HttpSession session) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Patient patient = objectMapper.readValue(json,Patient.class);
        System.out.println(patient);
        if(patientService.update(patient)>0){
            session.setAttribute("patient1",patient);
            return "true";
        }
        return "false";
    }

    //显示病人列表
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    @ResponseBody
    public String all() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Patient> patientList = patientService.all();
        if(patientList.size()>0){
           return objectMapper.writeValueAsString(patientList);
        }
        return "null";
    }

    //根据id查找病人信息
    @RequestMapping(value = "/selectPatientById",method = RequestMethod.GET)
    @ResponseBody
    public String selectPatientById(String id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Patient patient = patientService.selectById(Integer.parseInt(id));
        if(patient != null){
            return objectMapper.writeValueAsString(patient);
        }
        return "null";
    }

    @RequestMapping(value = "/pDelete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id){
        if(patientService.delete(Integer.parseInt(id)) >0 ){
            return "true";
        }
        return "false";
    }
}
