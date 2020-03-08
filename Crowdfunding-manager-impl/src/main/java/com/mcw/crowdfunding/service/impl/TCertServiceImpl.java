package com.mcw.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TCert;
import com.mcw.crowdfunding.bean.TCertExample;
import com.mcw.crowdfunding.mapper.TCertMapper;
import com.mcw.crowdfunding.service.TCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\29 0029-18:26
 */
@Service
public class TCertServiceImpl implements TCertService {

    @Autowired
    TCertMapper certMapper;


    @Override
    public PageInfo<TCert> listCertPage(Map<String, Object> paramMap) {
        String condition = (String) paramMap.get("condition");

        TCertExample example = new TCertExample();

        if( condition != null ) {
            example.createCriteria().andNameLike("%"+condition+"%") ;
        }

        List<TCert> certList = certMapper.selectByExample(example);

        PageInfo<TCert> pageInfo = new PageInfo<>(certList, 3);

        return pageInfo;
    }

    @Override
    public TCert getCertById(Integer id) {
        return certMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteTCert(Integer id) {
        certMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTCert(TCert cert) {
        certMapper.updateByPrimaryKeySelective(cert);
    }

    @Override
    public void saveTCert(TCert cert) {
        certMapper.insertSelective(cert);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        certMapper.deleteBatch(idList);
    }


}
