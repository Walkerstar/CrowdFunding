package com.mcw.scw.user.service.impl;

import com.mcw.scw.user.service.TOrderServiceFeign;
import com.mcw.scw.user.vo.resp.UserProjectVo;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mcw 2019\12\26 0026-20:05
 */
@Slf4j
@Component
public class TOrderServiceFeignExceptionHandler implements TOrderServiceFeign {
    @Override
    public AppResponse<List<UserProjectVo>> listSupportProject(Integer memberid) {
        AppResponse fail=AppResponse.fail(null);
        fail.setMsg("远程调用【获取我支持的项目】失败");
        log.info("远程调用【获取我支持的项目】失败");
        return fail;
    }
}
