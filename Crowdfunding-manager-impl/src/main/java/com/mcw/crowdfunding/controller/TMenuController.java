package com.mcw.crowdfunding.controller;

import com.mcw.crowdfunding.bean.TMenu;
import com.mcw.crowdfunding.bean.TPermission;
import com.mcw.crowdfunding.service.TMenuService;
import com.mcw.crowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.apache.ibatis.ognl.DynamicSubscript.mid;


/**
 * @author mcw 2019\12\5 0005-10:35
 */
@Controller
public class TMenuController {

    @Autowired
    TMenuService menuService;

    @Autowired
    TPermissionService permissionService;

    @ResponseBody
    @RequestMapping("/menu/menu_permission")
    public  List<TPermission>  getPermissionByMenuid(@RequestParam("menuid") Integer mid) {
        // 查询出当前菜单能被哪些权限（自定义标识）操作
        return permissionService.getPermissionByMenuid(mid);
    }

    @ResponseBody
    @RequestMapping("/menu/doDelete")
    public String doDelete(Integer id) {
        menuService.deleteTMenu(id);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/menu/doUpdate")
    public String doUpdate(TMenu menu) {
        menuService.updateMenu(menu);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/menu/getMenuById")
    public TMenu getMenuById(Integer id) {
        TMenu menu = menuService.getMenuById(id);
        return menu;
    }

    @ResponseBody
    @RequestMapping("/menu/doAdd")
    public String doAdd(TMenu menu) {
        menuService.saveTMenu(menu);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/menu/loadTree")
    public List<TMenu> loadTree() {
        List<TMenu> list = menuService.listMenuAllTree();
        return list;
    }

    @RequestMapping("/menu/index")
    public String index() {
        return "menu1/index";
    }
}
