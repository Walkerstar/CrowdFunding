package com.mcw.scw.user.service;

import com.mcw.scw.user.bean.TMember;
import com.mcw.scw.user.bean.TMemberAddress;
import com.mcw.scw.user.bean.TMemberProjectFollow;
import com.mcw.scw.user.vo.req.UserRegistVo;
import com.mcw.scw.user.vo.resp.UserFollowProjectVo;
import com.mcw.scw.user.vo.resp.UserProjectVo;
import com.mcw.scw.user.vo.resp.UserRespVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mcw 2019\12\13 0013-15:40
 */
public interface TMemberService {
    int saveTMember(UserRegistVo vo);

    UserRespVo getUserByLogin(String loginacct, String password);

    TMember getMemberById(Integer id);

    List<TMemberAddress> listAddress(Integer memberId);

    List<TMemberProjectFollow> listFollowProject(Integer memberId);

    @Transactional(readOnly = false)
    void saveMemberFollow(Integer projectId, Integer memberId);

    @Transactional(readOnly = false)
    void deleteMemberFollow(Integer projectId, Integer memberId);
}
