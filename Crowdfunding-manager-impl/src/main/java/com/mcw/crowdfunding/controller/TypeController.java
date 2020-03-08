package com.mcw.crowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TType;
import com.mcw.crowdfunding.service.TypeService;
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
 * @author mcw 2019\12\30 0030-16:42
 */
@Controller
public class TypeController {

    Logger log=LoggerFactory.getLogger(TypeController.class);

    @Autowired
    TypeService typeService;

    @RequestMapping("/type/doDeleteBatch")
    public String doDeleteBatch(String ids,Integer pn){

        List<Integer> idList=new ArrayList<>();

        String[] split = ids.split(",");

        for (String s : split) {
            int id = Integer.parseInt(s);
            idList.add(id);
        }

        log.debug("要删除的ID={}",idList);

        typeService.deleteBatch(idList);
        return "redirect:/type/index?pn="+pn;
    }

    @ResponseBody
    @RequestMapping("/type/doAdd")
    public String doAdd(TType type){
        typeService.saveType(type);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/type/getTypeById")
    public TType getCertById(Integer id){
        TType type=typeService.getTypeById(id);
        return type;
    }

    @RequestMapping("/type/doDelete")
    public String doDelete(Integer id,Integer pn){
        typeService.deleteType(id);
        return "redirect:/type/index?pn="+pn;
    }

    @ResponseBody
    @RequestMapping("/type/doUpdate")
    public String doUpdate(TType type){
        typeService.updateType(type);
        return "ok";
    }

    @RequestMapping("/type/index")
    public String index(@RequestParam(value = "pn",required = false,defaultValue = "1") Integer pn,
                       @RequestParam(value = "size",required = false,defaultValue = "4") Integer size,
                        Model model,
                        String condition){
        log.debug("跳转到资质维护页面");

        Map<String,Object> paramMap=new HashMap<>();

        PageHelper.startPage(pn,size);

        paramMap.put("condition",condition);

        PageInfo<TType> page=typeService.listTypePage(paramMap);

        model.addAttribute("page",page);
        return "type/index";
    }
}
