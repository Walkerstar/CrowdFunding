package com.mcw.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TAdmin;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\2 0002-19:56
 */
public interface TAdminService {
    TAdmin getTAdminByLogin(Map<String,Object> map);

    PageInfo<TAdmin> listAdminPage(Map<String,Object> paramMap);

    void saveTAdmin(TAdmin admin);

    TAdmin getTAdminById(Integer id);

    void updateTAdmin(TAdmin admin);

    void deleteTAdmin(Integer id);

    void deleteBatch(List<Integer> idList);
}
