package com.mcw.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TRole;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\4 0004-14:44
 */
public interface TRoleService {
    PageInfo<TRole> listRolePage(Map<String,Object> paramMap);

    void saveTRole(TRole role);

    TRole getRoleById(Integer id);

    void updateTRole(TRole role);

    void deleteTRoleById(Integer id);

    void deleteTRoleBatch(List<Integer> idList);
}
