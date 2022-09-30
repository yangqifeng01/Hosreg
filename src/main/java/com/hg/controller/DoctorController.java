package com.hg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.model.Doctor;
import com.hg.model.Patient;
import com.hg.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class DoctorController {
    @Resource
    private DoctorService doctorService;

    //前台列表
    @RequestMapping(value = "/doctorList",method = RequestMethod.POST)
    @ResponseBody
    public String list()  {
        List<Doctor> list = doctorService.list();
        if(list != null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }

    //将要显示的医生具体信息放入session中
    @RequestMapping(value = "/getDoctorById",method = RequestMethod.GET)
    @ResponseBody
    public String getDoctorById(String id, HttpSession session) throws JsonProcessingException {
        System.out.println("getDoctorById => showDoctor");
        System.out.println(id);
        //根据前台提供的id查找医生
        Doctor doctor = doctorService.selectById(Integer.parseInt(id));
        //判断是否存在该医生
        if(doctor != null){
            //为了方便在html页面展示，将查到信息放入session中
            ObjectMapper objectMapper = new ObjectMapper();
            session.setAttribute("doctorDetail",doctor);
            System.out.println(session.getAttribute("doctor"));
            return objectMapper.writeValueAsString(doctor);
        }
        return "null";
    }

    //登录
    @RequestMapping(value = "/dLogin",method = RequestMethod.POST)
    @ResponseBody
    public String login(Doctor doctor, HttpSession session){
        System.out.println(doctor);
        String msg = "false";
        List<Doctor> doctorList = doctorService.selectByName(doctor.getDoctorName());
        if(doctorList.size() >0 ){
            for (Doctor doctor1 : doctorList) {
                System.out.println(doctor1);
                if(doctor1.getPassword().equals(doctor.getPassword())){
                    if(doctor1.getProfessionalTitle().equals("院长")){
                        session.setAttribute("aDoctor",doctor1);
                        msg = "1";
                    }else {
                        msg = "true";
                        session.setAttribute("doctor",doctor1);
                    }
                    break;
                }
            }
        }
        return msg;
    }

    //判断医生是否登录
    @RequestMapping(value = "/dIsLogin",method = RequestMethod.POST)
    @ResponseBody
    public String isLogin(HttpSession session) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //医生
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        System.out.println("dIslogin：doctor"+doctor);
        //院长
        Doctor aDoctor = (Doctor) session.getAttribute("aDoctor");
        System.out.println("dIslogin：aDoctor"+aDoctor);
        if(doctor != null){
            System.out.println(session.getAttribute("doctor"));
            return objectMapper.writeValueAsString(doctor);
        }
        if (aDoctor != null){
            System.out.println(session.getAttribute("aDoctor"));
            return objectMapper.writeValueAsString(aDoctor);
        }
        return "false";
    }

    //更新医生信息
    @RequestMapping(value = "/d/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(String json,HttpSession session) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Doctor doctor = objectMapper.readValue(json,Doctor.class);
        System.out.println("update："+doctor);
        //医生
        Doctor doctor1 = (Doctor) session.getAttribute("doctor");
        System.out.println("dIslogin：doctor"+doctor1);
        //院长
        Doctor aDoctor = (Doctor) session.getAttribute("aDoctor");
        System.out.println("dIslogin：aDoctor"+aDoctor);
        if(doctorService.update(doctor)>0){
            if(doctor1!=null && doctor1.getId()==doctor.getId()){
                System.out.println(session.getAttribute("doctor").toString());
                session.setAttribute("doctor",doctor);
            }
            if(aDoctor!=null && aDoctor.getId()==doctor.getId()){
                System.out.println(session.getAttribute("aDoctor").toString());
                session.setAttribute("aDoctor",doctor);
            }
            return "true";
        }
        return "false";
    }

    //退出登录
    @RequestMapping("/d/exitLogin")
    public void exitLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(request.getSession().getAttribute("doctor")!=null){
            request.getSession().removeAttribute("doctor");
        }
        if(request.getSession().getAttribute("aDoctor")!=null){
            request.getSession().removeAttribute("aDoctor");
        }
        response.sendRedirect(request.getContextPath()+"/dalogin.html");
    }

    //搜索医生
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String search(String str) throws JsonProcessingException {
        System.out.println(str);
        List<Doctor> doctorList = doctorService.search(str);
        if(doctorList.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(doctorList);
        }
        return "null";
    }

    //添加医生
    @RequestMapping(value = "/d/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(String json) throws JsonProcessingException {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Doctor doctor = objectMapper.readValue(json,Doctor.class);
        System.out.println(doctor);
        if(doctorService.add(doctor)>0){
            return "true";
        }
        return "false";
    }

    //删除医生
    @RequestMapping(value = "/d/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id){
        System.out.println(id);
        if(doctorService.delete(Integer.parseInt(id))>0){

            return "true";
        }
        return "false";
    }
}
