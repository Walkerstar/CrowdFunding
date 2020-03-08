package com.mcw.crowdfunding.service;

import com.github.pagehelper.PageInfo;

import com.mcw.crowdfunding.bean.TType;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\30 0030-16:46
 */
public interface TypeService {
    PageInfo<TType> listTypePage(Map<String,Object> paramMap);

    void updateType(TType type);

    void deleteType(Integer id);

    TType getTypeById(Integer id);

    void saveType(TType type);

    void deleteBatch(List<Integer> idList);
}
