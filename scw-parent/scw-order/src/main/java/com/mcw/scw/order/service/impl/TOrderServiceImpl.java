package com.mcw.scw.order.service.impl;

import com.mcw.scw.enums.OrderStatusEnumes;
import com.mcw.scw.order.bean.TOrder;
import com.mcw.scw.order.bean.TOrderExample;
import com.mcw.scw.order.mapper.TOrderMapper;
import com.mcw.scw.order.service.TOrderService;
import com.mcw.scw.order.service.TProjectServiceFeign;
import com.mcw.scw.order.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.order.vo.resp.TReturn;
import com.mcw.scw.order.vo.resp.UserProjectVo;
import com.mcw.scw.util.AppDateUtils;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author mcw 2019\12\20 0020-16:02
 */
@Slf4j
@Service
public class TOrderServiceImpl implements TOrderService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TOrderMapper orderMapper;

    @Autowired
    TProjectServiceFeign projectServiceFeign;
    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        TOrder order=new TOrder();

        String accessToken = vo.getAccessToken();
        String memberId = stringRedisTemplate.opsForValue().get(accessToken);
        order.setMemberid(Integer.parseInt(memberId));
        order.setProjectid(vo.getProjectid());
        order.setReturnid(vo.getReturnid());

        String ordernum=UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        order.setOrdernum(ordernum);
        order.setCreatedate(AppDateUtils.getFormatTime());

        AppResponse<TReturn> response = projectServiceFeign.returnInfo(vo.getReturnid());//远程调用
        TReturn retObj=response.getData();

        Integer totalMoney=vo.getRtncount()*retObj.getSupportmoney()+retObj.getFreight();
        order.setMoney(totalMoney);
        order.setRtncount(vo.getRtncount());
        order.setStatus(OrderStatusEnumes.UNPAY.getCode()+"");
        order.setAddress(vo.getAddress());
        order.setInvoice(vo.getInvoice().toString());
        order.setInvoictitle(vo.getInvoictitle());
        order.setRemark(vo.getRemark());

        orderMapper.insertSelective(order);

        log.debug("业务层保存订单-------{}",order);
        return order;
    }

    @Override
    public List<TOrder> listSupportProject(Integer memberid) {
        TOrderExample example=new TOrderExample();
        example.createCriteria().andMemberidEqualTo(memberid).andStatusBetween("2","7");

        List<TOrder> orders = orderMapper.selectByExample(example);
        return orders;
    }
}
