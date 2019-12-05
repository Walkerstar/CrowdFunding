package com.mcw.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TAdminExample;
import com.mcw.crowdfunding.exception.LoginException;
import com.mcw.crowdfunding.mapper.TAdminMapper;
import com.mcw.crowdfunding.service.TAdminService;
import com.mcw.crowdfunding.util.AppDateUtils;
import com.mcw.crowdfunding.util.Const;
import com.mcw.crowdfunding.util.MD5Util;
import org.apache.commons.math3.analysis.solvers.MullerSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\2 0002-19:58
 */

@Service
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    TAdminMapper adminMapper;


    @Override
    public TAdmin getTAdminByLogin(Map<String, Object> map) {

        //1.密码加密

        //2.查询用户
        String loginacct = (String) map.get("loginacct");
        String userpswd = (String) map.get("userpswd");

        //3.判断用户是否为空
        TAdminExample adminExample = new TAdminExample();

        adminExample.createCriteria().andLoginacctEqualTo(loginacct);

        List<TAdmin> list = adminMapper.selectByExample(adminExample);

        if (list == null || list.size() == 0) {
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }

        TAdmin admin = list.get(0);

        //4.判断密码是否一致
        if (!admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
            throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
        }
        //5.返回结果
        return admin;

    }

    @Override
    public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");

        TAdminExample example = new TAdminExample();

        if( condition != null ) {
            example.createCriteria().andLoginacctLike("%"+condition+"%");

            TAdminExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameLike("%"+condition+"%");

            TAdminExample.Criteria criteria1 = example.createCriteria();
            criteria1.andEmailLike("%"+condition+"%");

            example.or(criteria);
            example.or(criteria1);
        }


        List<TAdmin> adminList = adminMapper.selectByExample(example);

        PageInfo<TAdmin> pageInfo = new PageInfo<>(adminList, 5);

        return pageInfo;
    }

    @Override
    public void saveTAdmin(TAdmin admin) {
        admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
        admin.setCreatetime(AppDateUtils.getFormatTime());
        adminMapper.insertSelective(admin);

    }

    @Override
    public TAdmin getTAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateTAdmin(TAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void deleteTAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        adminMapper.deleteBatch(idList);
    }
}
