package com.mcw.scw.user.service;

import com.mcw.scw.user.service.impl.TOrderServiceFeignExceptionHandler;
import com.mcw.scw.user.vo.resp.UserProjectVo;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author mcw 2019\12\26 0026-19:58
 */
@FeignClient(value ="SCW-ORDER",fallback =TOrderServiceFeignExceptionHandler.class )
public interface TOrderServiceFeign {

    @GetMapping("/order/listSupportProject")
    AppResponse<List<UserProjectVo>> listSupportProject(@RequestParam("memberid") Integer memberid);
}
