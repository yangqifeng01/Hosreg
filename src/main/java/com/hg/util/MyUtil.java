package com.hg.util;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.model.IDVerifyMessage;
import com.hg.model.Reg;
import com.hg.service.RegService;
import com.hg.service.RegServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUtil {

    //判断身份证号与姓名是否一致
    public static IDVerifyMessage validateIdCard(String idNumber,String name){
        String path = "https://gwgp-akpqpvpbcku.n.bdcloudapi.com/s/api/ocr/cloudCode/IdCard";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);
        request.setCredentials("ee95a5b247904de19784b5d137b800c7", "b7cbf89b38fe41479f653e75da342432");

        request.addHeaderParameter("Content-Type", "application/json;charset=UTF-8");

        request.addQueryParameter("idCard", idNumber);
        request.addQueryParameter("realName", name);

        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = new ApiExplorerResponse();
            response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            System.out.println(response.getResult());
            String json = response.getResult();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, IDVerifyMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将日期的字符串形式转换为对应的数字
    public static int weekNumber(String weekDay){
        int weekNumber = 0;
        switch (weekDay){
            case "星期一":
                weekNumber = 1;
                break;
            case "星期二":
                weekNumber = 2;
                break;
            case "星期三":
                weekNumber = 3;
                break;
            case "星期四":
                weekNumber = 4;
            case "星期五":
                weekNumber = 5;
            case "星期六":
                weekNumber = 6;
            case "星期日":
                weekNumber = 7;
        }
        return weekNumber;
    }

}
