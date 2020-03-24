package com.mcw.scw.webui.service.exp.handler;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.service.TMemberServiceFeign;
import com.mcw.scw.webui.vo.req.UserRegistVo;
import com.mcw.scw.webui.vo.resp.UserAddressVo;
import com.mcw.scw.webui.vo.resp.UserFollowProjectVo;
import com.mcw.scw.webui.vo.resp.UserProjectVo;
import com.mcw.scw.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mcw 2019\12\18 0018-20:50
 */
@Slf4j
@Component
public class TMemberServiceFeignExceptionHandler implements TMemberServiceFeign {
    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【登录】服务失败");

        log.error("调用远程【登录】服务失败");
        return fail;
    }

    @Override
    public AppResponse<Object> register(UserRegistVo vo) {
        AppResponse<Object> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【注册】服务失败");

        log.error("调用远程【注册】服务失败");
        return fail;
    }

    @Override
    public AppResponse<Object> sendsms(String loginacct) {
        AppResponse<Object> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【获取短信验证码】服务失败");

        log.error("调用远程【获取短信验证码】服务失败");
        return fail;
    }

    @Override
    public AppResponse<List<UserAddressVo>> address(String accessToken) {
        AppResponse<List<UserAddressVo>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【获取用户地址】服务失败");

        log.error("调用远程【获取用户地址】服务失败");
        return fail;
    }

    @Override
    public AppResponse<List<UserProjectVo>> support(String accessToken) {
        AppResponse<List<UserProjectVo>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【获取用户支持的项目】服务失败");

        log.error("调用远程【获取用户支持的项目】服务失败");
        return fail;
    }

    @Override
    public AppResponse<List<UserFollowProjectVo>> star(String accessToken) {
        AppResponse<List<UserFollowProjectVo>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程【获取用户关注的项目】服务失败");

        log.error("调用远程【获取用户关注的项目】服务失败");
        return fail;
    }
}
