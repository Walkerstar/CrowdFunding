package com.mcw.crowdfunding.service;

import com.mcw.crowdfunding.bean.TMenu;

import java.util.List;

/**
 * @author mcw 2019\12\3 0003-11:06
 */
public interface TMenuService {
    List<TMenu> listMenuAll();

    List<TMenu> listMenuAllTree();

    void saveTMenu(TMenu menu);

    TMenu getMenuById(Integer id);

    void updateMenu(TMenu menu);

    void deleteTMenu(Integer id);
}
