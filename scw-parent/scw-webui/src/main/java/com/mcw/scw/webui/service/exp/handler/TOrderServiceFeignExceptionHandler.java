package com.mcw.scw.webui.service.exp.handler;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.service.TOrderServiceFeign;
import com.mcw.scw.webui.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.webui.vo.resp.TOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mcw 2019\12\20 0020-14:54
 */
@Slf4j
@Component
public class TOrderServiceFeignExceptionHandler implements TOrderServiceFeign {
    @Override
    public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
        AppResponse<TOrder> resp=AppResponse.fail(null);
        resp.setMsg("调用远程的服务【保存订单】失败");
        log.error("调用远程的服务【保存订单】失败");
        return resp;
    }
}
