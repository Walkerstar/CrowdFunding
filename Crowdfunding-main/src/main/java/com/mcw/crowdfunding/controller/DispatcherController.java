package com.mcw.crowdfunding.controller;

import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TMenu;
import com.mcw.crowdfunding.service.TAdminService;
import com.mcw.crowdfunding.service.TMenuService;
import com.mcw.crowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\2 0002-16:13
 */
@Controller
public class DispatcherController {
    Logger log=LoggerFactory.getLogger(DispatcherController.class);

    @Autowired
    TAdminService adminService;

    @Autowired
    TMenuService menuService;

    @RequestMapping("/index")
    public String index(){
        log.debug("跳转到主页面");
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        log.debug("跳转到登录页面");
        return "login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        log.debug("跳转到后台系统main页面");

        if(session==null){
            return "redirect:/login";
        }
        List<TMenu> menus= (List<TMenu>) session.getAttribute(Const.MENULIST);

        if(menus==null){
            //存放父对象
             menus = menuService.listMenuAll();
            session.setAttribute(Const.MENULIST,menus);
        }

        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        log.debug("注销并跳转到主页面");
        if (session != null) {
            session.removeAttribute(Const.LOGIN_ADMIN);
            session.invalidate();
        }
        return "redirect:/index";
    }


    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, HttpSession session, Model model){
        log.debug("开始登录");
        log.debug("loginaccct={}",loginacct);
//        log.debug("userpswd={}",userpswd);

        Map<String,Object> map=new HashMap<>();
        map.put("loginacct",loginacct);
        map.put("userpswd",userpswd);

        try {
            TAdmin admin=adminService.getTAdminByLogin(map);
            session.setAttribute(Const.LOGIN_ADMIN,admin);

            log.debug("登录成功");
            return "redirect:/main";
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("登录失败{}",e.getMessage());
            model.addAttribute(Const.ERROR_MESSAGE,e.getMessage());
            return "login";
        }

    }
}
