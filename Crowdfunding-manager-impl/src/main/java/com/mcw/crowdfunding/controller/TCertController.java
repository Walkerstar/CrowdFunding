package com.mcw.crowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TCert;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.service.TCertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author mcw 2019\12\29 0029-18:15
 */
@Controller
public class TCertController {
    Logger log=LoggerFactory.getLogger(TCertController.class);

    @Autowired
    TCertService certService;

    @RequestMapping("/cert/doDeleteBatch")
    public String doDeleteBatch(String ids,Integer pn){

        List<Integer> idList=new ArrayList<>();

        String[] split = ids.split(",");

        for (String s : split) {
            int id = Integer.parseInt(s);
            idList.add(id);
        }

        log.debug("要删除的ID={}",idList);

        certService.deleteBatch(idList);
        return "redirect:/cert/index?pn="+pn;
    }

    @ResponseBody
    @RequestMapping("/cert/doAdd")
    public String doAdd(TCert cert){
        certService.saveTCert(cert);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/cert/getCertById")
    public TCert getCertById(Integer id){
        TCert cert=certService.getCertById(id);
        return cert;
    }

    @RequestMapping("/cert/doDelete")
    public String doDelete(Integer id,Integer pn){
        certService.deleteTCert(id);
        return "redirect:/cert/index?pn="+pn;
    }

    @ResponseBody
    @RequestMapping("/cert/doUpdate")
    public String doUpdate(TCert cert){
        certService.updateTCert(cert);
        return "ok";
    }

    @RequestMapping("/cert/index")
    public String index(@RequestParam(value = "pn",required = false,defaultValue = "1") Integer pn,
                        @RequestParam(value = "size",required = false,defaultValue = "4") Integer size,
                        Model model,
                        String condition){
        log.debug("跳转到资质维护页面");

        Map<String,Object> paramMap=new HashMap<>();

        PageHelper.startPage(pn,size);

        paramMap.put("condition",condition);

        PageInfo<TCert> page=certService.listCertPage(paramMap);

        model.addAttribute("page",page);
        return "cert/index";
    }
}
