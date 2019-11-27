package com.mcw.crowdfunding.manager.service.impl;

import com.mcw.crowdfunding.manager.dao.TestDao;
import com.mcw.crowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mcw
 * @date 2019\11\27 0027-16:33
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;


    @Override
    public void insert() {
        Map map=new HashMap();
        map.put("name","zhangsna");

        testDao.insert(map);
    }
}
