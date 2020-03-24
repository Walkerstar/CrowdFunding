package com.mcw.scw.user.service.impl;

import com.mcw.scw.user.service.TProjectServiceFeign;
import com.mcw.scw.user.vo.resp.ProjectDetailVo;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mcw 2019\12\27 0027-20:25
 */
@Component
@Slf4j
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {
    @Override
    public AppResponse<ProjectDetailVo> getdetailsInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> fail=AppResponse.fail(null);
        fail.setMsg("user远程调用【获取某个项目的详情】失败");
        log.info("user远程调用【获取某个项目的详情】失败");
        return fail;
    }
}
