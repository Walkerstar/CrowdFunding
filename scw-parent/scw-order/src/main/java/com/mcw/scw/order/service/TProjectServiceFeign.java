package com.mcw.scw.order.service;

import com.mcw.scw.order.service.impl.TProjectServiceFeignExceptionHandler;
import com.mcw.scw.order.vo.resp.ProjectDetailVo;
import com.mcw.scw.order.vo.resp.TReturn;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mcw 2019\12\20 0020-16:29
 */
@FeignClient(value = "SCW-PROJECT",fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

    @GetMapping("/project/details/returns/info/{returnId}")
     AppResponse<TReturn> returnInfo(@PathVariable("returnId") Integer returnId);

    @GetMapping("/project/details/info")
    AppResponse<ProjectDetailVo> getdetailsInfo(@RequestParam("projectId") Integer projectId);
}
