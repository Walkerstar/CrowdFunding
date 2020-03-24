package com.mcw.scw.webui.service;

import com.mcw.scw.webui.service.exp.handler.TMemberServiceFeignExceptionHandler;
import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.vo.req.UserRegistVo;
import com.mcw.scw.webui.vo.resp.UserAddressVo;
import com.mcw.scw.webui.vo.resp.UserFollowProjectVo;
import com.mcw.scw.webui.vo.resp.UserProjectVo;
import com.mcw.scw.webui.vo.resp.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mcw 2019\12\18 0018-20:47
 */
@FeignClient(value = "SCW-USER",fallback = TMemberServiceFeignExceptionHandler.class)
public interface TMemberServiceFeign {

    @PostMapping("/user/login")
    AppResponse<UserRespVo> login(@RequestParam("loginacct") String loginacct,@RequestParam("password") String password);

    @PostMapping("/user/register")
    AppResponse<Object> register(@RequestBody UserRegistVo vo);

    @PostMapping("/user/sendsms")
    AppResponse<Object> sendsms(String loginacct);

    @GetMapping("/user/info/address")
    AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);

    @GetMapping("/user/info/support/project")
    AppResponse<List<UserProjectVo>> support(@RequestParam("accessToken") String accessToken);

    @GetMapping("/user/info/star/project")
    AppResponse<List<UserFollowProjectVo>> star(@RequestParam("accessToken")String accessToken);

}
