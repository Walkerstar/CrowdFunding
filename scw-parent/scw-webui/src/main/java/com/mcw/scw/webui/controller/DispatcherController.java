package com.mcw.scw.webui.controller;

import com.alibaba.fastjson.JSON;
import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.constant.ProjectConstants;
import com.mcw.scw.webui.constant.UserConstants;
import com.mcw.scw.webui.service.TMemberServiceFeign;
import com.mcw.scw.webui.service.TProjectServiceFeign;
import com.mcw.scw.webui.vo.req.UserRegistVo;
import com.mcw.scw.webui.vo.resp.ProjectVo;
import com.mcw.scw.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author mcw 2019\12\17 0017-21:04
 */
@Controller
public class DispatcherController {

    @Autowired
    TMemberServiceFeign memberServiceFeign;

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/logout")
    public String logout(HttpSession session){

        if(session!=null){
            session.removeAttribute(UserConstants.LOGIN_MEMBER);
            session.invalidate();
        }
        return "redirect:/index";
    }

    @RequestMapping("/regist")
    public String regist(UserRegistVo vo){

        memberServiceFeign.register(vo);
        return "member/member";
    }

    @RequestMapping("/getCode")
    public AppResponse getCode(String loginacct){

        try {
            memberServiceFeign.sendsms(loginacct);
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail("发送短信失败");
        }
    }

    @RequestMapping("/toregist")
    public String toregist(){
        return "reg";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, HttpSession session){
        AppResponse<UserRespVo> login = memberServiceFeign.login(loginacct, userpswd);

        UserRespVo data = login.getData();

        if (data == null) {
            return "login";
        }
        session.setAttribute(UserConstants.LOGIN_MEMBER, data);

        String preUrl = (String) session.getAttribute("preUrl");
        if(StringUtils.isEmpty(preUrl)){
            return "redirect:/index";
        }else {
            return "redirect:"+preUrl;
        }
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model){
        List<ProjectVo> data = (List<ProjectVo>) redisTemplate.opsForValue().get("projectInfo");
        if(data==null){

            AppResponse<List<ProjectVo>> resp = projectServiceFeign.allHost();
            data = resp.getData();

            redisTemplate.opsForValue().set("projectInfo",data,1,TimeUnit.SECONDS);
        }
        model.addAttribute(ProjectConstants.PROJECTVO_LIST,data);


        return "index";
    }
}
