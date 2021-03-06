package com.mcw.crowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.service.TAdminService;
import com.mcw.crowdfunding.service.TRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\3 0003-14:35
 */
@Controller
public class TAdminController {

    Logger log=LoggerFactory.getLogger(TAdminController.class);

    @Autowired
    TAdminService adminService;

    @Autowired
    TRoleService roleService;

    @ResponseBody
    @RequestMapping("admin/doUnAssign")
    public String toUnAssign(Integer[] roleId,Integer adminId){
        log.debug("adminId={}",adminId);

        for (Integer rId : roleId) {
            log.debug("roleId={}",rId);
        }

        roleService.deleteAdminAndRoleRelationship(roleId,adminId);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("admin/doAssign")
    public String toAssign(Integer[] roleId,Integer adminId){
        log.debug("adminId={}",adminId);

        for (Integer rId : roleId) {
            log.debug("roleId={}",rId);
        }

        roleService.saveAdminAndRoleRelationship(roleId,adminId);

        return "ok";
    }

    @RequestMapping("/admin/toAssign")
    public String toAssign(String id,Model model){

        //1.查询所有角色
        List<TRole> allList=roleService.listAllRole();

        //2.根据用户id查询已经拥有的角色id
        List<Integer>  roleIdList=roleService.getRoleByAdminId(id);

        //3.将所拥有的角色，进行划分
        List<TRole> assignList=new ArrayList<>();
        List<TRole> unAssignList=new ArrayList<>();

        model.addAttribute("assignList",assignList);
        model.addAttribute("unAssignList",unAssignList);

        for (TRole role : allList) {
            if(roleIdList.contains(role.getId())){
                //4.已分配集合角色
                assignList.add(role);
            }else {
                //5.未分配集合角色
                unAssignList.add(role);
            }
        }

        return "admin/assignRole";
    }


    @RequestMapping("/admin/doDeleteBatch")
    public String doDeleteBatch(String ids,Integer pn){

        List<Integer> idList=new ArrayList<>();

        String[] split = ids.split(",");

        for (String s : split) {
            int id = Integer.parseInt(s);
            idList.add(id);
        }

        log.debug("要删除的ID={}",idList);

        adminService.deleteBatch(idList);
        return "redirect:/admin/index?pn="+pn;
    }

    @RequestMapping("/admin/doDelete")
    public String doDelete(Integer id,Integer pn){
        adminService.deleteTAdmin(id);
        return "redirect:/admin/index?pn="+pn;
    }

    @RequestMapping("/admin/doUpdate")
    public String doUpdate(TAdmin admin,Integer pn){
        adminService.updateTAdmin(admin);
        return "redirect:/admin/index?pn="+pn;
    }

    @RequestMapping("/admin/toUpdate")
    public String toUpdate(Integer id,Model model){

        TAdmin admin = adminService.getTAdminById(id);

        model.addAttribute("admin",admin);

        return "/admin/update";
    }

    @PreAuthorize("hasRole('PM - 项目经理')")
    @RequestMapping("/admin/doAdd")
    public String doAdd(TAdmin admin){
        adminService.saveTAdmin(admin);
        return "redirect:/admin/index?pn="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/toAdd")
    public String toAdd(){
        log.debug("跳转到增加管理员页面");
        return "/admin/add";
    }

    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pn",required = false,defaultValue = "1") Integer pn,
                        @RequestParam(value = "size",required = false,defaultValue = "4") Integer size,
                        Model model,
                        String condition){
        log.debug("跳转到用户维护页面");
        Map<String,Object> paramMap=new HashMap<>();

        PageHelper.startPage(pn,size);

        paramMap.put("condition",condition);

        PageInfo<TAdmin> page=adminService.listAdminPage(paramMap);

        model.addAttribute("page",page);
        return "admin/index";
    }
}
