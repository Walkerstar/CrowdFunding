package com.mcw.crowdfunding.service;

import com.mcw.crowdfunding.bean.TTag;

import java.util.List;

/**
 * @author mcw 2019\12\30 0030-17:46
 */
public interface TagService {
    void deleteTag(Integer id);

    void updateTag(TTag tag);

    TTag getTagById(Integer id);

    void saveTag(TTag tag);

    List<TTag> listTagTree();
}
