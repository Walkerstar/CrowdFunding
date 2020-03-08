package com.mcw.crowdfunding.controller;

import com.mcw.crowdfunding.bean.TPermission;
import com.mcw.crowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mcw 2019\12\6 0006-18:57
 */
@Controller
public class TPermissionController {

    @Autowired
    TPermissionService permissionService;


    @ResponseBody
    @RequestMapping("/permission/assignPermissionToMenu")
    public String assignPermissionToMenu(@RequestParam("mid") Integer mid,
                                         @RequestParam("perIds") String perIds){
        List<Integer> perIdArray=new ArrayList<>();
        String[] split = perIds.split(",");
        for (String s : split) {
            int id;
            try {
                id = Integer.parseInt(s);
                perIdArray.add(id);
            }catch (NumberFormatException e) {
            }
        }

        //1.将菜单和权限id集合的关系保存起来
        permissionService.assignPermissionToMenu(mid,perIdArray);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/permission/doDelete")
    public String doDelete(Integer id){
        permissionService.deletePermission(id);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/permission/doUpdate")
    public String doUpdate(TPermission permission){
        permissionService.updatePermission(permission);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/permission/getMenuById")
    public TPermission getMenuById(Integer id ){
        return  permissionService.getTPermissionById(id);
    }


    @ResponseBody
    @RequestMapping("/permission/doAdd")
    public String doAdd(TPermission permission){
        permissionService.savePermission(permission);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/permission/loadTree")
    public List<TPermission> loadTree(){
        List<TPermission> list=permissionService.listPermissionTree();
        return list;
    }

    @RequestMapping("/permission/index")
    public String index(){
        return "permission/index";
    }
}
