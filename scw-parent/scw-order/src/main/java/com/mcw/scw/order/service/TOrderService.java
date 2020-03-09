package com.mcw.scw.order.service;

import com.mcw.scw.order.bean.TOrder;
import com.mcw.scw.order.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.order.vo.resp.UserProjectVo;

import java.util.List;

/**
 * @author mcw 2019\12\20 0020-16:01
 */
public interface TOrderService {
    TOrder saveOrder(OrderInfoSubmitVo vo);

    List<TOrder> listSupportProject(Integer memberid);
}
