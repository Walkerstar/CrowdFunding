package com.mcw.scw.order.service.impl;

import com.mcw.scw.order.service.TProjectServiceFeign;
import com.mcw.scw.order.vo.resp.ProjectDetailVo;
import com.mcw.scw.order.vo.resp.TReturn;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mcw 2019\12\20 0020-16:30
 */
@Slf4j
@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {
    @Override
    public AppResponse<TReturn> returnInfo(Integer returnId) {
        AppResponse<TReturn> fail=AppResponse.fail(null);
        fail.setMsg("调用远程【获取项目某个回报档位信息】失败");
        log.error("调用远程【获取项目某个回报档位信息】失败");
        return fail;
    }

    @Override
    public AppResponse<ProjectDetailVo> getdetailsInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> fail=AppResponse.fail(null);
        fail.setMsg("调用远程【获取某个项目信息详情】失败");
        log.error("调用远程【获取某个项目信息详情】失败");
        return fail;
    }
}
