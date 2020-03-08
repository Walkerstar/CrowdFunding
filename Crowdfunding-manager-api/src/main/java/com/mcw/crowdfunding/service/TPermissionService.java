package com.mcw.crowdfunding.service;

import com.mcw.crowdfunding.bean.TPermission;
import org.apache.ibatis.ognl.DynamicSubscript;

import java.util.List;

/**
 * @author mcw 2019\12\6 0006-18:56
 */
public interface TPermissionService {
    List<TPermission> listPermissionTree();

    void savePermission(TPermission permission);

    TPermission getTPermissionById(Integer id);

    void updatePermission(TPermission permission);

    void deletePermission(Integer id);

    void assignPermissionToMenu(Integer mid, List<Integer> perIdArray);

    List<TPermission> getPermissionByMenuid(Integer mid);
}
