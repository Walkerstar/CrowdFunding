package com.mcw.crowdfunding.service.impl;

import com.mcw.crowdfunding.bean.TPermission;
import com.mcw.crowdfunding.bean.TPermissionMenuExample;
import com.mcw.crowdfunding.mapper.TPermissionMapper;
import com.mcw.crowdfunding.mapper.TPermissionMenuMapper;
import com.mcw.crowdfunding.service.TPermissionService;
import org.apache.ibatis.ognl.DynamicSubscript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mcw 2019\12\6 0006-19:03
 */
@Service
public class TPermissionServiceImpl implements TPermissionService {

    @Autowired
    TPermissionMapper permissionMapper;

    @Autowired
    TPermissionMenuMapper permissionMenuMapper;

    @Override
    public List<TPermission> listPermissionTree() {
        return permissionMapper.selectByExample(null);
    }

    @Override
    public void savePermission(TPermission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public TPermission getTPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePermission(TPermission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void assignPermissionToMenu(Integer mid, List<Integer> perIdArray) {
        //1.删除之前对应的权限
        TPermissionMenuExample example=new TPermissionMenuExample();
        example.createCriteria().andMenuidEqualTo(mid);
        permissionMenuMapper.deleteByExample(example);

        //2.插入提交过来的新的权限集合
        permissionMenuMapper.insertBatch(mid,perIdArray);
    }

    @Override
    public List<TPermission> getPermissionByMenuid(Integer mid) {
        return permissionMapper.getPermissionByMenuid(mid);
    }


}
