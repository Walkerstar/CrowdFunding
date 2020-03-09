package com.mcw.scw.user.component;

import com.mcw.scw.vo.resp.AppResponse;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mcw 2019\12\13 0013-11:20
 */
@Component
public class SmsTemplate {

    @Value("${sms.host}")
    String host ;

    @Value("${sms.path}")
    String path ;

    @Value("${sms.method}")
    String method ;

    @Value("${sms.appcode}")
    String appcode ;

    public AppResponse<String>  sendSms(Map<String,String> querys) {

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("mobile", "15527556781");
//        querys.put("param", "code:1234");
//        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<String, String>();


        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            return AppResponse.ok(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }
}
