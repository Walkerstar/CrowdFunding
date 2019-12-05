package com.mcw.crowdfunding.service.impl;

import com.mcw.crowdfunding.bean.TMenu;
import com.mcw.crowdfunding.mapper.TMenuMapper;
import com.mcw.crowdfunding.service.TMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\3 0003-11:07
 */

@Service
public class TMenuServiceImpl implements TMenuService {

    Logger log=LoggerFactory.getLogger(TMenuServiceImpl.class);

    @Autowired
    TMenuMapper menuMapper;

    @Override
    public List<TMenu> listMenuAll() {

        //存放父节点，再放孩子
        ArrayList<TMenu> menuList=new ArrayList<>();

        Map<Integer,TMenu> cache=new HashMap<>();

        List<TMenu> allList = menuMapper.selectByExample(null);

        //判断父节点
        for (TMenu menu : allList) {
            if (menu.getPid()==0){
                menuList.add(menu);
                cache.put(menu.getId(),menu);
            }
        }

        //添加孩子
        for (TMenu menu : allList) {
            if(menu.getPid()!=0){
                Integer pid = menu.getPid();
                TMenu parent = cache.get(pid);
                List<TMenu> children = parent.getChildren();
               children.add(menu);//根据子菜单的pid查找父菜单的id，然后根据父菜单childern属性进行父子关系组合
            }
        }

        log.debug("菜单={}",menuList);
        return menuList;
    }

    @Override
    public List<TMenu> listMenuAllTree() {
        return  menuMapper.selectByExample(null);
    }

    @Override
    public void saveTMenu(TMenu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public TMenu getMenuById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateMenu(TMenu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void deleteTMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }


}
