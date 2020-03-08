package com.mcw.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.bean.TRoleExample;
import com.mcw.crowdfunding.bean.TRolePermissionExample;
import com.mcw.crowdfunding.mapper.TAdminRoleMapper;
import com.mcw.crowdfunding.mapper.TRoleMapper;
import com.mcw.crowdfunding.mapper.TRolePermissionMapper;
import com.mcw.crowdfunding.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\4 0004-14:45
 */
@Service
public class TRoleServiceImpl implements TRoleService {

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TAdminRoleMapper adminRoleMapper;

    @Autowired
    TRolePermissionMapper rolePermissionMapper;

    @Override
    public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");

        List<TRole> roles =null;

        if(StringUtils.isEmpty(condition)){
           roles= roleMapper.selectByExample(null);
        }else {
            TRoleExample roleExample=new TRoleExample();
            roleExample.createCriteria().andNameLike("%"+condition+"%");
            roles= roleMapper.selectByExample(roleExample);
        }

        PageInfo<TRole> page=new PageInfo<>(roles,5);
        return page;
    }

    @Override
    public void saveTRole(TRole role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public TRole getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTRole(TRole role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteTRoleById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteTRoleBatch(List<Integer> idList) {
        roleMapper.deleteTRoleBatch(idList);
    }

    @Override
    public List<TRole> listAllRole() {
        return roleMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getRoleByAdminId(String id) {
        return adminRoleMapper.getRoleByAdminId(id);
    }

    @Override
    public void saveAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
        adminRoleMapper.saveAdminAndRoleRelationship(roleId,adminId);
    }

    @Override
    public void deleteAdminAndRoleRelationship(Integer[] roleId, Integer adminId) {
        adminRoleMapper.deleteAdminAndRoleRelationship(roleId,adminId);
    }

    @Override
    public void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids) {

        //先删除之前分配过的，然后在重新分配所有打钩的
        TRolePermissionExample exmple=new TRolePermissionExample();
        exmple.createCriteria().andRoleidEqualTo(roleId);
        rolePermissionMapper.deleteByExample(exmple);

        rolePermissionMapper.saveRoleAndPermissionRelationship(roleId,ids);
    }

    @Override
    public List<Integer> listPermissionIdByRoleId(Integer roleId) {
        return rolePermissionMapper.listPermissionIdByRoleId(roleId);
    }
}
