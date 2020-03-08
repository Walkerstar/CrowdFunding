package com.mcw.crowdfunding.service.impl;

import com.mcw.crowdfunding.bean.TTag;
import com.mcw.crowdfunding.mapper.TTagMapper;
import com.mcw.crowdfunding.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mcw 2019\12\30 0030-17:46
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TTagMapper tagMapper;

    @Override
    public void deleteTag(Integer id) {
        tagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTag(TTag tag) {
        tagMapper.updateByPrimaryKeySelective(tag);
    }

    @Override
    public TTag getTagById(Integer id) {
        return tagMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveTag(TTag tag) {
        tagMapper.insertSelective(tag);
    }

    @Override
    public List<TTag> listTagTree() {
        return tagMapper.selectByExample(null);
    }
}
