package com.mcw.scw.project.service.impl;

import com.mcw.scw.project.service.MemberServiceFeign;
import com.mcw.scw.project.vo.resp.TMember;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.stereotype.Component;

/**
 * @author mcw 2019\12\19 0019-20:11
 */
@Component
public class MemberServiceFeignHandler implements MemberServiceFeign {
    @Override
    public AppResponse<TMember> getMemberById(Integer id) {
        AppResponse<TMember> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【获取用户信息/发起人】服务失败");
        return fail;
    }

    @Override
    public AppResponse<Object> saveProjectFollow(Integer projectId, Integer memberId) {
        AppResponse<Object> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【往项目关注表里面插入数据】服务失败");
        return fail;
    }

    @Override
    public AppResponse<Object> deleteProjectFollow(Integer projectId, Integer memberId) {
        AppResponse<Object> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【往项目关注表里面删除数据】服务失败");
        return fail;
    }
}
