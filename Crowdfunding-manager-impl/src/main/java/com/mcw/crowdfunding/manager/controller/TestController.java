package com.mcw.crowdfunding.manager.controller;

import com.mcw.crowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mcw
 * @date 2019\11\27 0027-16:23
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;
    @RequestMapping("/test")
    public String test(){
        System.out.println("testcontroller");
        testService.insert();
        return "success";
    }
}
