package com.mcw.scw.project.service;

import com.mcw.scw.project.service.impl.MemberServiceFeignHandler;
import com.mcw.scw.project.vo.resp.TMember;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mcw 2019\12\19 0019-20:11
 */
@FeignClient(value = "SCW-USER",fallback =MemberServiceFeignHandler.class)
public interface MemberServiceFeign {

    @GetMapping("/user/info/getMemberById")
    AppResponse<TMember> getMemberById(@RequestParam("id") Integer id);

    @GetMapping("/user/info/save/projectFollow")
    AppResponse<Object> saveProjectFollow(@RequestParam("projectId")Integer projectId,@RequestParam("memberId")Integer memberId);

    @GetMapping("/user/info/delete/projectFollow")
    AppResponse<Object> deleteProjectFollow(@RequestParam("projectId")Integer projectId,@RequestParam("memberId")Integer memberId);
}
