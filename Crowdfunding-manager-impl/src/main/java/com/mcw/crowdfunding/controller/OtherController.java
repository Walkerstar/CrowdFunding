package com.mcw.crowdfunding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mcw 2019\12\30 0030-16:31
 */
@Controller
public class OtherController {

    //业务审核
    @RequestMapping("/auth_cert/index")
    public String auth_cert(){
        return "auth_cert/index";
    }

    @RequestMapping("/auth_adv/index")
    public String auth_adv(){
        return "auth_adv/index";
    }

    @RequestMapping("/auth_project/index")
    public String auth_project(){
        return "auth_project/index";
    }

    //业务管理
    @RequestMapping("/certtype/index")
    public String certtype(){
        return "certtype/index";
    }

    @RequestMapping("/process/index")
    public String process(){
        return "process/index";
    }

    @RequestMapping("/advert/index")
    public String advert(){
        return "advert/index";
    }

    @RequestMapping("/message/index")
    public String message(){
        return "message/index";
    }

    //
    @RequestMapping("/param/index")
    public String param(){
        return "param";
    }

}
