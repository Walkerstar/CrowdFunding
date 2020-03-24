package com.mcw.scw.user.service.impl;

import com.mcw.scw.user.bean.*;
import com.mcw.scw.user.exp.UserException;
import com.mcw.scw.user.enums.UserExceptionEnum;
import com.mcw.scw.user.mapper.TMemberAddressMapper;
import com.mcw.scw.user.mapper.TMemberMapper;
import com.mcw.scw.user.mapper.TMemberProjectFollowMapper;
import com.mcw.scw.user.service.TMemberService;
import com.mcw.scw.user.vo.req.UserRegistVo;
import com.mcw.scw.user.vo.resp.UserFollowProjectVo;
import com.mcw.scw.user.vo.resp.UserProjectVo;
import com.mcw.scw.user.vo.resp.UserRespVo;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author mcw 2019\12\13 0013-15:41
 */
@Slf4j
@Transactional(readOnly = true)
@Service
public class TMemberServiceImpl implements TMemberService {

    @Autowired
    TMemberMapper memberMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TMemberAddressMapper memberAddressMapper;

    @Autowired
    TMemberProjectFollowMapper memberProjectFollowMapper;

    @Transactional
    @Override
    public int saveTMember(UserRegistVo vo) {

        try {
            //将vo属性对拷到do对象中

            TMember member = new TMember();
            BeanUtils.copyProperties(vo,member);
            member.setUsername(vo.getLoginacct());

            String userpswd = vo.getUserpswd();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            member.setUserpswd(encoder.encode(userpswd));

            int i = memberMapper.insertSelective(member);

            log.debug("注册会员成功--{}",member);
            return i;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("注册失败--{}",e.getMessage());
            throw new UserException(UserExceptionEnum.USER_SAVE_ERROR);
        }
    }

    @Override
    public UserRespVo getUserByLogin(String loginacct, String password) {

        UserRespVo vo = new UserRespVo();

        TMemberExample example=new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(loginacct);

        List<TMember> list = memberMapper.selectByExample(example);

        if(list==null || list.size()==0){
            throw new UserException(UserExceptionEnum.USER_UNEXITS);
        }

        TMember member = list.get(0);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password,member.getUserpswd())) {
            throw new UserException(UserExceptionEnum.USER_UNEXITS);
        }

        BeanUtils.copyProperties(member,vo);
        String accessToken=UUID.randomUUID().toString().replaceAll("-","");
        vo.setAccessToken(accessToken);

        stringRedisTemplate.opsForValue().set(accessToken,member.getId().toString());

        return vo;
    }

    @Override
    public TMember getMemberById(Integer id) {
        TMember member = memberMapper.selectByPrimaryKey(id);
        member.setUserpswd(null);

        return member;
    }

    @Override
    public List<TMemberAddress> listAddress(Integer memberId) {
        TMemberAddressExample example=new TMemberAddressExample();
        example.createCriteria().andMemberidEqualTo(memberId);

        List<TMemberAddress> list = memberAddressMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<TMemberProjectFollow> listFollowProject(Integer memberId) {
        TMemberProjectFollowExample example=new TMemberProjectFollowExample();
        example.setDistinct(true);
        example.createCriteria().andMemberidEqualTo(memberId);

        List<TMemberProjectFollow> follows = memberProjectFollowMapper.selectByExample(example);

        return follows;
    }

    @Override
    public void saveMemberFollow(Integer projectId, Integer memberId) {
        TMemberProjectFollow follow = new TMemberProjectFollow();
        follow.setMemberid(memberId);
        follow.setProjectid(projectId);
        memberProjectFollowMapper.insertSelective(follow);
    }

    @Override
    public void deleteMemberFollow(Integer projectId, Integer memberId) {
        TMemberProjectFollowExample example=new TMemberProjectFollowExample();
        example.createCriteria().andMemberidEqualTo(memberId).andProjectidEqualTo(projectId);

        memberProjectFollowMapper.deleteByExample(example);
    }

}
