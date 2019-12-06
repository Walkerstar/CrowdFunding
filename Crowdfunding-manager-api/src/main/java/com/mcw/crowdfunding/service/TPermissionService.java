package com.mcw.crowdfunding.service;

import com.mcw.crowdfunding.bean.TPermission;

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
}
