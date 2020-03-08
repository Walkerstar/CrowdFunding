package com.mcw.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.mcw.crowdfunding.bean.TCert;

import java.util.List;
import java.util.Map;

/**
 * @author mcw 2019\12\29 0029-18:25
 */
public interface TCertService {


    PageInfo<TCert> listCertPage(Map<String,Object> paramMap);

    TCert getCertById(Integer id);

    void deleteTCert(Integer id);


    void updateTCert(TCert cert);

    void saveTCert(TCert cert);

    void deleteBatch(List<Integer> idList);
}
