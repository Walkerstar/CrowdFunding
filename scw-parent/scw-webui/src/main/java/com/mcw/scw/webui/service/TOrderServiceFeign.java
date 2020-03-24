package com.mcw.scw.webui.service;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.service.exp.handler.TOrderServiceFeignExceptionHandler;
import com.mcw.scw.webui.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.webui.vo.resp.TOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author mcw 2019\12\20 0020-14:53
 */
@FeignClient(value = "SCW-ORDER",fallback =TOrderServiceFeignExceptionHandler.class )
public interface TOrderServiceFeign {

    /**
     * 远程调用参数传递问题
     *
     * 1.简单参数  @RequestParam @PathVariable
     *
     * 2.复杂对象 @RequestBody
     * @param orderInfoSubmitVo
     * @return
     */
    @PostMapping("/order/saveOrder")
    AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo);
}
