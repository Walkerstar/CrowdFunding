package com.mcw.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.*;
import com.mcw.crowdfunding.mapper.TTypeMapper;
import com.mcw.crowdfunding.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\30 0030-16:46
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TTypeMapper typeMapper;

    @Override
    public PageInfo<TType> listTypePage(Map<String, Object> paramMap) {
        String condition = (String) paramMap.get("condition");

        TTypeExample example = new TTypeExample();

        if( condition != null ) {
            example.createCriteria().andNameLike("%"+condition+"%") ;

            TTypeExample.Criteria criteria = example.createCriteria();
            criteria.andRemarkLike("%"+condition+"%");

            example.or(criteria);
        }

        List<TType> certList = typeMapper.selectByExample(example);

        PageInfo<TType> pageInfo = new PageInfo<>(certList, 3);

        return pageInfo;
    }

    @Override
    public void updateType(TType type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    @Override
    public void deleteType(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TType getTypeById(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveType(TType type) {
        typeMapper.insertSelective(type);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        typeMapper.deleteBatch(idList);
    }
}
