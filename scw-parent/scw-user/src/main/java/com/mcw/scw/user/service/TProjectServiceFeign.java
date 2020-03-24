package com.mcw.scw.user.service;

import com.mcw.scw.user.service.impl.TProjectServiceFeignExceptionHandler;
import com.mcw.scw.user.vo.resp.ProjectDetailVo;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mcw 2019\12\27 0027-20:24
 */
@FeignClient(value="SCW-PROJECT",fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

    @GetMapping("/project/details/info")
    AppResponse<ProjectDetailVo> getdetailsInfo(@RequestParam("projectId") Integer projectId);
}
