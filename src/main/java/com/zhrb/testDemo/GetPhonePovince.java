package com.zhrb.testDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhrb.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName GetPhonePovince
 * @Description
 * @Author Administrator
 * @Date 2019/9/29 9:29
 * @Version
 */
public class GetPhonePovince {
    public static void main(String[] args) {
        String[] phones = {"18198156780",
                "18003434568"
        };
        List<String> phoneList = new ArrayList<>();
        phoneList = Arrays.asList(phones);
        String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
        String phoneAndPro = "";
        List<String> phoneAndProList = new ArrayList<>();
        for (int i = 0;i < phoneList.size();i ++){
            phoneAndPro = getPro(phoneList.get(i),url);
            phoneAndProList.add(phoneAndPro);
        }
        System.out.println(phoneAndProList);
    }

    private static String getPro(String phone,String url){
        String phoneAndPro = "";
        String response = HttpClientUtil.getHttp(url+phone);
        int index=response.indexOf("=");
        String pro = response.substring(index+1);
        JSONObject jsonObject = JSON.parseObject(pro);

        String telString = (String)jsonObject.get("telString");
        String province = (String)jsonObject.get("province");
        phoneAndPro = telString + "-" + province;
        return phoneAndPro;
    }
}
