package com.mcw.crowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.service.TRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\4 0004-14:45
 */
@Controller
public class TRolecontroller {

    Logger log=LoggerFactory.getLogger(TRolecontroller.class);

    @Autowired
    TRoleService roleService;

    @ResponseBody
    @RequestMapping("/role/listPermissionIdByRoleId")
    public List<Integer> listPermissionIdByRoleId(Integer roleId){

        log.debug("roleId={}",roleId);

        List<Integer> list=roleService.listPermissionIdByRoleId(roleId);
        return list;
    }

    @ResponseBody
    @RequestMapping("/role/doAssignPermissionToRole")
    public String doAssignPermissionToRole(Integer roleId,String idList){
        log.debug("roleId={}",roleId);
        log.debug("permissionIds={}",idList);

        List<Integer> ids=new ArrayList<>();
        String[] split = idList.split(",");

        for (String id : split) {
            int permissionId = Integer.parseInt(id);
            ids.add(permissionId);
        }

        log.debug("许可ID={}",ids);
        roleService.saveRoleAndPermissionRelationship(roleId,ids);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/role/doDeleteBatch")
    public String doDeleteBatch(String idStr){

        List<Integer> idList=new ArrayList<>();

        String[] split = idStr.split(",");

        for (String s : split) {
            idList.add(Integer.parseInt(s));
        }

        roleService.deleteTRoleBatch(idList);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/doDelete")
    public String doDelete(Integer id){

        roleService.deleteTRoleById(id);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/doUpdate")
    public String doUpdate(TRole role){

        roleService.updateTRole(role);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/getRoleById")
    public TRole getRoleById(Integer id){
        TRole role=roleService.getRoleById(id);
        return role;
    }



    /**
     * //@PreAuthorize("hasRole('PM-项目经理')")
     * 1.放在此处不起作用，是因为CrowdFundingSecurityConfig的@EnableGlobalMethodSecurity(prePostEnabled = true)没有被 springMvc 扫描到，
     *    而是被 spring 扫描到了，所以将它放到 service 层中问题解决
     *
     * 2.另一种解决方法
     * 将spring容器和springmvc容器和为一体
     * 在web.xml中将加载IOC容器的listener去掉， 让springMvc扫描spring文件
     */
    @PreAuthorize("hasRole('PM - 项目经理')")
    @ResponseBody
    @RequestMapping("/role/doAdd")
    public String doAdd(TRole role){
        roleService.saveTRole(role);
        return "ok";
    }

    /**
     * 加上 @ResponseBody注解后，
     * 1.如果方法返回类型是String，则字符串原样输出
     * 2.如果方法返回类型是对象（List，Map，Entity Class），则序列化为json字符串
     * @param pn
     * @param size
     * @param condition
     * @return
     */
    @RequestMapping("/role/loadData")
    @ResponseBody
    public PageInfo<TRole> loadData(@RequestParam(value = "pn",required = false,defaultValue = "1") Integer pn,
                                    @RequestParam(value = "size",required = false,defaultValue = "2") Integer size,
                                    @RequestParam(value = "condition",required = false,defaultValue = "") String condition){
        PageHelper.startPage(pn,size);

        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("condition",condition);

        PageInfo<TRole> page=roleService.listRolePage(paramMap);


        return page;
    }

    @RequestMapping("/role/index")
    public String index(){
        return "role/index";
    }
}
