package com.mcw.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.bean.TRoleExample;
import com.mcw.crowdfunding.mapper.TRoleMapper;
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
}
