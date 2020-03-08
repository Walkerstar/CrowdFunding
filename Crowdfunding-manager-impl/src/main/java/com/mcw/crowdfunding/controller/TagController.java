package com.mcw.crowdfunding.controller;

import com.mcw.crowdfunding.bean.TTag;
import com.mcw.crowdfunding.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mcw 2019\12\30 0030-17:46
 */
@Controller
public class TagController {

    @Autowired
    TagService tagService;

    @ResponseBody
    @RequestMapping("/tag/doDelete")
    public String doDelete(Integer id){
        tagService.deleteTag(id);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/tag/doUpdate")
    public String doUpdate(TTag tag){
        tagService.updateTag(tag);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/tag/getTagById")
    public TTag getMenuById(Integer id ){
        return  tagService.getTagById(id);
    }


    @ResponseBody
    @RequestMapping("/tag/doAdd")
    public String doAdd(TTag tag){
        tagService.saveTag(tag);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/tag/loadTree")
    public List<TTag> loadTree(){
        List<TTag> list=tagService.listTagTree();
        return list;
    }

    @RequestMapping("/tag/index")
    public String index(){
        return "tag/index";
    }
}
